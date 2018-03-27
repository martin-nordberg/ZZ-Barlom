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
 * Virtual node for an input type="checkbox" element.
 */
internal class KatyDomInputCheckbox<Message>(
    phrasingContent: KatyDomPhrasingContentBuilder<Message>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    autofocus: Boolean?,
    checked: Boolean?,
    contenteditable: Boolean?,
    dir: EDirection?,
    disabled: Boolean?,
    form: String?,
    hidden: Boolean?,
    lang: String?,
    name: String?,
    required: Boolean?,
    spellcheck: Boolean?,
    step: String?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    value: String?,
    defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key ?: name, accesskey, contenteditable, dir, hidden, lang,
                                spellcheck, style, tabindex, title, translate) {

    override val nodeName = "INPUT"

    init {

        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("checked", checked)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("name", name)
        setBooleanAttribute("required", required)
        setAttribute("step", step)
        setAttribute("value", value)

        setAttribute("type", "checkbox")

        phrasingContent.attributesContent(this).defineAttributes()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

