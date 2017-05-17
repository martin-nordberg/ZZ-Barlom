//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an <nav> element.
 */
internal class KatyDomNav : KatyDomHtmlElement {

    /**
     * Constructs a new article element with minimal attributes.
     */
    constructor(
        selector: String?,
        key: String?,
        style: String?
    ) : super(selector, key, style)

    /**
     * Constructs a new article element with global elements beyond id and class.
     */
    constructor(
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
        translate: Boolean?
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate)

    override val nodeName = "NAV"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
