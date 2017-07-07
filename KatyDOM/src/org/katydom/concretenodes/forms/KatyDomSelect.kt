//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomOptionContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a select element.
 */
internal class KatyDomSelect(
    phrasingContent: KatyDomPhrasingContentBuilder,
    selector: String?,
    key: String?,
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
    size: Int?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    value: String?,
    defineContent: KatyDomOptionContentBuilder.() -> Unit
) : KatyDomHtmlElement(selector, key ?: name, accesskey, contenteditable, dir,
                       hidden, lang, spellcheck, style, tabindex, title, translate) {

    init {
        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()

        require(size == null || size >= 0) { "Attribute size must be non-negative." }

        setBooleanAttribute("autofocus", autofocus)
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setBooleanAttribute("multiple", multiple)
        setAttribute("name", name)
        setBooleanAttribute("required", required)
        setAttribute("size", size?.toString())
        setAttribute("value", value)

        phrasingContent.optionContent(this).defineContent()
        this.freeze()
    }

    override val nodeName = "SELECT"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

