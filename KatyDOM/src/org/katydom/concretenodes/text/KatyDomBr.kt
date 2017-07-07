//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomAttributesContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <br> element.
 */
internal class KatyDomBr(
    phrasingContent: KatyDomPhrasingContentBuilder,
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
    defineAttributes: KatyDomAttributesContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key, accesskey, contenteditable, dir,
                       hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {
        phrasingContent.attributesContent(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "BR"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
