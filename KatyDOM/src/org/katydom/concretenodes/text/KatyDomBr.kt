//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomElementContentBuilder
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <br> element.
 */
internal class KatyDomBr
    : KatyDomHtmlElement {

    constructor(
        @Suppress("UNUSED_PARAMETER") flowContentBuilder: KatyDomFlowContentBuilder,
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
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex,
        title, translate) {

        KatyDomElementContentBuilder(this).defineAttributes()
        this.freeze()
    }

    constructor(
        @Suppress("UNUSED_PARAMETER") phrasingContentBuilder: KatyDomPhrasingContentBuilder,
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
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex,
        title, translate) {

        KatyDomElementContentBuilder(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "BR"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
