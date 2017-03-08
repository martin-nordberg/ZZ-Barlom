//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomNode
import org.w3c.dom.Node
import org.w3c.dom.Text

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM node for a plain text node.
 */
internal class KatyDomText(

    /** The text within the node. */
    val nodeValue: String

) : KatyDomNode(null) {

    override val nodeName = "#text"

////

    override fun patch2(domElement: Node, priorElement: KatyDomNode) {

        if (domElement !is Text) throw IllegalArgumentException( "DOM node expected to be text." )
        if (priorElement !is KatyDomText) throw IllegalArgumentException( "KatyDOM node expected to be KatyDOM text.")

        if ( domElement.nodeValue != priorElement.nodeValue) {
            domElement.nodeValue = priorElement.nodeValue
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
