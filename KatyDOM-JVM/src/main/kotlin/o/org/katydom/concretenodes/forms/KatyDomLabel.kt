//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.concretenodes.forms

import o.org.katydom.abstractnodes.KatyDomHtmlElement
import o.org.katydom.builders.KatyDomPhrasingContentBuilder
import o.org.katydom.types.EDirection

//---------------------------------------------------------------------------------------------------------------------

/**
 * Virtual node for a label element.
 */
internal class KatyDomLabel<Msg>(
    phrasingContent: KatyDomPhrasingContentBuilder<Msg>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    contenteditable: Boolean?,
    dir: EDirection?,
    `for`: String?,
    hidden: Boolean?,
    lang: String?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
) : KatyDomHtmlElement<Msg>(selector, key, accesskey, contenteditable, dir,
                            hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()
        phrasingContent.contentRestrictions.confirmLabelAllowed()

        setAttribute("for", `for`)

        phrasingContent.withLabelNotAllowed(this).defineContent()
        this.freeze()
    }

    ////

    override val nodeName = "LABEL"

}

//---------------------------------------------------------------------------------------------------------------------

