//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Topmost abstract base class for KatyDOM virtual DOM. Corresponds to DOM Node.
 */
abstract class KatyDomNode(val key:String?) {

    /** The child nodes within this node. Starts as an empty list. */
    val childNodes: List<KatyDomNode>

    /** A map of child nodes by their key. */
    val childNodesByKey: Map<String, KatyDomNode>

    /** The name of this node (usually the HTML tag name, otherwise a pseudo tag name like "#text"). */
    abstract val nodeName: String

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

    /**
     * Adds a new child node to this node.
     */
    internal fun addChildNode(childNode: KatyDomNode) {

        scaffolding.childNodes.add(childNode)

        if ( childNode.key != null) {
            scaffolding.childNodesByKey.put(childNode.key, childNode)
        }

    }

    /**
     * Freezes the content of this node. Makes any further attempt to add child nodes fail.
     */
    internal fun removeScaffolding() {
        _scaffolding = null
        removeMoreScaffolding()
    }

    /**
     * Removes the scaffolding of a derived class.
     */
    abstract protected fun removeMoreScaffolding()

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
