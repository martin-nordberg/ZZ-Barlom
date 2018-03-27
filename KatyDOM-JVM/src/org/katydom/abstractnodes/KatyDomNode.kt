//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

// Note: these are needed in JVM but not JS:
import org.katydom.api.EventCancellationException
import org.katydom.api.EventHandler
import org.katydom.api.MouseEventHandler
import org.katydom.eventtarget.addEventListener
import org.katydom.eventtarget.removeEventListener
import org.katydom.types.EEventType
import org.katydom.types.EMouseEventType
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Topmost abstract base class for KatyDOM virtual DOM. Corresponds to DOM Node.
 * @param _key a key for this KatyDOM node that is unique among all the siblings of this node.
 */
abstract class KatyDomNode<Message>(private val _key: Any?) {

    /** Returns the key for this node. If none provided, uses the node name. */
    val key: Any
        get() = _key ?: nodeName

    /** Returns the first and only child node in this node. (Exception if there is none or more than one.) */
    val soleChildNode: KatyDomNode<Message>
        get() {

            check(firstChildNode != null) { "No child found." }
            check(firstChildNode == lastChildNode) { "More than one child node found." }

            return firstChildNode!!

        }

    /** The name of this node (usually the HTML tag name, otherwise a pseudo tag name like "#text"). */
    abstract val nodeName: String

    override fun toString(): String {
        return "<" + nodeName.toLowerCase() + ">"
    }

////

    /**
     * Adds a new child node to this node.
     * @param childNode the child node to add.
     */
    internal fun addChildNode(childNode: KatyDomNode<Message>) {

        if (isAddingAttributes) {
            freezeAttributes()
            state = EState.ADDING_CHILD_NODES
        }
        else if (isAddingEventHandlers) {
            state = EState.ADDING_CHILD_NODES
        }
        else {
            check(isAddingChildNodes) { "Cannot modify a KatyDOM node after it has been fully constructed." }
        }

        require(!childNodesByKey.containsKey(childNode.key)) {
            "Duplicate key: " + childNode.key + " in " + this.toString()
        }

        if (firstChildNode == null) {
            firstChildNode = childNode
            lastChildNode = childNode
        }
        else {
            childNode.prevSiblingNode = lastChildNode
            lastChildNode!!.nextSiblingNode = childNode
            lastChildNode = childNode
        }

        childNodesByKey[childNode.key] = childNode

        afterAddChildNode(childNode)

    }

    /**
     * Adds a mouse event handler for the given type of mouse event.
     * @param eventType the kind of event
     * @param handler the callback when the vent occurs
     */
    internal fun addMouseEventHandler(eventType: EMouseEventType, handler: MouseEventHandler) {

        if (isAddingAttributes) {
            freezeAttributes()
            state = EState.ADDING_CHILD_NODES
        }
        else {
            check(isAddingEventHandlers) { "KatyDOM node's event handlers must be defined before its child nodes." }
        }

        eventHandlers.put(
            eventType.domName,
            { event: Event ->
                try {
                    handler(event as MouseEvent)
                }
                catch (exception: EventCancellationException) {
                    event.preventDefault()
                }
            }
        )

    }

    /**
     * Adds a general event handler for the given type of event.
     * @param eventType the kind of event
     * @param handler the callback when the vent occurs
     */
    internal fun addEventHandler(eventType: EEventType, handler: EventHandler) {

        if (isAddingAttributes) {
            freezeAttributes()
            state = EState.ADDING_EVENT_HANDLERS
        }
        else {
            check(isAddingEventHandlers) { "KatyDOM node's event handlers must be defined before its child nodes." }
        }

        eventHandlers.put(
            eventType.domName,
            { event: Event ->
                try {
                    handler(event)
                }
                catch (exception: EventCancellationException) {
                    event.preventDefault()
                }
            }
        )

    }

    /**
     * Performs extra handling after a [childNode] has been added.
     */
    protected open fun afterAddChildNode(childNode: KatyDomNode<Message>) {

    }

    /**
     * Sets the attributes and child nodes of a newly created real DOM node to match this virtual DOM node.
     * @param domNode the real DOM node to be configured to mirror this virtual DOM node.
     */
    internal fun establish(domNode: Node) {

        check(state >= EState.CONSTRUCTED) {
            "KatyDOM node must be fully constructed before establishing the real DOM."
        }
        check(state <= EState.CONSTRUCTED) {
            "KatyDOM node already established."
        }

        require(domNode.nodeName == nodeName) {
            "Cannot establish a real DOM node differing in type from the KatyDOM node."
        }

        if (nodeName != "#text") {
            establishAttributes(domNode)
            establishEventHandlers(domNode)
            establishChildNodes(domNode)
        }

        this.domNode = domNode

        state = EState.ESTABLISHED

    }

