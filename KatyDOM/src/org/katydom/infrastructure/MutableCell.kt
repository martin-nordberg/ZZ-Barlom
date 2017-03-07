//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

/**
 * Read-only interface to a mutable cell.
 */
class MutableCell<T>(
    private var _value: T? = null
) : Cell<T> {

    override fun contains(value: T): Boolean {
        return value == _value
    }

    override fun get(): T? {
        return this._value
    }

    override fun ifNotPresent(action: () -> Unit) {

        val value = _value

        if (value == null) {
            action()
        }

    }

    override fun ifPresent(action: (T) -> Unit) {

        val value = _value

        if (value != null) {
            action(value)
        }

    }

    fun set(value: T?) {
        this._value = value;
    }

}