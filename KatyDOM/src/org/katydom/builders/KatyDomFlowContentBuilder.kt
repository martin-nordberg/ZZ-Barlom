//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.KatyDomDiv
import org.katydom.concretenodes.KatyDomHr
import org.katydom.concretenodes.KatyDomText
import org.katydom.concretenodes.KatyDomUl

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomFlowContentBuilder(private val element: KatyDomHtmlElement)
    : KatyDomElementContentBuilder(element) {

    fun div(
        selector: String = "",
        style: String? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomDiv(selector, style)
        KatyDomFlowContentBuilder(childElement).defineContent()
        element.addChildNode(childElement)
    }

    fun hr(
        selector: String = "",
        style: String? = null,
        defineContent: KatyDomElementContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomHr(selector, style)
        KatyDomFlowContentBuilder(childElement).defineContent()
        element.addChildNode(childElement)
    }

    fun text(textChars: String) {
        element.addChildNode(KatyDomText(textChars))
    }

    fun ul(
        selector: String = "",
        style: String? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomUl(selector, style)
        KatyDomListItemContentBuilder(childElement).defineContent()
        element.addChildNode(childElement)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

