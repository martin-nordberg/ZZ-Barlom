//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.revisions

/**
 * Decrement the integer value by one.
 */
fun V<Int>.decrement() {
    set(get() - 1)
}

/**
 * Increment the integer value by one.
 */
fun V<Int>.increment() {
    set(get() + 1)
}
