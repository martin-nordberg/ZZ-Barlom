//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.revisions

import java.util.*
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

/**
 * Utility class for managing in-memory transactions. The code is similar to "versioned boxes", the concept behind JVSTM
 * for software transactional memory. However, this code is much more streamlined, though very experimental.
 */
class StmTransaction(
    override val writeability: ETransactionWriteability
) : IStmTransaction {

    /**
     * A newer revision number seen during reading will cause a write conflict if anything writes through this
     * transaction.
     */
    private var _newerRevisionSeen: Boolean

    /**
     * The next transaction in a linked list of transactions awaiting clean up.
     */
    private val _nextTransactionAwaitingCleanUp: AtomicReference<StmTransaction?>

    /**
     * The versioned items read by this transaction.
     */
    private val _versionedItemsRead: MutableSet<AbstractVersionedItem>

    /**
     * The versioned item written by this transaction.
     */
    private val _versionedItemsWritten: MutableSet<AbstractVersionedItem>


    /**
     * The revision number being read by this transaction.
     */
    override val sourceRevisionNumber: Long

    /**
     * The revision number being written by this transaction. Negative while the transaction is running; zero if the
     * transaction is aborted; positive after the transaction has been committed.
     */
    override val targetRevisionNumber: AtomicLong


    /**
     * Constructs a new transaction.
     */
    init {

        // Spin until we get a next rev number and put it in the queue of rev numbers in use w/o concurrent change.
        // (We avoid concurrent change because if another thread bumped the revisions in use, it might also have
        // cleaned up the revision before we said we were using it.)
        while (true) {
            val sourceRevNumber = StmTransaction.lastCommittedRevisionNumber.get()
            StmTransaction.sourceRevisionsInUse.add(sourceRevNumber)
            if (sourceRevNumber == StmTransaction.lastCommittedRevisionNumber.get()) {
                sourceRevisionNumber = sourceRevNumber
                break
            }
            StmTransaction.sourceRevisionsInUse.remove(sourceRevNumber)
        }

        // Use the next negative pending revision number to mark our writes.
        targetRevisionNumber = AtomicLong(StmTransaction.lastPendingRevisionNumber.decrementAndGet())

        // Track the versioned items read and written by this transaction.
        _versionedItemsRead = HashSet()
        _versionedItemsWritten = HashSet()

        // Flag a write conflict as early as possible.
        _newerRevisionSeen = false

        // Establish a link for putting this transaction in a linked list of completed transactions.
        _nextTransactionAwaitingCleanUp = AtomicReference(null)

    }


    override fun abort(e: Exception?) {

        // Revision number = 0 indicates an aborted transaction.
        targetRevisionNumber.set(0L)

        // Clean up aborted revisions ...
        _versionedItemsWritten.forEach(
            org.barlom.infrastructure.utilities.revisions.AbstractVersionedItem::removeAbortedRevision)

        _versionedItemsRead.clear()
        _versionedItemsWritten.clear()

        // Trigger any clean up that is possible from no longer needing our source version.
        cleanUpOlderRevisions()

    }

    override fun addVersionedItemRead(versionedItem: AbstractVersionedItem) {

        // Sanity check the input.
        Objects.requireNonNull(versionedItem)

        // Track versioned items read by this transaction.
        _versionedItemsRead.add(versionedItem)

    }

    override fun addVersionedItemWritten(versionedItem: AbstractVersionedItem) {

        // Sanity check the input.
        Objects.requireNonNull(versionedItem)

        // Track all versioned items written by this transaction.
        _versionedItemsWritten.add(versionedItem)

        // If we have already seen a write conflict, fail early.
        if (_newerRevisionSeen) {
            throw WriteConflictException()
        }

    }

    override fun commit() {

        // TBD: notify observers of read & written items inside transaction -- use a callback interface

        // Make the synchronized changed to make the transaction permanent.
        if (!_versionedItemsWritten.isEmpty()) {
            StmTransaction.writeTransaction(this)
        }

        // TBD: notify observers of read & written items outside the transaction -- use a callback interface

        // No longer hang on to the items read.
        _versionedItemsRead.clear()

        // Add this transaction (with its written revisions) to a queue awaiting clean up when no longer needed.
        awaitCleanUp()

        // Trigger any clean up that is possible from no longer needing our source version.
        cleanUpOlderRevisions()

    }

    override fun ensureWriteable() {
        if (writeability != ETransactionWriteability.READ_WRITE) {
            throw IllegalStateException("Attempted to write a value during a read-only transaction.")
        }
    }

    override val enclosingTransaction: IStmTransaction?
        get() = null

    override val status: ETransactionStatus
        get() {

            val targetRevNumber = targetRevisionNumber.get()

            if (targetRevNumber < 0L) {
                return ETransactionStatus.IN_PROGRESS
            }

            if (targetRevNumber == 0L) {
                return ETransactionStatus.ABORTED
            }

            return ETransactionStatus.COMMITTED

        }

    override fun setNewerRevisionSeen() {

        // If we have previously written something, then we've detected a write conflict; fail early.
        if (!_versionedItemsWritten.isEmpty()) {
            throw WriteConflictException()
        }

        // Track the newer revision number to fail early if we subsequently write something.
        _newerRevisionSeen = true

    }

    /**
     * Puts this transaction at the head of a list of all transactions awaiting clean up.
     */
    private fun awaitCleanUp() {

        // Get the first transaction awaiting clean up.
        var firstTransAwaitingCleanUp = StmTransaction.firstTransactionAwaitingCleanUp.get()

        // Link this transaction into the head of the list.
        _nextTransactionAwaitingCleanUp.set(firstTransAwaitingCleanUp)

        // Spin until we do both atomically.
        while (!StmTransaction.firstTransactionAwaitingCleanUp.compareAndSet(firstTransAwaitingCleanUp, this)) {
            firstTransAwaitingCleanUp = StmTransaction.firstTransactionAwaitingCleanUp.get()
            _nextTransactionAwaitingCleanUp.set(firstTransAwaitingCleanUp)
        }

    }

    /**
     * Removes the source revision number of this transaction from those in use. Cleans up older revisions if not in use
     * by other transactions.
     */
    private fun cleanUpOlderRevisions() {

        // We're no longer using the source revision.
        val priorOldestRevisionInUse = StmTransaction.sourceRevisionsInUse.peek()
        StmTransaction.sourceRevisionsInUse.remove(sourceRevisionNumber)

        // Determine the oldest revision still needed.
        var oldestRevisionInUse = StmTransaction.sourceRevisionsInUse.peek()
        if (oldestRevisionInUse == null) {
            oldestRevisionInUse = priorOldestRevisionInUse
        }

        //  Remove each transaction awaiting clean up that has a target revision number older than needed.
        var tref = StmTransaction.firstTransactionAwaitingCleanUp
        var t = tref.get()
        if (t == null) {
            return
        }

        var trefNext = t._nextTransactionAwaitingCleanUp
        var tNext = trefNext.get()

        while (t != null) {
            if (t.targetRevisionNumber.get() <= oldestRevisionInUse) {
                if (tref.compareAndSet(t, tNext)) {
                    // Remove revisions older than the now unused revision number.
                    t.removeUnusedRevisions()
                    t._nextTransactionAwaitingCleanUp.set(null)
                }
            }
            else {
                tref = trefNext
            }

            // Advance through the list of transactions awaiting clean up.
            t = tref.get()
            if (t == null) {
                return
            }
            trefNext = t._nextTransactionAwaitingCleanUp
            tNext = trefNext.get()
        }

    }

    /**
     * Cleans up all the referenced versioned items written by this transaction.
     */
    private fun removeUnusedRevisions() {

        // Remove all revisions older than the one written by this transaction.
        val oldestUsableRevNumber = targetRevisionNumber.get()
        for (versionedItem in _versionedItemsWritten) {
            versionedItem.removeUnusedRevisions(oldestUsableRevNumber)
        }

        // Stop referencing the versioned items.
        _versionedItemsWritten.clear()

    }

    companion object {

        /**
         * Head of a linked list of transactions awaiting clean up.
         */
        private val firstTransactionAwaitingCleanUp: AtomicReference<StmTransaction?> = AtomicReference(null)

        /**
         * Monotone increasing revision number incremented whenever a transaction is successfully committed.
         */
        private val lastCommittedRevisionNumber: AtomicLong = AtomicLong(0L)

        /**
         * Monotone decreasing revision number decremented whenever a transaction is started. Negative value indicates a
         * transaction in progress.
         */
        private val lastPendingRevisionNumber: AtomicLong = AtomicLong(0L)

        /**
         * Priority queue of revision numbers currently in use as the source revision for some transaction.
         */
        private val sourceRevisionsInUse: Queue<Long> = PriorityBlockingQueue()


        /**
         * Atomically commits the given transaction.
         *
         * @param transaction the transaction to commit
         *
         * @throws WriteConflictException if some other transaction has written some value the given transaction read.
         */
        private fun writeTransaction(transaction: StmTransaction) {

            synchronized(this) {
                // Check for conflicts.
                transaction._versionedItemsRead.forEach(AbstractVersionedItem::ensureNotWrittenByOtherTransaction)

                // Set the revision number to a committed value.
                transaction.targetRevisionNumber.set(StmTransaction.lastCommittedRevisionNumber.incrementAndGet())
            }

        }

    }

}
