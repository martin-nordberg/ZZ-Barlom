//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.browserspi

import org.katydom.spi.DocumentProxy
import org.katydom.spi.NodeProxy
import org.w3c.dom.Node

/**
 * Implementation of ElementProxy for the browser DOM.
 */
open class DomNodeProxy(val node: Node) : NodeProxy {

    override fun appendChild(childNode: NodeProxy): NodeProxy {
        return DomNodeProxy(node.appendChild((childNode as DomNodeProxy).node))
    }

    override fun insertBefore(newChildNode: NodeProxy, referenceChildNode: NodeProxy?): NodeProxy {
        return DomNodeProxy(node.insertBefore((newChildNode as DomNodeProxy).node, (referenceChildNode as DomNodeProxy).node))
    }

    override val ownerDocument: DocumentProxy
        get() = DomDocumentProxy(node.ownerDocument ?: throw IllegalStateException("Encountered node without a document"))

    override val parentNode: NodeProxy?
        get() {
            val result = node.parentNode
            return if (result == null) null else DomNodeProxy(result)
        }

    override fun removeChild(childNode: NodeProxy): NodeProxy {
        node.removeChild((childNode as DomNodeProxy).node)
        return childNode
    }

}