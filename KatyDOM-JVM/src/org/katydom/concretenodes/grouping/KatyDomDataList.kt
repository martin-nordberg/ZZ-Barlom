//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.grouping

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.builders.KatyDomOptionContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <datalist> element.
 */
internal class KatyDomDataList<Message>(
    flowContent: KatyDomPhrasingContentBuilder<Message>? = null,
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
    defineContent: KatyDomOptionContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "DATALIST"

    init {
        (flowContent ?: KatyDomPhrasingContentBuilder(this)).optionContent(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
