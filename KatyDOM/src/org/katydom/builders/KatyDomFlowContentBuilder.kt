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

/**
 * Virtual DOM builder for the normal case of HTML "flow content".
 */
class KatyDomFlowContentBuilder(

    /** The element whose content is being built. */
    private val element: KatyDomHtmlElement

) : KatyDomElementContentBuilder(element) {

    fun div(
        selector: String = "",
        key: String? = null,
        style: String? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomDiv(selector, key, style)
        KatyDomFlowContentBuilder(childElement).defineContent()
        childElement.removeScaffolding()
        element.addChildNode(childElement)
    }

    fun hr(
        selector: String = "",
        key: String? = null,
        style: String? = null,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomHr(selector, key, style)
        KatyDomFlowContentBuilder(childElement).defineAttributes()
        childElement.removeScaffolding()
        element.addChildNode(childElement)
    }

    fun text(textChars: String) {
        val textNode = KatyDomText(textChars)
        textNode.removeScaffolding()
        element.addChildNode(textNode)
    }

    fun ul(
        selector: String = "",
        key: String? = null,
        style: String? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomUl(selector, key, style)
        KatyDomListItemContentBuilder(childElement).defineContent()
        childElement.removeScaffolding()
        element.addChildNode(childElement)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

