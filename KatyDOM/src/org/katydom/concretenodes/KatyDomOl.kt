//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder
import org.katydom.types.EDirection
import org.katydom.types.EOrderedListType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an ordered list <ol> element.
 */
internal class KatyDomOl(
        flowContent: KatyDomFlowContentBuilder,
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
        type: EOrderedListType?,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "OL"

    init {
        setBooleanAttribute("reversed", reversed)
        setAttribute("start", start?.toString())
        setAttribute("type", type?.toHtmlString())

        KatyDomListItemContentBuilder(flowContent,this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

