//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <span> element.
 */
internal class KatyDomSpan : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String? = null,
        key: String? = null,
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
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        flowContent.phrasingContent(this).defineContent()
        this.freeze()
    }

    constructor(
        phrasingContent: KatyDomPhrasingContentBuilder,
        selector: String? = null,
        key: String? = null,
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
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        phrasingContent.withNoAddedRestrictions(this).defineContent()
        this.freeze()
    }

    override val nodeName = "SPAN"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
