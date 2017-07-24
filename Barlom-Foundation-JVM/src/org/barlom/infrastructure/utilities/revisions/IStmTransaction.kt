//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.revisions

import java.util.concurrent.atomic.AtomicLong

/**
 * Utility class for managing in-memory transactions. The code is similar to "versioned boxes", the concept behind JVSTM
 * for software transactional memory. However, this code is much more streamlined, though very experimental.
 */
interface IStmTransaction {

    /**
     * @return the outer transaction of a nested transaction or null for the outermost transaction.
     */
    val enclosingTransaction: IStmTransaction?

    /**
     * @return the revision number of information to be read by this transaction.
     */
    val sourceRevisionNumber: Long

    /**
     * Determines the status of this transaction from its target revision number. <p> TBD: this seems to have no use
     *
     * @return the transaction status (IN_PROGRESS, COMMITTED, or ABORTED).
     */
    val status: ETransactionStatus

    /**
     * @return the revision number of information written by this transaction (negative while transaction is running;
     * positive after committed.
     */
    val targetRevisionNumber: AtomicLong

    /**
     * @return the writeability of this transaction.
     */
    val writeability: ETransactionWriteability


    /**
     * Aborts this transaction; abandons the revisions made by the transaction.
     *
     * @param e the exception that caused a client to abort. the transaction.
     */
    fun abort(e: Exception?)

    /**
     * Tracks all versioned items read by this transaction. The transaction will confirm that all these items remain
     * unwritten by some other transaction before this transaction commits.
     *
     * @param versionedItem the item that has been read.
     */
    fun addVersionedItemRead(versionedItem: AbstractVersionedItem)

    /**
     * Tracks all versioned items written by this transaction. The versions written by this transaction will be cleaned
     * up after the transaction aborts. Any earlier versions will be cleaned up after all transactions using any earlier
     * versions and their source have completed.
     *
     * @param versionedItem the item that has been written.
     */
    fun addVersionedItemWritten(versionedItem: AbstractVersionedItem)

    /**
     * Commits this transaction.
     *
     * @throws WriteConflictException if some other transaction has concurrently written values read during this
     *                                transaction.
     */
    fun commit()

    /**
     * Ensures that the transaction is writeable.
     */
    fun ensureWriteable()

    /**
     * Takes note that some read operation has seen a newer version and will certainly fail with a write conflict if
     * this transaction writes anything. Fails immediately if this transaction has already written anything.
     */
    fun setNewerRevisionSeen()

}
