//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * A version-managing handle to an integer value with transactional revisions. Adds increment and decrement
 * shorthand to V<Int>.
 */
class VInt(

    /** The initial value. */
    value: Int

) : V<Int>(value) {

    /**
     * Decrement the integer value by one.
     */
    fun decrement() {
        set(get() - 1)
    }

    /**
     * Increment the integer value by one.
     */
    fun increment() {
        set(get() + 1)
    }

}
