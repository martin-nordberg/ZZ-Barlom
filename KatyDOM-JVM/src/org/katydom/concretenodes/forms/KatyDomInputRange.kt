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
 * Virtual node for an input type="range" element.
 */
internal class KatyDomInputRange<Num : Number, Message>(
    phrasingContent: KatyDomPhrasingContentBuilder<Message>,
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
    max: Num?,
    min: Num?,
    name: String?,
    spellcheck: Boolean?,
    step: String?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    value: Num?,
    defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key ?: name, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "INPUT"

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        setAttribute("autocomplete", autocomplete)
        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("list", list)
        setNumberAttribute("max", max)
        setNumberAttribute("min", min)
        setAttribute("name", name)
        setAttribute("step", step)
        setNumberAttribute("value", value)

        setAttribute("type", "range")

        phrasingContent.attributesContent(this).defineAttributes()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

