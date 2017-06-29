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
import org.katydom.types.EFormEncodingType
import org.katydom.types.EFormSubmissionMethod

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an input type="submit" element.
 */
internal class KatyDomInputSubmitButton : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String?,
        key: String?,
        accesskey: String?,
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
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(disabled, form, formaction, formenctype, formmethod, formnovalidate, formtarget, name, value)

        flowContent.phrasingContent(this).withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    constructor(
        phrasingContent: KatyDomPhrasingContentBuilder,
        selector: String?,
        key: String?,
        accesskey: String?,
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
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(disabled, form, formaction, formenctype, formmethod, formnovalidate, formtarget, name, value)

        phrasingContent.withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "INPUT"

    private fun setAttributes(
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        name: String?,
        value: String?
    ) {
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("formaction", formaction)
        setAttribute("formenctype", formenctype?.toHtmlString())
        setAttribute("formmethod", formmethod?.toHtmlString())
        setBooleanAttribute("formnovalidate", formnovalidate)
        setAttribute("formtarget", formtarget)
        setAttribute("name", name)
        setAttribute("value", value)

        setAttribute("type", "submit")
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

