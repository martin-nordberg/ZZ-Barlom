//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EButtonType
import org.katydom.types.EDirection
import org.katydom.types.EFormEncodingType
import org.katydom.types.EFormSubmissionMethod

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a button element.
 */
internal class KatyDomButton<Message>(
    phrasingContent: KatyDomPhrasingContentBuilder<Message>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    autofocus: Boolean?,
    contenteditable: Boolean?,
    dir: EDirection?,
    disabled: Boolean?,
    form: String?,
    formaction: String?,
    formenctype: EFormEncodingType?,
    formmethod: EFormSubmissionMethod?,
    formnovalidate: Boolean?,
    formtarget: String?,
    hidden: Boolean?,
    lang: String?,
    menu: String?,
    name: String?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    type: EButtonType?,
    value: String?,
    defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key ?: name, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "BUTTON"

    init {
        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("formaction", formaction)
        setAttribute("formenctype", formenctype?.toHtmlString())
        setAttribute("formmethod", formmethod?.toHtmlString())
        setBooleanAttribute("formnovalidate", formnovalidate)
        setAttribute("formtarget", formtarget)
        setAttribute("menu", menu)
        setAttribute("name", name)
        setAttribute("value", value)

        setAttribute("type", type?.toHtmlString())

        phrasingContent.withInteractiveContentNotAllowed(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

