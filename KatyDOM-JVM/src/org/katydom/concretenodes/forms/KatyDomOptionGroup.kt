//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.*
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an optgroup element.
 */
internal class KatyDomOptionGroup<Message>(
    optionContent: KatyDomOptionContentBuilder<Message>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    contenteditable: Boolean?,
    dir: EDirection?,
    disabled: Boolean?,
    hidden: Boolean?,
    label: String?,
    lang: String?,
    name: String?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    defineContent: KatyDomOptionContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key ?: name, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "OPTGROUP"

    init {
        optionContent.contentRestrictions.confirmOptionGroupAllowed()

        require(label == null || !label.isEmpty()) { "Attribute label may not be an empty string." }

        setBooleanAttribute("disabled", disabled)
        setAttribute("label", label)

        optionContent.withOptionGroupNotAllowed(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

