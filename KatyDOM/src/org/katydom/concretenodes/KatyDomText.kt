//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomNode

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM node for a plain text node.
 */
internal class KatyDomText(

    /** The text within the node. */
    val textChars: String

) : KatyDomNode(null) {

    override val nodeName = "#text"

    override fun removeMoreScaffolding() {
        // no scaffolding needed; nothing to do
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
