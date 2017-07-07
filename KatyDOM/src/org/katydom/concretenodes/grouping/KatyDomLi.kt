//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.grouping

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an <li> element.
 */
internal class KatyDomLi(
    listContent: KatyDomListItemContentBuilder,
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
    value: Int?,
    defineContent: KatyDomFlowContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key, accesskey, contenteditable, dir,
                       hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {

        if (value != null) {
            check(listContent.isOrdered) { "Only ordered list items can have value attributes." }
            setAttribute("value", value.toString())
        }

        listContent.flowContent.withNoAddedRestrictions(this).defineContent()
        this.freeze()

    }

    override val nodeName = "LI"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
