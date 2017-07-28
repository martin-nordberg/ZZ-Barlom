//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions


/** Type for a revision number with atomic updates. */
class AtomicRevisionNumber(
    value: Long
) {

    private var _value : Long = value

    fun decrementAndGet(): Long {
        _value -= 1
        return _value
    }

    fun get() : Long {
        return _value
    }

    fun incrementAndGet(): Long {
        _value += 1
        return _value
    }

    fun set(value:Long) {
        _value = value
    }

}

/** Type for a reference with atomic updates. */
class RevAtomicReference<T>(
    value: T
) {

    private var _value : T = value

    fun compareAndSet( expected:T, value:T): Boolean {

        if ( _value == expected ) {
            _value = value
            return true
        }

        return false

    }

    fun get() : T {
        return _value
    }

    fun set(value:T) {
        _value = value
    }

}

/** Synonym for a thread local value. */
class RevThreadLocal<T> {

    private var _value : T? = null

    fun get() : T? {
        return _value
    }

    fun set(value:T?) {
        _value = value
    }

}

/** Type for the hash code of a versioned item. */
class VersionedItemHashCode(
    value : Int
) {

    private var _value = value

    fun incrementAndGet(): Int {
        _value += 1
        return _value
    }

}

/** Increments the priority of the current thread. */
internal fun incrementThreadPriority() {
    // do nothing
}

/** Restores the thread priority to normal. */
internal fun restoreNormalThreadPriority() {
    // do nothing
}
