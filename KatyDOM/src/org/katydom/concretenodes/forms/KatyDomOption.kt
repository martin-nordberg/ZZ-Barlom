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
        selector: String?,
        key: String?,
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
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(disabled, label, selected)
        setAttribute("value", value)

        KatyDomElementContentBuilder(this).defineAttributes()
        this.freeze()
    }

    constructor(
        selector: String?,
        key: String?,
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
        defineAttributes: KatyDomElementContentBuilder.() -> Unit   // TODO: text builder
    ) : super(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setAttributes(disabled, label, selected)

        KatyDomElementContentBuilder(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "OPTION"

    private fun setAttributes(
        disabled: Boolean?,
        label: String?,
        selected: Boolean?
    ) {
        setBooleanAttribute("disabled", disabled)
        setAttribute("label", label)
        setBooleanAttribute("selected", selected)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

