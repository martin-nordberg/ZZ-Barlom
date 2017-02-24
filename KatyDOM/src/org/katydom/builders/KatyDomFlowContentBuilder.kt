//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomNode
import org.katydom.concretenodes.KatyDomDiv
import org.katydom.concretenodes.KatyDomHr
import org.katydom.concretenodes.KatyDomText
import org.katydom.concretenodes.KatyDomUl

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomFlowContentBuilder
    : KatyDomElementContentBuilder() {

    val soleNode: KatyDomNode
        get() = content.soleChildNode

    fun div(
        selector: String = "",
        style: String? = null,
        fillChildNodes: KatyDomFlowContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomDiv(
            selector,
            style,
            KatyDomFlowContentBuilder().apply { fillChildNodes() }.content
        ))
    }

    fun hr(
        selector: String = "",
        style: String? = null,
        fillAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomHr(
            selector,
            style,
            KatyDomElementContentBuilder().apply { fillAttributes() }.content
        ))
    }

    fun text(textChars: String) {
        content.addChildNode(KatyDomText(textChars))
    }

    fun ul(
        selector: String = "",
        style: String? = null,
        fillChildNodes: KatyDomListItemContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomUl(
            selector,
            style,
            KatyDomListItemContentBuilder().apply { fillChildNodes() }.content
        ))
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

