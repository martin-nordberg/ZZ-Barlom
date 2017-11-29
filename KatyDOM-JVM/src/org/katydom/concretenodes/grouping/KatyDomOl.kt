//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.grouping

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
    key: Any?,
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
) : KatyDomHtmlElement(selector, key, accesskey, contenteditable, dir,
                       hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {
        setBooleanAttribute("reversed", reversed)
        setNumberAttribute("start", start)
        setAttribute("type", type?.toHtmlString())

        KatyDomListItemContentBuilder(flowContent, true, this).defineContent()
        this.freeze()
    }

    override val nodeName = "OL"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

