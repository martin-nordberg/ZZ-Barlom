//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.concretenodes.KatyDomLi

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomListItemContentBuilder
    : KatyDomAttributeContentBuilder() {

    fun li(
        selector: String = "",
        style: String = "",
        fillChildNodes: KatyDomFlowContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomLi(selector, style, KatyDomFlowContentBuilder().apply { fillChildNodes() }.content))
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

