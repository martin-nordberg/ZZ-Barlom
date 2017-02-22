//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomNode
import org.katydom.concretenodes.KatyDomDiv
import org.katydom.concretenodes.KatyDomText

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomFlowContentBuilder
    : KatyDomAttributeContentBuilder() {

    val soleNode: KatyDomNode
        get() = content.soleChildNode

    fun div(
        selector: String = "",
        style: String = "",
        fillChildNodes: KatyDomFlowContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomDiv(selector, style, KatyDomFlowContentBuilder().apply { fillChildNodes() }.content))
    }

    fun hr(
        selector: String = "",
        style: String = "",
        fillAttributes: KatyDomAttributeContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomDiv(selector, style, KatyDomAttributeContentBuilder().apply { fillAttributes() }.content))
    }

    fun text(textChars: String) {
        content.addChildNode(KatyDomText(textChars))
    }

    fun ul(
        selector: String = "",
        style: String = "",
        fillChildNodes: KatyDomListItemContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomDiv(selector, style, KatyDomListItemContentBuilder().apply { fillChildNodes() }.content))
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

