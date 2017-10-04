//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomAttributesContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an input type="number" element.
 */
internal class KatyDomInputNumber<T : Number>(
    phrasingContent: KatyDomPhrasingContentBuilder,
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
    max: T?,
    min: T?,
    name: String?,
    placeholder: String?,
    readonly: Boolean?,
    required: Boolean?,
    spellcheck: Boolean?,
    step: String?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    value: T?,
    defineAttributes: KatyDomAttributesContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key ?: name, accesskey, contenteditable, dir,
                       hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttribute("autocomplete", autocomplete)
        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("list", list)
        setAttribute("max", max.toString())
        setAttribute("min", min.toString())
        setAttribute("name", name)
        setAttribute("placeholder", placeholder)
        setBooleanAttribute("readonly", readonly)
        setBooleanAttribute("required", required)
        setAttribute("step", step)
        setAttribute("value", value.toString())

        setAttribute("type", "number")

        phrasingContent.attributesContent(this).defineAttributes()
        this.freeze()
    }

    override val nodeName = "INPUT"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

