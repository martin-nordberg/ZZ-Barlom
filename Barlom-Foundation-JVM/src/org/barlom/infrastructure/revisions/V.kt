//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * A version-managing handle to a value with transactional revisions.
 *
 * @param <T> the type of the value that is managed through its revisions.
 */
class V<T>(

    /** the initial value. */
    value: T

) : AbstractVersionedItem() {

    /**
     * Reference to the latest revision. Revisions are kept in a custom linked list with the newest revision at the head
     * of the list.
     */
    private val _latestRevision: RevAtomicReference<Revision<T>>


    /**
     * Constructs a new versioned handle with given starting value for the current transaction's revision.
     */
    init {

        // Track everything through the current transaction.
        val currentTransaction: IStmTransaction = StmTransactionContext.transactionOfCurrentThread

        // Make sure we have a read/write transaction
        currentTransaction.ensureWriteable()

        _latestRevision = RevAtomicReference(
            Revision(value, currentTransaction.targetRevisionNumber, null)
        )

        // Keep track of everything we've written.
        currentTransaction.addVersionedItemWritten(this)

    }


    /**
     * Reads the version of the item relevant for the transaction active in the currently running thread.
     *
     * @return the value as of the start of the transaction or else as written by the transaction
     */
    fun get(): T {

        // Track everything through the current transaction.
        val currentTransaction = StmTransactionContext.transactionOfCurrentThread

        // Work within the transaction of the current thread.
        val sourceRevisionNumber = currentTransaction.sourceRevisionNumber
        val targetRevisionNumber = currentTransaction.targetRevisionNumber.get()

        // Loop through the revisions.
        var revision = _latestRevision.get()
        while (revision != null) {

            val revisionNumber = revision.revisionNumber.get()

            // If written by the current transaction, read back the written value.
            if (revisionNumber == targetRevisionNumber) {
                return revision.value
            }

            // If written and committed by some other transaction, note that our transaction is already poised for
            // a write conflict if it writes anything. I.e. fail early for a write conflict.
            if (revisionNumber > sourceRevisionNumber) {
                currentTransaction.setNewerRevisionSeen()
            }

            // If revision is committed and older or equal to our source revision, read it.
            if (revisionNumber <= sourceRevisionNumber && revisionNumber > 0L) {
                // Keep track of everything we've read.
                currentTransaction.addVersionedItemRead(this)

                // Return the value found for the source revision or earlier.
                return revision.value
            }

            revision = revision.priorRevision.get()

        }

        throw NullPointerException("No revision found for transaction.")

    }

    /**
     * Writes a new revision of the item managed by this handle.
     *
     * @param value The new raw value to become the next revision of this item.
     */
    fun set(value: T) {

        // Work within the transaction of the current thread.
        val currentTransaction = StmTransactionContext.transactionOfCurrentThread

        // Make sure we have a read/write transaction
        currentTransaction.ensureWriteable()

        val sourceRevisionNumber = currentTransaction.sourceRevisionNumber
        val targetRevisionNumber = currentTransaction.targetRevisionNumber.get()

        // Loop through the revisions ...
        var revision = _latestRevision.get()
        while (revision != null) {

            val revisionNumber = revision.revisionNumber.get()

            // If previously written by the current transaction, just update to the newer value.
            if (revisionNumber == targetRevisionNumber) {
                revision.value = value
                return
            }

            // If revision is committed and older or equal to our source revision, need a new one.
            if (revisionNumber <= sourceRevisionNumber && revisionNumber > 0L) {

                // ... except if not changed, treat as a read.
                if (value == revision.value) {
                    currentTransaction.addVersionedItemRead(this)
                    return
                }

                break

            }

            revision = revision.priorRevision.get()

        }

        // Create the new revision at the front of the chain.
        _latestRevision.set(
            Revision(value, currentTransaction.targetRevisionNumber, _latestRevision.get())
        )

        // Keep track of everything we've written.
        currentTransaction.addVersionedItemWritten(this)

    }

    override fun ensureNotWrittenByOtherTransaction() {

        // Work within the transaction of the current thread.
        val currentTransaction = StmTransactionContext.transactionOfCurrentThread

        val sourceRevisionNumber = currentTransaction.sourceRevisionNumber

        // Loop through the revisions ...
        var revision = _latestRevision.get()
        while (revision != null) {

            val revisionNumber = revision.revisionNumber.get()

            // If find something newer, then transaction conflicts.
            if (revisionNumber > sourceRevisionNumber) {
                throw WriteConflictException()
            }

            // If revision is committed and older or equal to our source revision, then done.
            if (revisionNumber <= sourceRevisionNumber && revisionNumber > 0L) {
                break
            }

            revision = revision.priorRevision.get()

        }

    }

    override fun removeAbortedRevision() {

        // First check the latest revision.
        var revision = _latestRevision.get()

        while (revision.revisionNumber.get() == 0L) {
            if (_latestRevision.compareAndSet(revision, revision.priorRevision.get())) {
                return
            }
        }

        // Loop through the revisions.
        var priorRevision = revision.priorRevision.get()
        while (priorRevision != null) {

            val revisionNumber = priorRevision.revisionNumber.get()

            if (revisionNumber == 0L) {
                // If found & removed w/o concurrent change, then done.
                if (revision.priorRevision.compareAndSet(priorRevision, priorRevision.priorRevision.get())) {
                    return
                }

                // If concurrent change, abandon this call and try again from the top.
                priorRevision = null
                removeAbortedRevision()
            }
            else {
                // Advance through the list.
                revision = priorRevision
                priorRevision = revision.priorRevision.get()
            }
        }

    }

    override fun removeUnusedRevisions(oldestUsableRevisionNumber: Long) {

        // Loop through the revisions.
        var revision = _latestRevision.get()
        while (revision != null) {

            val revisionNumber = revision.revisionNumber.get()

            // Truncate revisions older than the oldest usable revision.
            if (revisionNumber == oldestUsableRevisionNumber) {
                revision.priorRevision.set(null)
                break
            }

            revision = revision.priorRevision.get()
        }

    }


    /**
     * Internal record structure for revisions in the linked list of revisions.
     *
     * @param <T> the type of the value that is managed through its revisions.
     */
    private class Revision<T>(
        var value: T,
        val revisionNumber: RevAtomicLong,
        priorRevisionVal: Revision<T>?
    ) {

        /**
         * A reference to the previous revision of the versioned item.
         */
        val priorRevision: RevAtomicReference<Revision<T>?> = RevAtomicReference(priorRevisionVal)

    }

}
