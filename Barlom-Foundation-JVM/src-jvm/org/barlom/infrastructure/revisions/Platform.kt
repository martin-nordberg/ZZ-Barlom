//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions


/** Type for a revision number with atomic updates. */
typealias AtomicRevisionNumber = java.util.concurrent.atomic.AtomicLong

/** Type for a boolean with atomic updates. */
typealias RevAtomicBoolean = java.util.concurrent.atomic.AtomicBoolean

/** Type for a reference with atomic updates. */
typealias RevAtomicReference<T> = java.util.concurrent.atomic.AtomicReference<T>

/** Type for a queue of in-progress revisions. */
typealias RevQueueImpl = java.util.concurrent.PriorityBlockingQueue<Long>

/** Type for a queue of in-progress revisions. */
typealias RevQueue = java.util.Queue<Long>

/** Synonym for a thread local value. */
typealias RevThreadLocal<T> = java.lang.ThreadLocal<T>

/** Type for the hash code of a versioned item. */
typealias VersionedItemHashCode = java.util.concurrent.atomic.AtomicInteger

/** Increments the priority of the current thread. */
internal fun incrementThreadPriority() {

    if (Thread.currentThread().priority < Thread.MAX_PRIORITY) {
        Thread.currentThread().priority = Thread.currentThread().priority + 1
    }

}

/** Restores the thread priority to normal. */
internal fun restoreNormalThreadPriority() {
    Thread.currentThread().priority = Thread.NORM_PRIORITY
}