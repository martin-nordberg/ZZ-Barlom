//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.concretenodes.KatyDomLi

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomListItemContentBuilder
    : KatyDomElementContentBuilder() {

    fun li(
        selector: String = "",
        style: String? = null,
        fillChildNodes: KatyDomFlowContentBuilder.() -> Unit
    ) {
        content.addChildNode(KatyDomLi(
            selector,
            style,
            KatyDomFlowContentBuilder().apply { fillChildNodes() }.content
        ))
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

