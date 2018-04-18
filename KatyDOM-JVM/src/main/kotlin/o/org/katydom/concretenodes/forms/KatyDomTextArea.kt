//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.concretenodes.forms

import o.org.katydom.abstractnodes.KatyDomHtmlElement
import o.org.katydom.builders.KatyDomPhrasingContentBuilder
import o.org.katydom.builders.KatyDomTextContentBuilder
import o.org.katydom.types.EDirection
import o.org.katydom.types.EInputMode
import o.org.katydom.types.EWrapType

//---------------------------------------------------------------------------------------------------------------------

/**
 * Virtual node for a textarea element.
 */
internal class KatyDomTextArea<Msg>(
    phrasingContent: KatyDomPhrasingContentBuilder<Msg>,
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
    defineContent: KatyDomTextContentBuilder<Msg>.() -> Unit
) : KatyDomHtmlElement<Msg>(selector, key ?: name, accesskey, contenteditable, dir,
                            hidden, lang, spellcheck, style, tabindex, title, translate) {

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

    ////

    override val nodeName = "TEXTAREA"

}

//---------------------------------------------------------------------------------------------------------------------

