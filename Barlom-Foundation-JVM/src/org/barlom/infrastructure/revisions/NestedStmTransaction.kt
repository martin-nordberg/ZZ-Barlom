//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * Utility class for managing in-memory transactions. The code is similar to "versioned boxes", the concept behind JVSTM
 * for software transactional memory. However, this code is much more streamlined, though very experimental.
 */
class NestedStmTransaction(
    override val writeability: ETransactionWriteability,
    override val enclosingTransaction: IStmTransaction
) : IStmTransaction {

    override val sourceRevisionNumber: Long
        get() = enclosingTransaction.sourceRevisionNumber

    override val status: ETransactionStatus
        get() = enclosingTransaction.status

    override val targetRevisionNumber: RevAtomicLong
        get() = enclosingTransaction.targetRevisionNumber


    /**
     * Constructs a new transaction.
     */
    init {

        if (!writeability.isReadOnly() && enclosingTransaction.writeability.isReadOnly()) {
            throw IllegalStateException("Cannot nest a read-write transaction inside a read-only transaction.")
        }

    }


    override fun abort(e: Exception?) {
        throw NestedStmTransactionAbortedException(e)
    }

    override fun addVersionedItemRead(versionedItem: AbstractVersionedItem) {
        enclosingTransaction.addVersionedItemRead(versionedItem)
    }

    override fun addVersionedItemWritten(versionedItem: AbstractVersionedItem) {
        enclosingTransaction.addVersionedItemWritten(versionedItem)
    }

    override fun commit() {
        // do nothing

        // TODO: Committing a nested read-write transaction should ensure no write conflicts
    }

    override fun ensureWriteable() {
        if (writeability != ETransactionWriteability.READ_WRITE) {
            throw IllegalStateException("Attempted to write a value during a read-only transaction.")
        }
    }

    override fun setNewerRevisionSeen() {
        enclosingTransaction.setNewerRevisionSeen()
    }

}
