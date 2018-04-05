//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.kdom

import org.katydom.dom.Node
import org.katydom.dom.events.Event


/**
 * Implementation of Node for producing HTML text (server side or testing).
 */
abstract class KDomNode : Node {

    private var _firstChild: KDomNode? = null

    private var _nextSibling: KDomNode? = null

    private var _parentNode: KDomNode? = null

    override val firstChild
        get() = _firstChild

    override val lastChild: KDomNode?
        get() {

            var result = _firstChild

            while (result != null && result._nextSibling != null) {
                result = result._nextSibling
            }

            return result

        }

    override val nextSibling: KDomNode?
        get() = _nextSibling

    override val parentNode
        get() = _parentNode

    override fun addEventListener(eventName: String, eventHandler: (Event) -> Unit) {
        // do nothing in JVM
    }

    override fun appendChild(newChild: Node): KDomNode {
        val result = newChild as KDomNode
        if (_firstChild == null) {
            _firstChild = result
        }
        else {
            lastChild!!._nextSibling = result
        }
        result.setParentNode(this)
        return result
    }

    override fun hasChildNodes(): Boolean {
        return _firstChild != null
    }

    override fun insertBefore(newChild: Node, refChild: Node?): KDomNode {

        val result = newChild as KDomNode

        result._parentNode?.removeChild(result)

        if (refChild == null || _firstChild == null) {
            return appendChild(newChild)
        }

        if (_firstChild == refChild) {
            _firstChild = result
        }
        else {
            var child = _firstChild
            while (child != null && child._nextSibling != null && child._nextSibling != refChild) {
                child = child._nextSibling
            }

            if (child == null) {
                return appendChild(newChild)
            }
            else {
                child._nextSibling = newChild
            }
        }

        result._nextSibling = refChild as KDomNode
        result.setParentNode(this)
        return result

    }

    override fun removeChild(oldChild: Node): KDomNode {
        val result = oldChild as KDomNode
        if (_firstChild == result) {
            _firstChild = result._nextSibling
        }
        else {
            var child = _firstChild
            while (child != null && child._nextSibling != result) {
                child = child._nextSibling
            }
            if (child != null) {
                child._nextSibling = result._nextSibling
            }
        }
        result.setParentNode(null)
        return result
    }

    override fun removeEventListener(eventName: String, eventHandler: (Event) -> Unit) {
        // do nothing in JVM
    }

    internal fun setParentNode(parentNode: KDomNode?) {
        _parentNode = parentNode
    }

    abstract fun toHtmlString(indent: Int = 0): String

}