    /**
     * Patches a real DOM node by determining the difference between this KatyDOM node and its prior edition.
     * @param priorNode the prior edition of this KatyDOM node.
     */
    internal fun patch(priorNode: KatyDomNode<Message>) {

        // Quit early if the node is the same (e.g. memoized).
        if (this === priorNode) {
            check(isPatchedReplaced) {
                "New KatyDOM node is identical to prior node but prior node has not been patched."
            }
            return
        }

        check(state >= EState.CONSTRUCTED) {
            "KatyDOM node must be fully constructed before establishing the real DOM."
        }
        check(state <= EState.CONSTRUCTED) {
            "KatyDOM node already established."
        }

        check(!priorNode.isPatchedRemoved) {
            "Prior node cannot be patched after being removed."
        }
        check(!priorNode.isPatchedReplaced) {
            "Prior node cannot be patched twice."
        }
        check(priorNode.isEstablished) {
            "Prior KatyDOM node must be established before patching. " + priorNode.nodeName + "." + priorNode.key
        }

        require(priorNode.nodeName == nodeName) {
            "Cannot patch a difference between two KatyDOM nodes of different types."
        }

        val domNode = checkNotNull(priorNode.domNode) {
            "Prior KatyDOM node is not linked to its DOM node."
        }

        // Patch the attributes.
        patchAttributes(domNode, priorNode)

        // Patch the event handlers
        patchEventHandlers(domNode, priorNode)

        // Patch the child nodes.
        if (priorNode.firstChildNode == null) {

            establishChildNodes(domNode)

        }
        else if (firstChildNode == null) {

            while (domNode.hasChildNodes()) {
                domNode.removeChild(domNode.firstChild!!)
            }

        }
        else {

            patchChildNodes(domNode, priorNode)

        }

        this.domNode = domNode
        state = EState.ESTABLISHED

        priorNode.state = EState.PATCHED_REPLACED

    }

////

    /**
     * States in the lifecycle of a node.
     */
    protected enum class EState {
        /** The node is still open for its attributes to be set by one of the KatyDOM builders. */
        ADDING_ATTRIBUTES,
        /** The node is being modified for event handling. */
        ADDING_EVENT_HANDLERS,
        /** The node's children are still under construction. */
        ADDING_CHILD_NODES,
        /** The node has been fully defined and is ready to be established in the real DOM. */
        CONSTRUCTED,
        /** The node has been established in the real DOM (either the first edition or replacing a prior edition). */
        ESTABLISHED,
        /** The node has been replaced by a newer edition in the real DOM. */
        PATCHED_REPLACED,
        /** The node does not appear in a newer edition. */
        PATCHED_REMOVED
    }

    protected abstract fun createDomNode(document: Document, domNode: Node, domChild: Node?)

    /**
     * Performs the DOM element configuration needed by a derived class.
     * @param domElement the real DOM element being built.
     */
    protected abstract fun establishAttributes(domElement: Node)

    /**
     * Freezes the content of this node. Makes any further attempt to add child nodes or attributes fail.
     */
    protected fun freeze() {

        check(state <= EState.CONSTRUCTED) { "KatyDOM node already fully constructed." }

        if (isAddingAttributes) {
            freezeAttributes()
        }

        state = EState.CONSTRUCTED

    }

    /**
     * Removes the scaffolding of a derived class. Override as needed. Base class method does nothing.
     */
    protected abstract fun freezeAttributes()

    /** Whether this node is still having its attributes set. */
    protected val isAddingAttributes
        get() = state == EState.ADDING_ATTRIBUTES

    /** Whether this node is still being built. */
    protected val isAddingChildNodes
        get() = state == EState.ADDING_CHILD_NODES

    /** Whether this node is still having its event handlers set. */
    protected val isAddingEventHandlers
        get() = state == EState.ADDING_EVENT_HANDLERS

    /** Whether this node is fully constructed. */
    protected val isConstructed
        get() = state == EState.CONSTRUCTED

    /** Whether this node has been established in the real DOM. */
    protected val isEstablished
        get() = state == EState.ESTABLISHED

    /** Whether this node has been removed from a later edition in the real DOM. */
    protected val isPatchedRemoved
        get() = state == EState.PATCHED_REMOVED

    /** Whether this node has been replaced by a later edition in the real DOM. */
    protected val isPatchedReplaced
        get() = state == EState.PATCHED_REPLACED

    /**
     * Performs the patch needed by a derived class. Override as needed. Base class method does nothing.
     * @param domElement the real DOM node being patched.
     * @param priorElement the prior edition of this KatyDOM node from which to compute the patch.
     */
    protected abstract fun patchAttributes(domElement: Node, priorElement: KatyDomNode<Message>)

////

    /**
     * Establishes child nodes in the given DOM node matching the child nodes of this KatyDOM node.
     */
    private fun establishChildNodes(domNode: Node) {

        val document = checkNotNull(domNode.ownerDocument, { "DOM element must have an owner document." })

        var childNode = firstChildNode

        while (childNode != null) {

            childNode.createDomNode(document, domNode, null)

            childNode = childNode.nextSiblingNode

        }
    }

