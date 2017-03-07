//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

/**
 * Read-only interface to a mutable cell.
 * TODO: Make it more like Option?
 * TODO: Make it iterable like a collection of one element?
 */
interface Cell<T> {

    fun contains(value:T):Boolean

    fun get(): T?

    fun ifNotPresent(action: () -> Unit)

    fun ifPresent(action: (T) -> Unit)

}
