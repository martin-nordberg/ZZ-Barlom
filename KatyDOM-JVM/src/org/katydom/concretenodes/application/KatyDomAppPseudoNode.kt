//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.application

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.abstractnodes.KatyDomNode
import org.katydom.builders.KatyDomFlowContentBuilder

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for the parent pseudo element of an application. The pseudo element allows only one child node to be
 * created. That child node is the root element of an application.
 */
internal class KatyDomAppPseudoNode :
    KatyDomHtmlElement(null, null, null, null, null,
                       null, null, null, null, null, null, null) {

    override val nodeName = "!APPLICATION"

    /**
     * Fills this node with the content that is the whole application. The [defineContent] function should add
     * exactly one child node that is to become the root node of the application. This pseudo node has no
     * corresponding real DOM node.
     */
    fun fill(defineContent: KatyDomFlowContentBuilder.() -> Unit) {

        KatyDomFlowContentBuilder(this).withNoAddedRestrictions(this).defineContent()

        require(this.isConstructed) {
            "Application node should be filled with exactly one child node."
        }

    }

    override fun afterAddChildNode(childNode: KatyDomNode) {

        // Allow only one child node
        freeze()

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
