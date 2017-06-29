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
 * Virtual node for an input type="image" element.
 */
internal class KatyDomInputImageButton : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String?,
        key: String?,
        accesskey: String?,
        alt: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        height: Int?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        src: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        width: Int?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(alt, disabled, form, formaction, formenctype, formmethod, formnovalidate, formtarget, height,
                      name, src, value, width)

        flowContent.phrasingContent(this).withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    constructor(
        phrasingContent: KatyDomPhrasingContentBuilder,
        selector: String?,
        key: String?,
        accesskey: String?,
        alt: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        height: Int?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        src: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        width: Int?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(alt, disabled, form, formaction, formenctype, formmethod, formnovalidate, formtarget, height,
                      name, src, value, width)

        phrasingContent.withLabelNotAllowed(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "INPUT"

    private fun setAttributes(
        alt: String?,
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        height: Int?,
        name: String?,
        src: String?,
        value: String?,
        width: Int?
    ) {
        setAttribute("alt", alt)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("formaction", formaction)
        setAttribute("formenctype", formenctype?.toHtmlString())
        setAttribute("formmethod", formmethod?.toHtmlString())
        setBooleanAttribute("formnovalidate", formnovalidate)
        setAttribute("formtarget", formtarget)
        setAttribute("height", height?.toString())
        setAttribute("name", name)
        setAttribute("src", src)
        setAttribute("value", value)
        setAttribute("width", width?.toString())

        setAttribute("type", "image")
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

