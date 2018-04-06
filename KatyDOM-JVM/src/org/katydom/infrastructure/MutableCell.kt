//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

/**
 * Implementation of a cell with provision for changing its contents.
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

    /**
     * Changes the value held in this cell.
     * @param value the new value.
     */
    fun set(value: T?) {
        this._value = value
    }

}