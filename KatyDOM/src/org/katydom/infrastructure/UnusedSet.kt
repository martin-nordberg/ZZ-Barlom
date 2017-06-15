//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

/**
 * Implementation of MutableSet<T> that throws an error for every operation.
 */
class UnusedSet<T> : MutableSet<T> {

    override fun add(element: T): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun addAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun clear() {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun iterator(): MutableIterator<T> {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun remove(element: T): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override val size: Int
        get() = throw UnsupportedOperationException("Attempted to use unused set.")

    override fun contains(element: T): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

    override fun isEmpty(): Boolean {
        throw UnsupportedOperationException("Attempted to use unused set.")
    }

}