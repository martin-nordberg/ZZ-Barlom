//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomNode
import org.w3c.dom.Comment
import org.w3c.dom.Document
import org.w3c.dom.Node

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM node for a comment node.
 */
internal class KatyDomComment<Msg>(

    /** The text within the comment. */
    _nodeValue: String,

    /** Unique identifier for this comment. */
    key: Any?

) : KatyDomNode<Msg>(key) {

    override val nodeName = "#comment"

    val nodeValue = _nodeValue

    init {
        this.freeze()
    }

////

    override fun createDomNode(document: Document, domNode: Node, domChild: Node?) {
        val childComment = document.createComment(nodeValue)
        establish(childComment)
        domNode.insertBefore(childComment, domChild)
    }

    override fun establishAttributes(domElement: Node) {
        // Nothing to establish; comments have no attributes.
    }

    override fun freezeAttributes() {
        // Nothing to freeze beyond the base node.
    }

    override fun patchAttributes(domElement: Node, priorElement: KatyDomNode<Msg>) {

        if (domElement !is Comment) throw IllegalArgumentException("DOM node expected to be comment.")
        if (priorElement !is KatyDomComment) throw IllegalArgumentException("KatyDOM node expected to be KatyDOM comment.")

        if (nodeValue != priorElement.nodeValue) {
            domElement.nodeValue = nodeValue
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
