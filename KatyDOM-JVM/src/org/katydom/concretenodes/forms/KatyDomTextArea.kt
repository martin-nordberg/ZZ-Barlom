//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.builders.KatyDomTextContentBuilder
import org.katydom.types.EDirection
import org.katydom.types.EInputMode
import org.katydom.types.EWrapType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a textarea element.
 */
internal class KatyDomTextArea<Message>(
    phrasingContent: KatyDomPhrasingContentBuilder<Message>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    autocomplete: String?,
    autofocus: Boolean?,
    cols: Int?,
    contenteditable: Boolean?,
    dir: EDirection?,
    dirname: String?,
    disabled: Boolean?,
    form: String?,
    hidden: Boolean?,
    inputmode: EInputMode?,
    lang: String?,
    maxlength: Int?,
    minlength: Int?,
    name: String?,
    placeholder: String?,
    readonly: Boolean?,
    required: Boolean?,
    rows: Int?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    wrap: EWrapType?,
    defineContent: KatyDomTextContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key ?: name, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "TEXTAREA"

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        require(cols == null || cols > 0) { "Attribute cols must be greater than zero." }
        require(maxlength == null || maxlength >= 0) { "Attribute maxlength must be non-negative." }
        require(minlength == null || minlength >= 0) { "Attribute minlength must be non-negative." }
        require(rows == null || rows > 0) { "Attribute rows must be greater than zero." }

        setAttribute("autocomplete", autocomplete)
        setBooleanAttribute("autofocus", autofocus)
        setNumberAttribute("cols", cols)
        setAttribute("dirname", dirname)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("inputmode", inputmode?.toHtmlString())
        setNumberAttribute("maxlength", maxlength)
        setNumberAttribute("minlength", minlength)
        setAttribute("name", name)
        setAttribute("placeholder", placeholder)
        setBooleanAttribute("readonly", readonly)
        setBooleanAttribute("required", required)
        setNumberAttribute("rows", rows)
        setAttribute("wrap", wrap?.toHtmlString())

        phrasingContent.textContent(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

