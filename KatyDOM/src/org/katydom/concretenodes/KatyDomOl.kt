//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.types.EDirection
import org.katydom.types.EOrderedListType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an ordered list <ol> element.
 */
internal class KatyDomOl : KatyDomHtmlElement {

    /**
     * Constructs a new ordered list element with minimal attributes.
     */
    constructor(
        selector: String?,
        key: String?,
        reversed: Boolean?,
        start: Int?,
        style: String?,
        type: EOrderedListType?
    ) : super(selector, key, style) {

        setBooleanAttribute("reversed", reversed)
        setAttribute("start", start?.toString())
        setAttribute("type", type?.toHtmlString())

    }

    /**
     * Constructs a new ordered list element with full attributes.
     */
    constructor(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        hidden: Boolean?,
        lang: String?,
        reversed: Boolean?,
        spellcheck: Boolean?,
        start: Int?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        type: EOrderedListType?
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setBooleanAttribute("reversed", reversed)
        setAttribute("start", start?.toString())
        setAttribute("type", type?.toHtmlString())

    }


    override val nodeName = "OL"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

