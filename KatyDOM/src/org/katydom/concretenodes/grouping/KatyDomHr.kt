//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.grouping

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomElementContentBuilder
import org.katydom.builders.KatyDomFlowContentBuilder

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an <hr> element.
 */
internal class KatyDomHr(
        selector: String?,
        key: String?,
        style: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key, style=style) {

    override val nodeName = "HR"

    init {
        KatyDomElementContentBuilder(this).defineAttributes()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
