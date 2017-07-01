//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.KatyDomOption
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomOptionContentBuilder(

        private val element: KatyDomHtmlElement

) : KatyDomElementContentBuilder(element) {

    /**
     * Adds an option element with any global attributes as the next child of the element under construction.
     */
    fun option(
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
    ) {
        element.addChildNode(
            KatyDomOption(this, selector, key, accesskey, contenteditable, dir, disabled,
                hidden, label, lang, name, selected, spellcheck, style,
                tabindex, title, translate, value, defineAttributes)
        )
    }

    /**
     * Adds an option element with any global attributes as the next child of the element under construction.
     */
    fun option(
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
        defineContent: KatyDomTextContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomOption(this, selector, key, accesskey, contenteditable, dir, disabled,
                hidden, label, lang, name, selected, spellcheck, style,
                tabindex, title, translate, defineContent)
        )
    }

////

    /**
     * Creates a new text content builder for the given child [element].
     */
    internal fun textContent(element: KatyDomHtmlElement) : KatyDomTextContentBuilder {
        return KatyDomTextContentBuilder(element)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
