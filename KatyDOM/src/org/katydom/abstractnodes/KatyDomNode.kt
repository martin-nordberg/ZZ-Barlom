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

    /** Returns the first child node in this node. (Out of bounds error if there is none.) */
    val firstChildNode: KatyDomNode
        get() = childNodes.first()

    /** The name of this node (usually the HTML tag name, otherwise a pseudo tag name like "#text"). */
    abstract val nodeName: String

////

    /**
     * Adds a new child node to this node.
     * @param childNode the child node to add.
     */
    internal fun addChildNode(childNode: KatyDomNode) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM node after it has been fully constructed.")

        childNodes.add(childNode)

        if (childNode.key != null) {
            childNodesByKey.put(childNode.key, childNode)
        }

    }

    /**
     * Sets the attributes and child nodes of a newly created real DOM node to match this virtual DOM node.
     * @param domNode the real DOM node to be configured to mirror this virtual DOM node.
     */
    internal fun establish(domNode: Node) {

        if (isUnderConstruction) throw IllegalStateException("KatyDOM node must be fully constructed before establishing the real DOM.")
        if (!isConstructed) throw IllegalStateException("KatyDOM node already established.")

        if (domNode.nodeName != nodeName) {
            throw IllegalArgumentException("Cannot establish a real DOM node differing in type from the KatyDOM node.")
        }

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

        state = EState.ESTABLISHED

    }

    /**
     * Freezes the content of this node. Makes any further attempt to add child nodes or attributes fail.
     */
    internal fun freeze() {

        if (!isUnderConstruction) throw IllegalStateException("KatyDOM node already fully constructed.")

        freezeAttributes()

        state = EState.CONSTRUCTED

    }

    /**
     * Patches a real DOM node by determining the difference between this KatyDOM node and its prior edition.
     * @param domNode the real DOM node corresponding to priorNode.
     * @param priorNode the prior edition of this KatyDOM node.
     */
    internal fun patch(domNode: Node, priorNode: KatyDomNode) {

        if (isUnderConstruction) throw IllegalStateException("KatyDOM node must be fully constructed before establishing the real DOM.")
        if (!isConstructed) throw IllegalStateException("KatyDOM node already established.")

        if (priorNode.isPatched) throw IllegalStateException("Prior node cannot be patched twice.")
        if (!priorNode.isEstablished) throw IllegalStateException("Prior KatyDOM node must be established before patching.")

        if (domNode.nodeName != nodeName) throw IllegalArgumentException("Cannot patch a real DOM node differing in type from the KatyDOM node.")
        if (priorNode.nodeName != nodeName) throw IllegalArgumentException("Cannot patch a difference between two KatyDOM nodes of different types.")

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

        state = EState.ESTABLISHED
        priorNode.state = EState.PATCHED

    }

////

    protected enum class EState {
        UNDER_CONSTRUCTION,
        CONSTRUCTED,
        ESTABLISHED,
        PATCHED
    }

    /**
     * Performs the DOM element configuration needed by a derived class. Override as needed. Base class method does nothing.
     * @param domElement the real DOM element being built.
     */
    open protected fun establishAttributes(domElement: Node) {
    }

    /**
     * Removes the scaffolding of a derived class. Override as needed. Base class method does nothing.
     */
    open protected fun freezeAttributes() {
    }

    protected val isConstructed
        get() = state == EState.CONSTRUCTED

    protected val isEstablished
        get() = state == EState.ESTABLISHED

    protected val isPatched
        get() = state == EState.PATCHED

    protected val isUnderConstruction
        get() = state == EState.UNDER_CONSTRUCTION

    /**
     * Performs the patch needed by a derived class. Override as needed. Base class method does nothing.
     * @param domElement the real DOM node being patched.
     * @param priorElement the prior edition of this KatyDOM node from which to compute the patch.
     */
    open protected fun patchAttributes(domElement: Node, priorElement: KatyDomNode) {
    }

////

    /** The child nodes within this node. Starts as an empty list. */
    private val childNodes: MutableList<KatyDomNode> = arrayListOf()

    /** A map of child nodes by their key. */
    private val childNodesByKey: MutableMap<String, KatyDomNode> = hashMapOf()

    /** Flag set to true once the node is fully constructed. */
    private var state: EState = EState.UNDER_CONSTRUCTION

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
