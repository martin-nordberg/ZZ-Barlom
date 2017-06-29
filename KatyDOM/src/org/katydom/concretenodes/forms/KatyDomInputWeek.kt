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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an input type="week" element.
 */
internal class KatyDomInputWeek : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String?,
        key: String?,
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
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        flowContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttributes(autocomplete, autofocus, disabled, form, list, max, min, name,
                      readonly, required, step, value)

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
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttributes(autocomplete, autofocus, disabled, form, list, max, min, name,
                      readonly, required, step, value)

        phrasingContent.withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "INPUT"

    private fun setAttributes(
        autocomplete: String?,
        autofocus: Boolean?,
        disabled: Boolean?,
        form: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        step: String?,
        value: String?
    ) {
        setAttribute("autocomplete", autocomplete)
        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("list", list)
        setAttribute("max", max)
        setAttribute("min", min)
        setAttribute("name", name)
        setBooleanAttribute("readonly", readonly)
        setBooleanAttribute("required", required)
        setAttribute("step", step)
        setAttribute("value", value)

        setAttribute("type", "week")
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

