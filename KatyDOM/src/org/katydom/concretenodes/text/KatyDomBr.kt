//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomElementContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <br> element.
 */
internal class KatyDomBr(
    selector: String?,
    key: String?,
    accesskey: String?,
    contenteditable: Boolean?,
    dir: EDirection?,
    hidden: Boolean?,
    lang: String?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    defineAttributes: KatyDomElementContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex,
                       title, translate) {

    override val nodeName = "BR"

    init {
        KatyDomElementContentBuilder(this).defineAttributes()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
