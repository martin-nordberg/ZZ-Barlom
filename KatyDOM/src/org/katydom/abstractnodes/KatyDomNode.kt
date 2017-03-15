//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.w3c.dom.Document
import org.w3c.dom.Node

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Topmost abstract base class for KatyDOM virtual DOM. Corresponds to DOM Node.
 * @param key a key for this KatyDOM node that is unique among all the siblings of this node.
 */
abstract class KatyDomNode(val key: String?) {

    /** Returns the first child node in this node. (Out of bounds error if there is none.) */
    val soleChildNode: KatyDomNode
        get() {

            if (firstChildNode == Nothing) throw IllegalStateException("No child found.")
            if (firstChildNode != lastChildNode) throw IllegalStateException("More than one child node found.")

            return firstChildNode

        }

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

        if (childNodesByKey.containsKey(childNode.key)) throw IllegalStateException("Duplicate key: " + childNode.key)

        if (firstChildNode == Nothing) {
            firstChildNode = childNode
            lastChildNode = childNode
        }
        else {
            childNode.prevSiblingNode = lastChildNode
            lastChildNode.nextSiblingNode = childNode
            lastChildNode = childNode
        }

        if (childNode.key != null) {
            childNodesByKey[childNode.key] = childNode
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

        establishChildNodes(domNode)

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
     * @param priorNode the prior edition of this KatyDOM node.
     */
    internal fun patch(priorNode: KatyDomNode) {

        // Quit early if the node is the same (e.g. memoized).
        if (this === priorNode) {
            if (!isPatched) throw IllegalStateException("New KatyDOM node is identical to prior node but prior node has not been patched.")
            return
        }

        if (state < EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node must be fully constructed before establishing the real DOM.")
        if (state > EState.CONSTRUCTED) throw IllegalStateException("KatyDOM node already established.")

        if (priorNode.isPatched) throw IllegalStateException("Prior node cannot be patched twice.")
        if (!priorNode.isEstablished) throw IllegalStateException("Prior KatyDOM node must be established before patching.")

        if (priorNode.nodeName != nodeName) throw IllegalArgumentException("Cannot patch a difference between two KatyDOM nodes of different types.")

        val domNode = priorNode.domNode ?: throw IllegalStateException("Prior KatyDOM node is not linked to its DOM node.")

        // Patch the attributes.
        patchAttributes(domNode, priorNode)

        // Patch the child nodes.
        if (priorNode.firstChildNode == Nothing) {

            establish(domNode)

        }
        else if (firstChildNode == Nothing) {

            while (domNode.hasChildNodes()) {
                domNode.removeChild(domNode.firstChild!!)
            }

            this.domNode = domNode

            state = EState.ESTABLISHED

        }
        else if (childNodesByKey.isEmpty()) {

            patchChildNodes(domNode, priorNode)

        }
        else {

            patchChildNodesByKey(domNode, priorNode)

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

    protected abstract fun createDomNode(document: Document, domNode: Node, domChild: Node?)

    /**
     * Performs the DOM element configuration needed by a derived class.
     * @param domElement the real DOM element being built.
     */
    protected abstract fun establishAttributes(domElement: Node)

    /**
     * Removes the scaffolding of a derived class. Override as needed. Base class method does nothing.
     */
    protected abstract fun freezeAttributes()

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
    protected abstract fun patchAttributes(domElement: Node, priorElement: KatyDomNode)

////

    private fun establishChildNodes(domNode: Node) {

        val document: Document = domNode.ownerDocument ?: throw IllegalArgumentException("DOM element must have an owner document.")

        var childNode = firstChildNode

        while (childNode != Nothing) {

            childNode.createDomNode(document, domNode, null)

            childNode = childNode.nextSiblingNode

        }
    }

    /**
     * Patches the child nodes of the DOM with changes between this node and the prior node.
     */
    private fun patchChildNodes(domNode: Node, priorNode: KatyDomNode) {

        // TODO: The main point of virtual DOM is still TBD here: just patch the differences.

        while (domNode.hasChildNodes()) {
            domNode.removeChild(domNode.firstChild!!)
        }

        establishChildNodes(domNode)

    }

    /**
     * Patches the child nodes of the DOM with changes between this node and the prior node.
     */
    private fun patchChildNodesByKey(domNode: Node, priorNode: KatyDomNode) {

        var startChild = firstChildNode
        var endChild = lastChildNode

        var priorStartChild = priorNode.firstChildNode
        var priorEndChild = priorNode.lastChildNode

        var domChild = domNode.firstChild

        val document: Document = domNode.ownerDocument ?: throw IllegalArgumentException("DOM element must have an owner document.")

        while (startChild != endChild.nextSiblingNode) {

            if (startChild.key == priorStartChild.key) {

                domChild = domChild!!.nextSibling

                startChild.patch(priorStartChild)

                startChild = startChild.nextSiblingNode
                priorStartChild = priorStartChild.nextSiblingNode

            }
            else if (endChild.key == priorEndChild.key) {

                endChild.patch(priorEndChild)

                endChild = endChild.prevSiblingNode
                priorEndChild = priorEndChild.prevSiblingNode

            }
            else if (startChild.key == priorEndChild.key) {

                domNode.insertBefore(priorEndChild.domNode!!, domChild)

                startChild.patch(priorEndChild)

                startChild = startChild.nextSiblingNode
                priorEndChild = priorEndChild.prevSiblingNode

            }
            else if (endChild.key == priorStartChild.key) {

                if (endChild.nextSiblingNode == Nothing) {
                    domNode.insertBefore(priorStartChild.domNode!!, null)
                }
                else {
                    domNode.insertBefore(priorStartChild.domNode!!, endChild.nextSiblingNode.domNode)
                }

                endChild.patch(priorStartChild)

                endChild = endChild.prevSiblingNode
                priorStartChild = priorStartChild.nextSiblingNode

            }
            else {

                val priorChild = priorNode.childNodesByKey[startChild.key]
                if (priorChild != null) {

                    domNode.insertBefore(priorChild.domNode!!, domChild)

                    startChild.patch(priorChild)

                }
                else {

                    startChild.createDomNode(document, domNode, domChild)

                }

                startChild = startChild.nextSiblingNode

            }

        }

        while (priorStartChild != priorEndChild.nextSiblingNode) {

            if (childNodesByKey[priorStartChild.key] == null) {
                domNode.removeChild(priorStartChild.domNode!!)
            }

            priorStartChild = priorStartChild.nextSiblingNode

        }

    }

    /** A map of child nodes by their key. */
    private val childNodesByKey: MutableMap<String, KatyDomNode> = hashMapOf()

    /** The established DOM node after this node has been established or patched. */
    private var domNode: Node? = null

    /** The first child node within this node. Starts as Nothing, meaning no children. */
    private var firstChildNode: KatyDomNode = Nothing

    /** The last child node within this node. Starts as Nothing, meaning no children. */
    private var lastChildNode: KatyDomNode = Nothing

    /** The next sibling node within this node. Points to Nothing from last child in the list. */
    private var nextSiblingNode: KatyDomNode = Nothing

    /** The previous sibling node within this node. Linked to Nothing for first child in the list. */
    private var prevSiblingNode: KatyDomNode = Nothing

    /** Flag set to true once the node is fully constructed. */
    private var state: EState = EState.ADDING_ATTRIBUTES

    private object Nothing : KatyDomNode("org.katydom.abstractnodes.KatyDomNode.Nothing#key") {

        override val nodeName: String
            get() {
                throw UnsupportedOperationException("Cannot use Nothing")
            }

        override fun createDomNode(document: Document, domNode: Node, domChild: Node?) {
            throw UnsupportedOperationException("Cannot use Nothing")
        }

        override fun establishAttributes(domElement: Node) {
            throw UnsupportedOperationException("Cannot use Nothing")
        }

        override fun freezeAttributes() {
            throw UnsupportedOperationException("Cannot use Nothing")
        }

        override fun patchAttributes(domElement: Node, priorElement: KatyDomNode) {
            throw UnsupportedOperationException("Cannot use Nothing")
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
