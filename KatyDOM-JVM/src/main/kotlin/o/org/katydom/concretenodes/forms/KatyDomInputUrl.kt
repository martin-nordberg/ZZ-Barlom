//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.concretenodes.forms

import o.org.katydom.abstractnodes.KatyDomHtmlElement
import o.org.katydom.builders.KatyDomAttributesContentBuilder
import o.org.katydom.builders.KatyDomPhrasingContentBuilder
import o.org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an input type="url" element.
 */
internal class KatyDomInputUrl<Msg>(
    phrasingContent: KatyDomPhrasingContentBuilder<Msg>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    autocomplete: String?,
    autofocus: Boolean?,
    contenteditable: Boolean?,
    dir: EDirection?,
    disabled: Boolean?,
    form: String?,
    hidden: Boolean?,
    lang: String?,
    list: String?,
    maxlength: Int?,
    minlength: Int?,
    name: String?,
    pattern: String?,
    placeholder: String?,
    readonly: Boolean?,
    required: Boolean?,
    size: Int?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    value: String?,
    defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
) : KatyDomHtmlElement<Msg>(selector, key ?: name, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "INPUT"

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        require(maxlength == null || maxlength >= 0) { "Attribute maxlength must be non-negative." }
        require(minlength == null || minlength >= 0) { "Attribute minlength must be non-negative." }
        require(size == null || size >= 0) { "Attribute size must be non-negative." }

        setAttribute("autocomplete", autocomplete)
        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("list", list)
        setNumberAttribute("maxlength", maxlength)
        setNumberAttribute("minlength", minlength)
        setAttribute("name", name)
        setAttribute("pattern", pattern)
        setAttribute("placeholder", placeholder)
        setBooleanAttribute("readonly", readonly)
        setBooleanAttribute("required", required)
        setNumberAttribute("size", size)
        setAttribute("value", value)

        setAttribute("type", "url")

        phrasingContent.attributesContent(this).defineAttributes()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
