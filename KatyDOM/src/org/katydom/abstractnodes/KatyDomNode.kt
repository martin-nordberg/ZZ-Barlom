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
 * @param key a key for this KatyDOM node that is unique among all the siblings of this node.
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
     * @param childNode the child node to add.
     */
    internal fun addChildNode(childNode: KatyDomNode) {

        scaffolding.childNodes.add(childNode)

        if (childNode.key != null) {
            scaffolding.childNodesByKey.put(childNode.key, childNode)
        }

    }

    /**
     * Sets the attributes and child nodes of a newly created real DOM node to match this virtual DOM node.
     * @param domNode the real DOM node to be configured to mirror this virtual DOM node.
     */
    internal fun establish(domNode: Node) {

        if (domNode.nodeName != nodeName) { throw IllegalArgumentException("Cannot establish a real DOM node differing in type from the KatyDOM node.") }

        if (_scaffolding != null) throw IllegalStateException("Virtual DOM node should be fully constructed before patching real DOM.")

        val document: Document = domNode.ownerDocument ?: throw IllegalArgumentException("DOM element must have an owner document.")

        for (childNode in childNodes) {

            when (childNode) {

                is KatyDomElement -> {
                    val childElement = document.createElement(childNode.nodeName)
                    childNode.establish(childElement)
                    domNode.appendChild(childElement)
                }

                is KatyDomText    -> {
                    val childText = document.createTextNode(childNode.nodeValue)
                    domNode.appendChild(childText)
                }

            }

        }

        establishAttributes(domNode)

    }

    /**
     * Patches a real DOM node by determining the difference between this KatyDOM node and its prior edition.
     * @param domNode the real DOM node corresponding to priorNode.
     * @param priorNode the prior edition of this KatyDOM node.
     */
    internal fun patch(domNode: Node, priorNode: KatyDomNode) {

        if (domNode.nodeName != nodeName) throw IllegalArgumentException("Cannot patch a real DOM node differing in type from the KatyDOM node.")
        if (priorNode.nodeName != nodeName) throw IllegalArgumentException("Cannot patch a difference between two KatyDOM nodes of different types.")

        if (_scaffolding != null) throw IllegalStateException("KatyDOM node should be fully constructed before patching real DOM.")

        // Quit early if the node is the same (e.g. memoized).
        if (this == priorNode) {
            return
        }

        // Patch the attributes.
        patchAttributes(domNode, priorNode)

        // Patch the child nodes.
        if (priorNode.childNodes.isEmpty()) {

            establish(domNode)

        }
        else if (childNodes.isEmpty()) {

            while (domNode.hasChildNodes()) {
                domNode.removeChild(domNode.firstChild!!)
            }

        }
        else {

            // TODO: The main point of virtual DOM is still TBD here: just patch the differences.

            while (domNode.hasChildNodes()) {
                domNode.removeChild(domNode.firstChild!!)
            }

            establish(domNode)

        }

    }

    /**
     * Freezes the content of this node. Makes any further attempt to add child nodes fail.
     */
    internal fun removeScaffolding() {
        _scaffolding = null
        removeAttributesScaffolding()
    }

////

    /**
     * Performs the DOM element configuration needed by a derived class. Override as needed. Base class method does nothing.
     * @param domElement the real DOM element being built.
     */
    open protected fun establishAttributes(domElement: Node) {
    }

    /**
     * Performs the patch needed by a derived class. Override as needed. Base class method does nothing.
     * @param domElement the real DOM node being patched.
     * @param priorElement the prior edition of this KatyDOM node from which to compute the patch.
     */
    open protected fun patchAttributes(domElement: Node, priorElement: KatyDomNode) {
    }

    /**
     * Removes the scaffolding of a derived class. Override as needed. Base class method does nothing.
     */
    open protected fun removeAttributesScaffolding() {
    }

////

    // TODO: Funky idea of scaffolding is over complicated - just use a boolean frozen flag.

    /**
     * Wrapper for the mutable state of this node while it is under construction. Removed when the node has been fully
     * built.
     */
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
