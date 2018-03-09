//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.grouping

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <div> element.
 */
internal class KatyDomDiv(
    flowContent: KatyDomFlowContentBuilder? = null,
    selector: String? = null,
    key: Any? = null,
    accesskey: String? = null,
    contenteditable: Boolean? = null,
    dir: EDirection? = null,
    hidden: Boolean? = null,
    lang: String? = null,
    spellcheck: Boolean? = null,
    style: String? = null,
    tabindex: Int? = null,
    title: String? = null,
    translate: Boolean? = null,
    defineContent: KatyDomFlowContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key, accesskey, contenteditable, dir,
                       hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "DIV"

    init {
        (flowContent ?: KatyDomFlowContentBuilder(this)).withNoAddedRestrictions(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
