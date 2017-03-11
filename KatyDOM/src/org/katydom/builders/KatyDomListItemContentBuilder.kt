//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.KatyDomLi

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomListItemContentBuilder(private val element: KatyDomHtmlElement)
    : KatyDomElementContentBuilder(element) {

    fun li(
        selector: String = "",
        key: String? = null,
        style: String? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomLi(selector, key, style)
        KatyDomFlowContentBuilder(childElement).defineContent()
        childElement.freeze()
        element.addChildNode(childElement)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