    /**
     * Establishes event handlers for the given domNode from the event handlers of this KatyDOM node.
     */
    private fun establishEventHandlers(domNode: Node) {

        for ((key, eventHandler) in eventHandlers) {
            domNode.addEventListener(
                key,
                eventHandler
            )
        }

    }

    /**
     * Whether this KatyDOM node matches the given KatyDOM node for purposes of patching.
     * @param otherNode the node to compare with.
     */
    private fun matches(otherNode: KatyDomNode<Message>?): Boolean {

        if (otherNode == null) {
            return false
        }

        if (nodeName != otherNode.nodeName) {
            return false
        }

        return key == otherNode.key

    }

    /**
     * Patches the child nodes of the DOM with changes between this node and the prior node.
     * @param domNode the DOM node to patch changes into.
     * @param priorNode the prior edition of this KatyDOM node that corresponds to the given domNode.
     */
    private fun patchChildNodes(domNode: Node, priorNode: KatyDomNode<Message>) {

        // Shrinking interval of child nodes of this node.
        var startChild = firstChildNode
        var endChild = lastChildNode

        // Shrinking internal of child nodes of prior node.
        var priorStartChild = priorNode.firstChildNode
        var priorEndChild = priorNode.lastChildNode

        // First existing DOM node - moves forward as the interval shrinks in the front
        var domChild = domNode.firstChild

        val document = checkNotNull(domNode.ownerDocument) { "DOM element must have an owner document." }

        while (startChild != null && endChild != null && startChild != endChild.nextSiblingNode && endChild != startChild.prevSiblingNode) {

            if (startChild.matches(priorStartChild)) {

                domChild = domChild!!.nextSibling

                startChild.patch(priorStartChild!!)

                startChild = startChild.nextSiblingNode
                priorStartChild = priorStartChild.nextSiblingNode

            }
            else if (endChild.matches(priorEndChild)) {

                endChild.patch(priorEndChild!!)

                endChild = endChild.prevSiblingNode
                priorEndChild = priorEndChild.prevSiblingNode

            }
            else if (startChild.matches(priorEndChild)) {

                domNode.insertBefore(priorEndChild!!.domNode!!, domChild)

                startChild.patch(priorEndChild)

                startChild = startChild.nextSiblingNode
                priorEndChild = priorEndChild.prevSiblingNode

            }
            else if (endChild.matches(priorStartChild)) {

                if (endChild.nextSiblingNode == null) {
                    domNode.insertBefore(priorStartChild!!.domNode!!, null)
                }
                else {
                    domNode.insertBefore(priorStartChild!!.domNode!!, endChild.nextSiblingNode!!.domNode)
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

        // Delete any obsolete prior nodes.
        while (priorStartChild != null && priorEndChild != null && priorStartChild != priorEndChild.nextSiblingNode) {

            if (priorStartChild.isEstablished) {
                domNode.removeChild(priorStartChild.domNode!!)
                priorStartChild.state = EState.PATCHED_REMOVED
            }

            priorStartChild = priorStartChild.nextSiblingNode

        }

    }

    /**
     * Patches the event handlers of the DOM with changes between this node and the prior node.
     * @param domNode the DOM node to patch changes into.
     * @param priorNode the prior edition of this KatyDOM node that corresponds to the given domNode.
     */
    private fun patchEventHandlers(domNode: Node, priorNode: KatyDomNode<Message>) {

        // TODO: Need the event handlers to implement equals

        // Establish the new event listeners
        for ((key, eventHandler) in eventHandlers) {
            if (eventHandler != priorNode.eventHandlers[key]) {
                val priorEventHandler = priorNode.eventHandlers[key]
                if (priorEventHandler != null) {
                    domNode.removeEventListener(key, priorEventHandler)
                }
                domNode.addEventListener(key, eventHandler)
            }
        }

        // Kill off the old ones
        for ((key, priorEventHandler) in priorNode.eventHandlers) {
            if (!eventHandlers.contains(key)) {
                domNode.removeEventListener(key, priorEventHandler)
            }
        }


    }

    /** A map of child nodes by their key. */
    private val childNodesByKey: MutableMap<Any, KatyDomNode<Message>> = hashMapOf()

    /** The established DOM node after this node has been established or patched. */
    private var domNode: Node? = null

    /** A map of registered event handlers. */
    private val eventHandlers: MutableMap<String, EventHandler> = mutableMapOf()

    /** The first child node within this node. Starts as Nothing, meaning no children. */
    private var firstChildNode: KatyDomNode<Message>? = null

    /** The last child node within this node. Starts as Nothing, meaning no children. */
    private var lastChildNode: KatyDomNode<Message>? = null

    /** The next sibling node within this node. Points to Nothing from last child in the list. */
    private var nextSiblingNode: KatyDomNode<Message>? = null

    /** The previous sibling node within this node. Linked to Nothing for first child in the list. */
    private var prevSiblingNode: KatyDomNode<Message>? = null

    /** Flag set to true once the node is fully constructed. */
    private var state: EState = EState.ADDING_ATTRIBUTES

}

