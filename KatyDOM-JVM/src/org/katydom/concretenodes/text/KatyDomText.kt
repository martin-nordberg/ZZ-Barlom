//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomNode
import org.katydom.dom.Document
import org.katydom.dom.Node
import org.katydom.dom.Text

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM node for a plain text node.
 */
internal class KatyDomText<Msg>(

    /** The text within the node. */
    val nodeValue: String

) : KatyDomNode<Msg>(null) {

    override val nodeName = "#text"

    init {
        this.freeze()
    }

////

    override fun createDomNode(document: Document, domNode: Node, domChild: Node?) {
        val childText = document.createTextNode(nodeValue)
        establish(childText)
        domNode.insertBefore(childText, domChild)
    }

    override fun establishAttributes(domElement: Node) {
        // Nothing to establish; text already set during node creation.
    }

    override fun freezeAttributes() {
        // Nothing to freeze beyond the base node.
    }

    override fun patchAttributes(domElement: Node, priorElement: KatyDomNode<Msg>) {

        if (domElement !is Text) throw IllegalArgumentException("DOM node expected to be text.")
        if (priorElement !is KatyDomText) throw IllegalArgumentException("KatyDOM node expected to be KatyDOM text.")

        if (nodeValue != priorElement.nodeValue) {
            domElement.nodeValue = nodeValue
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
