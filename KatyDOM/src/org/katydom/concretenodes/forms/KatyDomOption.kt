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
 * Virtual node for an option element.
 */
internal class KatyDomOption : KatyDomHtmlElement {

    constructor(
        optionContent: KatyDomOptionContentBuilder,
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
        selected: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String,
        defineAttributes: KatyDomAttributesContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(disabled, label, selected)
        setAttribute("value", value)

        optionContent.attributesContent(this).defineAttributes()
        this.freeze()
    }

    constructor(
        optionContent: KatyDomOptionContentBuilder,
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
        selected: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        defineContent: KatyDomTextContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(disabled, label, selected)

        optionContent.textContent(this).defineContent()
        this.freeze()
    }

    override val nodeName = "OPTION"

    private fun setAttributes(
        disabled: Boolean?,
        label: String?,
        selected: Boolean?
    ) {
        require( label == null || !label.isEmpty() ) { "Attribute label may not be an empty string." }

        setBooleanAttribute("disabled", disabled)
        setAttribute("label", label)
        setBooleanAttribute("selected", selected)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

