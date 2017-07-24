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
    private val _hashCode: Int = _lastHashCode.incrementAndGet()


    /**
     * Ensures that this item has been written by no transaction other than the currently running one.
     *
     * @throws WriteConflictException if there has been another transaction writing this item.
     */
    abstract fun ensureNotWrittenByOtherTransaction()

    override fun equals(other: Any?): Boolean {

        if (this == other) {
            return true
        }

        if (other == null || javaClass != other.javaClass) {
            return false
        }

        return _hashCode == (other as AbstractVersionedItem)._hashCode

    }

    override fun hashCode(): Int {
        return _hashCode
    }

    /**
     * Removes an aborted revision from this versioned item.
     */
    abstract fun removeAbortedRevision()

    /**
     * Removes any revisions older than the given one
     *
     * @param oldestUsableRevisionNumber the oldest revision number that can still be of any use.
     */
    abstract fun removeUnusedRevisions(oldestUsableRevisionNumber: Long)


    companion object {

        /** Tracks the last hash code used. */
        private val _lastHashCode = RevAtomicInteger(0)

    }

}
