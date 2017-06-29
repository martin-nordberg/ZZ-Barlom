//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomElementContentBuilder
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection
import org.katydom.types.EInputMode

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an input type="text" element.
 */
internal class KatyDomInputText : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        dirname: String?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        inputmode: EInputMode?,
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
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        flowContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttributes(autocomplete, autofocus, dirname, disabled, form, inputmode, list, maxlength, minlength, name,
                      pattern, placeholder, readonly, required, size, value)

        flowContent.phrasingContent(this).withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    constructor(
        phrasingContent: KatyDomPhrasingContentBuilder,
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        dirname: String?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        inputmode: EInputMode?,
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
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttributes(autocomplete, autofocus, dirname, disabled, form, inputmode, list, maxlength, minlength, name,
                      pattern, placeholder, readonly, required, size, value)

        phrasingContent.withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "INPUT"

    private fun setAttributes(
        autocomplete: String?,
        autofocus: Boolean?,
        dirname: String?,
        disabled: Boolean?,
        form: String?,
        inputmode: EInputMode?,
        list: String?,
        maxlength: Int?,
        minlength: Int?,
        name: String?,
        pattern: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        size: Int?,
        value: String?
    ) {
        require( maxlength == null || maxlength >= 0 ) { "Attribute maxlength must be non-negative." }
        require( minlength == null || minlength >= 0 ) { "Attribute minlength must be non-negative." }
        require( size == null || size >= 0 ) { "Attribute size must be non-negative." }

        setAttribute("autocomplete", autocomplete)
        setBooleanAttribute("autofocus", autofocus)
        setAttribute("dirname", dirname)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("inputmode", inputmode?.toHtmlString())
        setAttribute("list", list)
        setAttribute("maxlength", maxlength?.toString())
        setAttribute("minlength", minlength?.toString())
        setAttribute("name", name)
        setAttribute("pattern", pattern)
        setAttribute("placeholder", placeholder)
        setBooleanAttribute("readonly", readonly)
        setBooleanAttribute("required", required)
        setAttribute("size", size?.toString())
        setAttribute("value", value)

        setAttribute("type", "text")
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
