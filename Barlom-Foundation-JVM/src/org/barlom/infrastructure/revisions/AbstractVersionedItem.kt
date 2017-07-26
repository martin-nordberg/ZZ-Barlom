//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * Interface to a versioned value that supports clean up of obsolete or aborted versions.
 */
abstract class AbstractVersionedItem {

    /**
     * The sequential hash code of this versioned item.
     */
    private val _hashCode: Int


    /**
     * The revision history encompassing this versioned item.
     */
    val revisionHistory: RevisionHistory


    /**
     * Constructs a new versioned integer with given starting value for the current transaction's revision.
     */
    init {

        // Track everything through the current transaction.
        val currentTransaction = RevisionHistory.transactionOfCurrentThread

        revisionHistory = currentTransaction.revisionHistory

        _hashCode = revisionHistory.nextHashCode

        // Keep track of everything we've written.
        @Suppress("LeakingThis")
        currentTransaction.addVersionedItemWritten(this)

    }


    /**
     * Ensures that this item has been written by no transaction other than the currently running one.
     *
     * @throws WriteConflictException if there has been another transaction writing this item.
     */
    internal abstract fun ensureNotWrittenByOtherTransaction()

    override fun equals(other: Any?): Boolean {

        if (this == other) {
            return true
        }

        if (other is AbstractVersionedItem) {
            return _hashCode == other._hashCode
        }

        return false

    }

    override fun hashCode(): Int {
        return _hashCode
    }

    /**
     * Removes an aborted revision from this versioned item.
     */
    internal abstract fun removeAbortedVersion()

    /**
     * Removes any revisions older than the given one
     *
     * @param oldestUsableRevisionNumber the oldest revision number that can still be of any use.
     */
    internal abstract fun removeUnusedVersions(oldestUsableRevisionNumber: Long)

}
