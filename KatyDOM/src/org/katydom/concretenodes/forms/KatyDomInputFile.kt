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
 * Virtual node for an input type="file" element.
 */
internal class KatyDomInputFile : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String?,
        key: String?,
        accept: String?,
        accesskey: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        multiple: Boolean?,
        name: String?,
        required: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        flowContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttributes(accept, autofocus, disabled, form, multiple, name, required, value)

        flowContent.phrasingContent(this).withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    constructor(
        phrasingContent: KatyDomPhrasingContentBuilder,
        selector: String?,
        key: String?,
        accept: String?,
        accesskey: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        multiple: Boolean?,
        name: String?,
        required: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttributes(accept, autofocus, disabled, form, multiple, name, required, value)

        phrasingContent.withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "INPUT"

    private fun setAttributes(
        accept: String?,
        autofocus: Boolean?,
        disabled: Boolean?,
        form: String?,
        multiple: Boolean?,
        name: String?,
        required: Boolean?,
        value: String?
    ) {
        setAttribute("accept", accept)
        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setBooleanAttribute("multiple", multiple)
        setAttribute("name", name)
        setBooleanAttribute("required", required)
        setAttribute("value", value)

        setAttribute("type", "file")
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

