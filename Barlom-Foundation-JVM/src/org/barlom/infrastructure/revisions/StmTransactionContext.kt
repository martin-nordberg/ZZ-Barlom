//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * Utility class for managing STM transactions.
 */
object StmTransactionContext {

    /**
     * Thread-local storage for the transaction in use by the current thread (can be only one per thread).
     */
    private val _transactionOfCurrentThread: RevThreadLocal<IStmTransaction> = RevThreadLocal()


    /**
     * @return the status of the currently active transaction or NO_TRANSACTION if there is none.
     */
    val status: ETransactionStatus
        get() {

            // Get the thread-local transaction.
            val transaction = _transactionOfCurrentThread.get()

            // Ask the transaction for its status.
            return transaction?.status ?: ETransactionStatus.NO_TRANSACTION

        }


    /**
     * Aborts the current transaction.
     */
    fun abortTransaction(e: Exception?) {

        val transaction = transactionOfCurrentThread

        try {
            // Abort the changes.
            transaction.abort(e)
        }
        finally {
            // Clear the thread's transaction.
            _transactionOfCurrentThread.set(transaction.enclosingTransaction)
        }

    }

    /**
     * Creates a new read-nested write transaction. The lifecycle of the transaction must be managed by the client,
     * which is responsible for calling either commitTransaction or abortTransaction.
     */
    fun beginReadNestedWriteTransaction() {
        beginTransaction(ETransactionWriteability.READ_WITH_NESTED_WRITES)
    }

    /**
     * Creates a new read-only transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commitTransaction or abortTransaction.
     */
    fun beginReadOnlyTransaction() {
        beginTransaction(ETransactionWriteability.READ_ONLY)
    }

    /**
     * Creates a new read-write transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commitTransaction or abortTransaction.
     */
    fun beginReadWriteTransaction() {
        beginTransaction(ETransactionWriteability.READ_WRITE)
    }

    /**
     * Creates a new read-only transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commit or abort on the result.
     */
    private fun beginTransaction(writeability: ETransactionWriteability) {

        var transaction = _transactionOfCurrentThread.get()
        if (transaction == null) {
            transaction = StmTransaction(writeability)
        }
        else {
            transaction = NestedStmTransaction(writeability, transaction)
        }

        _transactionOfCurrentThread.set(transaction)

    }

    /**
     * Commits the current transaction.
     */
    fun commitTransaction() {

        val transaction = transactionOfCurrentThread

        try {
            // Commit the changes.
            transaction.commit()
        }
        catch (e: Exception) {
            // On any error abort the transaction.
            transaction.abort(e)
            throw e
        }
        finally {
            // Clear the thread's transaction.
            _transactionOfCurrentThread.set(transaction.enclosingTransaction)
        }

    }

    /**
     * Performs the work of the given read-nested write callback inside a newly created transaction.
     *
     * @param task       the work to be done inside a transaction.
     * @param maxRetries the maximum number of times to retry the transaction if write conflicts are encountered (must
     *                   be zero or more, zero meaning try but don't retry); ignored for nested transactions.
     *
     * @throws MaximumRetriesExceededException if the transaction fails even after the specified number of retries.
     */
    fun doInReadNestedWriteTransaction(maxRetries: Int, task: Runnable) {

        doInTransaction(ETransactionWriteability.READ_WITH_NESTED_WRITES, maxRetries, task)

    }

    /**
     * Performs the work of the given read-only callback inside a newly created transaction.
     *
     * @param task the work to be done inside a transaction.
     */
    fun doInReadOnlyTransaction(task: Runnable) {

        doInTransaction(ETransactionWriteability.READ_ONLY, 0, task)

    }

    /**
     * Performs the work of the given read-write callback inside a newly created transaction.
     *
     * @param task       the work to be done inside a transaction.
     * @param maxRetries the maximum number of times to retry the transaction if write conflicts are encountered (must
     *                   be zero or more, zero meaning try but don't retry); ignored for nested transactions.
     *
     * @throws MaximumRetriesExceededException if the transaction fails even after the specified number of retries.
     */
    fun doInReadWriteTransaction(maxRetries: Int, task: Runnable) {

        doInTransaction(ETransactionWriteability.READ_WRITE, maxRetries, task)

    }

    /**
     * Performs the work of the given callback inside a newly created transaction.
     *
     * @param task       the work to be done inside a transaction.
     * @param maxRetries the maximum number of times to retry the transaction if write conflicts are encountered (must
     *                   be zero or more, zero meaning try but don't retry).
     *
     * @throws MaximumRetriesExceededException if the transaction fails even after the specified number of retries.
     */
    private fun doInTransaction(writeability: ETransactionWriteability, maxRetries: Int, task: Runnable) {

        // Sanity check the input.
        if (maxRetries < 0) {
            throw IllegalArgumentException("Retry count must be greater than or equal to zero.")
        }

        try {

            var retry = 0
            while (retry <= maxRetries) {

                try {
                    var transaction = _transactionOfCurrentThread.get()
                    if (transaction == null) {
                        transaction = StmTransaction(writeability)
                    }
                    else {
                        transaction = NestedStmTransaction(writeability, transaction)
                    }

                    try {
                        _transactionOfCurrentThread.set(transaction)

                        // Execute the transactional task.
                        task.run()

                        // Commit the changes.
                        transaction.commit()

                        // If succeeded, no more retries are needed.
                        return
                    }
                    catch (e: Exception) {
                        // On any error abort the transaction.
                        transaction.abort(e)
                        throw e
                    }
                    finally {
                        // Clear the thread's transaction.
                        _transactionOfCurrentThread.set(transaction.enclosingTransaction)
                    }
                }
                catch (e: WriteConflictException) {
                    // Do not retry nested transactions
                    if (_transactionOfCurrentThread.get() != null) {
                        break
                    }

                    // Ignore the exception; go around the loop again....

                    // Increment the thread priority for a better chance on next try.
                    if (Thread.currentThread().getPriority() < Thread.MAX_PRIORITY) {
                        Thread.currentThread().setPriority(Thread.currentThread().getPriority() + 1)
                    }
                }

                retry += 1

            }

            // If we dropped out of the loop, then we exceeded the retry count.
            throw MaximumRetriesExceededException()

        }
        finally {
            // Restore the thread priority after any retries.
            Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
        }

    }

    /**
     * @return the transaction that has been established for the currently running thread
     */
    val transactionOfCurrentThread: IStmTransaction
        get() {

            // Get the thread-local transaction.
            val result = _transactionOfCurrentThread.get()

            // If there is none, then it's a programming error.
            if (result == null) {
                throw IllegalStateException("Attempted to complete a transactional operation without a transaction.")
            }

            return result

        }

}
