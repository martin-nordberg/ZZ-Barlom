//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * A version-managing handle to a linked list with transactional revisions.
 *
 * @param <T> the type of the value that is held in the list.
 */
open class VLinkedList<T> {

    /**
     * Reference to the latest revision. Revisions are kept in a custom linked list with the newest revision at the head
     * of the list.
     */
    private val _firstLink : V<Link<T>?> = V(null)

    /**
     * The (versioned) size of this list.
     */
    private val _size = VInt(0)


    /** Whether the list is empty. */
    val isEmpty : Boolean
        get() = _size.get() == 0

    /** Whether the list is non-empty. */
    val isNotEmpty : Boolean
        get() = _size.get() > 0

    /** The size of this list for the active revision in review. Size is O(1). */
    val size : Int
        get() = _size.get()


    /**
     * Adds an item to the list. Insertion is O(1)
     */
    fun add( value: T ) {
        val link = Link(value,_firstLink.get(),V(false))
        _firstLink.set(link)
        _size.increment()
    }

    /**
     * Makes a snapshot copy of the linked list. Copying is O(n)
     */
    fun asList() : List<T> {
        return asMutableList()
    }

    /**
     * Makes a snapshot copy of the linked list. Copying is O(n)
     */
    private fun asMutableList() : MutableList<T> {

        val result = ArrayList<T>(size)

        var link = _firstLink.get()
        while ( link != null ) {

            if ( !link.isDeleted.get() ) {
                result.add(link.value)
            }

            link = link.nextLink

        }

        return result

    }

    /**
     * Makes a snapshot copy of the linked list sorted by the given criteria. Copying is O(n)
     */
    fun <R : Comparable<R>> asSortedList(selector: (T) -> R?): List<T> {
        val result = asMutableList()
        result.sortBy(selector)
        return result
    }

    /**
     * Tests whether the list contains a given value. Search is O(n).
     */
    fun contains(value:T) : Boolean {

        var link = _firstLink.get()
        while ( link != null ) {

            if ( !link.isDeleted.get() && link.value == value ) {
                return true
            }

            link = link.nextLink

        }

        return false

    }

    /**
     * Performs the given [action] on each item in the list.
     */
    fun forEach(action:(T)->Unit) : Unit {

        var link = _firstLink.get()
        while ( link != null ) {

            if ( !link.isDeleted.get() ) {
                action(link.value)
            }

            link = link.nextLink

        }

    }

    /**
     * Performs the given [action] on each item in the list. Quits early if action() returns false.
     */
    fun forEachWhile(action:(T)->Boolean) : Unit {

        var link = _firstLink.get()
        while ( link != null ) {

            if ( !link.isDeleted.get() ) {
                if ( !action(link.value) ) {
                    break
                }
            }

            link = link.nextLink

        }

    }

    /**
     * Removes an item (the first matching the input) from the list. Removal is O(n).
     */
    fun remove(value:T) : Boolean {

        var link = _firstLink.get()
        while ( link != null ) {

            if ( !link.isDeleted.get() && link.value == value ) {
                link.isDeleted.set(true)
                _size.decrement()
                return true
            }

            link = link.nextLink

        }

        return false

    }


    /**
     * Internal record structure for links in the list, each versioned.
     *
     * @param <T> the type of the value that is managed through its revisions.
     */
    private class Link<T>(
        val value: T,
        val nextLink: Link<T>?,
        val isDeleted: V<Boolean>
    )

}
