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

        if (isAddingAttributes) {
            freezeAttributes()
            state = EState.ADDING_CHILD_NODES
        }
        else if (!isAddingChildNodes) {
            throw IllegalStateException("Cannot modify a KatyDOM node after it has been fully constructed.")
        }

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

        if (state < EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node must be fully constructed before establishing the real DOM.")
        if (state > EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node already established.")

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

        this.domNode = domNode

        state = EState.ESTABLISHED

    }

    /**
     * Freezes the content of this node. Makes any further attempt to add child nodes or attributes fail.
     */
    internal fun freeze() {

        if (state >= EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node already fully constructed.")

        if (isAddingAttributes) {
            freezeAttributes()
        }

        state = EState.CONSTRUCTED

    }

    /**
     * Patches a real DOM node by determining the difference between this KatyDOM node and its prior edition.
     * @param domNode the real DOM node corresponding to priorNode.
     * @param priorNode the prior edition of this KatyDOM node.
     */
    internal fun patch(domNode: Node, priorNode: KatyDomNode) {

        if (state < EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node must be fully constructed before establishing the real DOM.")
        if (state > EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node already established.")

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

            this.domNode = domNode

            state = EState.ESTABLISHED

        }
        else {

            // TODO: The main point of virtual DOM is still TBD here: just patch the differences.

            while (domNode.hasChildNodes()) {
                domNode.removeChild(domNode.firstChild!!)
            }

            establish(domNode)

        }

        priorNode.state = EState.PATCHED

    }

////

    /**
     * States in the lifecycle of a node.
     */
    protected enum class EState {
        /** The node is still open for its attributes to be set by one of the KatyDOM builders. */
        ADDING_ATTRIBUTES,
        /** The node's children are still under construction. */
        ADDING_CHILD_NODES,
        /** The node has been fully defined and is ready to be established in the real DOM. */
        CONSTRUCTED,
        /** The node has been established in the real DOM (either the first edition or replacing a prior edition). */
        ESTABLISHED,
        /** The node has been replaced by a newer edition in the real DOM. */
        PATCHED
    }

    /**
     * Performs the DOM element configuration needed by a derived class.
     * @param domElement the real DOM element being built.
     */
    abstract protected fun establishAttributes(domElement: Node)

    /**
     * Removes the scaffolding of a derived class. Override as needed. Base class method does nothing.
     */
    abstract protected fun freezeAttributes()

    /** Whether this node is still being built. */
    protected val isAddingAttributes
        get() = state == EState.ADDING_ATTRIBUTES

    /** Whether this node is still being built. */
    protected val isAddingChildNodes
        get() = state == EState.ADDING_CHILD_NODES

    /** Whether this node is fully constructed. */
    protected val isConstructed
        get() = state == EState.CONSTRUCTED

    /** Whether this node has been established in the real DOM. */
    protected val isEstablished
        get() = state == EState.ESTABLISHED

    /** Whether this node has been replaced by a later edition in the real DOM. */
    protected val isPatched
        get() = state == EState.PATCHED

    /**
     * Performs the patch needed by a derived class. Override as needed. Base class method does nothing.
     * @param domElement the real DOM node being patched.
     * @param priorElement the prior edition of this KatyDOM node from which to compute the patch.
     */
    abstract protected fun patchAttributes(domElement: Node, priorElement: KatyDomNode)

////

    /** The child nodes within this node. Starts as an empty list. */
    private val childNodes: MutableList<KatyDomNode> = arrayListOf()

    /** A map of child nodes by their key. */
    private val childNodesByKey: MutableMap<String, KatyDomNode> = hashMapOf()

    /** The established DOM node after this node has been established or patched. */
    private var domNode: Node? = null

    /** Flag set to true once the node is fully constructed. */
    private var state: EState = EState.ADDING_ATTRIBUTES

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
