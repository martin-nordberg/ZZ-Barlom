//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.concretenodes.grouping

import o.org.katydom.abstractnodes.KatyDomHtmlElement
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.builders.KatyDomListItemContentBuilder
import o.org.katydom.types.EDirection

//---------------------------------------------------------------------------------------------------------------------

/**
 * Virtual node for an unordered list <ul> element.
 */
internal class KatyDomUl<Msg>(
    flowContent: KatyDomFlowContentBuilder<Msg>,
    selector: String?,
    key: Any?,
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
    defineContent: KatyDomListItemContentBuilder<Msg>.() -> Unit
) : KatyDomHtmlElement<Msg>(selector, key, accesskey, contenteditable, dir,
                            hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {
        flowContent.listItemContent(this).defineContent()
        this.freeze()
    }

    ////

    override val nodeName = "UL"

}

//---------------------------------------------------------------------------------------------------------------------

