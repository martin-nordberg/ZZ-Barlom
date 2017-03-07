//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.concretenodes.KatyDomText
import org.w3c.dom.Document
import org.w3c.dom.Node

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Topmost abstract base class for KatyDOM virtual DOM. Corresponds to DOM Node.
 */
abstract class KatyDomNode(val key: String?) {

    /** The child nodes within this node. Starts as an empty list. */
    val childNodes: List<KatyDomNode>

    /** A map of child nodes by their key. */
    val childNodesByKey: Map<String, KatyDomNode>

    /** The name of this node (usually the HTML tag name, otherwise a pseudo tag name like "#text"). */
    abstract val nodeName: String

////

    /**
     * Adds a new child node to this node.
     */
    internal fun addChildNode(childNode: KatyDomNode) {

        scaffolding.childNodes.add(childNode)

        if (childNode.key != null) {
            scaffolding.childNodesByKey.put(childNode.key, childNode)
        }

    }

    internal fun establish(domNode: Node) {

        val document: Document = domNode.ownerDocument ?: throw IllegalArgumentException("DOM element must have an owner document.")

        for (childNode in childNodes) {

            when (childNode) {
                is KatyDomElement -> {
                    val childElement = document.createElement(childNode.nodeName)
                    childNode.establish(childElement)
                    domNode.appendChild(childElement)
                }
                is KatyDomText    -> {
                    val childText = document.createTextNode(childNode.textChars)
                    domNode.appendChild(childText)
                }
            }

        }

        establish2(domNode)

    }

    internal fun patch(domNode: Node, priorNode: KatyDomNode) {

        patch2(domNode, priorNode)

        if ( priorNode.childNodes.isEmpty() ) {

            establish( domNode )

        }
        else if ( childNodes.isEmpty() ) {

            while( domNode.hasChildNodes() ) {
                domNode.removeChild( domNode.firstChild )
            }

        }
        else {

            // TODO: The main point of virtual DOM is still TBD here: just patch the differences.

            while( domNode.hasChildNodes() ) {
                domNode.removeChild( domNode.firstChild )
            }

            establish( domNode )

        }

    }

    /**
     * Freezes the content of this node. Makes any further attempt to add child nodes fail.
     */
    internal fun removeScaffolding() {
        _scaffolding = null
        removeScaffolding2()
    }

////

    /**
     * Performs the patch needed by a derived class. Override as needed. Base class method does nothing.
     */
    open protected fun establish2(domElement: Node) {}

    /**
     * Performs the patch needed by a derived class. Override as needed. Base class method does nothing.
     */
    open protected fun patch2(domElement: Node, priorElement: KatyDomNode) {}

    /**
     * Removes the scaffolding of a derived class. Override as needed. Base class method does nothing.
     */
    open protected fun removeScaffolding2() {}

////

    private class Scaffolding {
        val childNodes: MutableList<KatyDomNode> = arrayListOf()
        val childNodesByKey: MutableMap<String, KatyDomNode> = hashMapOf()
    }

    private var _scaffolding: Scaffolding?

    init {
        val sc = Scaffolding()
        _scaffolding = sc
        childNodes = sc.childNodes
        childNodesByKey = sc.childNodesByKey
    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomNode.")

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
