//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Topmost abstract base class for KatyDOM virtual DOM. Corresponds to DOM Node.
 */
abstract class KatyDomNode {

    /** The child nodes within this node. Starts as an empty list. */
    val childNodes: Iterable<KatyDomNode>

    /** The name of this node (usually the HTML tag name, otherwise a pseudo tag name like "#text"). */
    abstract val nodeName: String

    ////

    private class Scaffolding(
        val childNodes: MutableList<KatyDomNode> = arrayListOf()
    )

    private var _scaffolding: Scaffolding?

    init {
        val sc = Scaffolding()
        _scaffolding = sc
        childNodes = sc.childNodes
    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomNode.")

    /**
     * Adds a new child node to this node.
     */
    internal fun addChildNode(childNode: KatyDomNode) {
        scaffolding.childNodes.add(childNode)
    }

    internal fun removeScaffolding() {
        _scaffolding = null
        removeScaffolding2()
    }

    abstract protected fun removeScaffolding2()

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
