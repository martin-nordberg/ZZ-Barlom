//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a label element.
 */
internal class KatyDomLabel<Message>(
    phrasingContent: KatyDomPhrasingContentBuilder<Message>,
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
    defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "LABEL"

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()
        phrasingContent.contentRestrictions.confirmLabelAllowed()

        setAttribute("for", `for`)

        phrasingContent.withLabelNotAllowed(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

