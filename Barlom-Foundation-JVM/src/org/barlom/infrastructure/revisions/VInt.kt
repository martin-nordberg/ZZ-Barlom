//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * A version-managing handle to an integer value with transactional revisions.
 */
class VInt(

    /** The initial value. */
    value: Int

) : AbstractVersionedItem() {

    /**
     * Reference to the latest revision. Revisions are kept in a custom linked list with the newest revision at the head
     * of the list.
     */
    private val _latestVersion: RevAtomicReference<Version>


    /**
     * Constructs a new versioned integer with given starting value for the current transaction's revision.
     */
    init {

        // Track everything through the current transaction.
        val targetRevisionNumber = RevisionHistory.transactionOfCurrentThread.targetRevisionNumber

        _latestVersion = RevAtomicReference(
            Version(value, targetRevisionNumber, null)
        )

    }


    /**
     * Decrement the integer value by one.
     */
    fun decrement() {
        set(get() - 1)
    }

    /**
     * Reads the version of the integer relevant for the transaction active in the currently running thread.
     *
     * @return the value as of the start of the transaction or else as written by the transaction
     */
    fun get(): Int {

        // Track everything through the current transaction when we're doing a write.
        val currentTransaction = RevisionHistory.maybeTransactionOfCurrentThread

        // Work within the transaction of the current thread if defined.
        val sourceRevisionNumber = currentTransaction?.sourceRevisionNumber ?: revisionHistory.revisionNumberInCurrentThread
        val targetRevisionNumber = currentTransaction?.targetRevisionNumber?.get()

        // Loop through the revisions.
        var version = _latestVersion.get()
        while (version != null) {

            val revisionNumber = version.revisionNumber.get()

            // If written by the current transaction, read back the written value.
            if (revisionNumber == targetRevisionNumber) {
                return version.value
            }

            // If written and committed by some other transaction, note that our transaction is already poised for
            // a write conflict if it writes anything. I.e. fail early for a write conflict.
            if (revisionNumber > sourceRevisionNumber) {
                currentTransaction?.setNewerRevisionSeen()
            }

            // If revision is committed and older or equal to our source revision, read it.
            if (revisionNumber in 1..sourceRevisionNumber) {
                // Keep track of everything we've read.
                currentTransaction?.addVersionedItemRead(this)

                // Return the value found for the source revision or earlier.
                return version.value
            }

            version = version.priorVersion.get()

        }

        throw NullPointerException("No revision found for transaction.")

    }

    /**
     * Increment the integer value by one.
     */
    fun increment() {
        set(get() + 1)
    }

    /**
     * Writes a new revision of the integer managed by this handle.
     *
     * @param value The new raw value to become the next revision of this item.
     */
    fun set(value: Int) {

        // Work within the transaction of the current thread.
        val currentTransaction = RevisionHistory.transactionOfCurrentThread

        val sourceRevisionNumber = currentTransaction.sourceRevisionNumber
        val targetRevisionNumber = currentTransaction.targetRevisionNumber.get()

        // Loop through the revisions ...
        var version = _latestVersion.get()
        while (version != null) {

            val revisionNumber = version.revisionNumber.get()

            // If previously written by the current transaction, just update to the newer value.
            if (revisionNumber == targetRevisionNumber) {
                version.value = value
                return
            }

            // If revision is committed and older or equal to our source revision, need a new one.
            if (revisionNumber in 1..sourceRevisionNumber) {

                // ... except if not changed, treat as a read.
                if (value == version.value) {
                    currentTransaction.addVersionedItemRead(this)
                    return
                }

                break

            }

            version = version.priorVersion.get()

        }

        // Create the new revision at the front of the chain.
        _latestVersion.set(
            Version(
                value, currentTransaction.targetRevisionNumber, _latestVersion.get()
            )
        )

        // Keep track of everything we've written.
        currentTransaction.addVersionedItemWritten(this)

    }

    override fun ensureNotWrittenByOtherTransaction() {

        // Work within the transaction of the current thread.
        val currentTransaction = RevisionHistory.transactionOfCurrentThread

        val sourceRevisionNumber = currentTransaction.sourceRevisionNumber

        // Loop through the revisions ...
        var version = _latestVersion.get()
        while (version != null) {

            val revisionNumber = version.revisionNumber.get()

            // If find something newer, then transaction conflicts.
            if (revisionNumber > sourceRevisionNumber) {
                throw WriteConflictException()
            }

            // If revision is committed and older or equal to our source revision, then done.
            if (revisionNumber <= sourceRevisionNumber && revisionNumber > 0L) {
                break
            }

            version = version.priorVersion.get()

        }

    }

    override fun removeAbortedVersion() {

        // First check the latest revision.
        var version = _latestVersion.get()

        while (version.revisionNumber.get() == 0L) {
            if (_latestVersion.compareAndSet(version, version.priorVersion.get())) {
                return
            }
            version = _latestVersion.get()
        }

        // Loop through the revisions.
        var priorVersion = version.priorVersion.get()
        while (priorVersion != null) {

            val revisionNumber = priorVersion.revisionNumber.get()

            if (revisionNumber == 0L) {
                // If found & removed w/o concurrent change, then done.
                if (version.priorVersion.compareAndSet(priorVersion, priorVersion.priorVersion.get())) {
                    return
                }

                // If concurrent change, abandon this call and try again from the top.
                priorVersion = null
                removeAbortedVersion()
            }
            else {
                // Advance through the list.
                version = priorVersion
                priorVersion = version.priorVersion.get()
            }

        }

    }

    override fun removeUnusedVersions(oldestUsableRevisionNumber: Long) {

        // Loop through the revisions.
        var version = _latestVersion.get()
        while (version != null) {

            val revisionNumber = version.revisionNumber.get()

            // Truncate revisions older than the oldest usable revision.
            if (revisionNumber == oldestUsableRevisionNumber) {
                version.priorVersion.set(null)
                break
            }

            version = version.priorVersion.get()

        }

    }


    /**
     * Internal record structure for revisions in the linked list of revisions.
     */
    private class Version(
        var value: Int,
        val revisionNumber: AtomicRevisionNumber,
        priorVersionRef: Version?
    ) {

        val priorVersion = RevAtomicReference(priorVersionRef)

    }

}
