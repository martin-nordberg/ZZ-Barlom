//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.KatyDomOption
import org.katydom.concretenodes.forms.KatyDomOptionGroup
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomOptionContentBuilder(

        private val element: KatyDomHtmlElement,

        /** Restrictions on content enforced at run time. */
        internal val contentRestrictions: KatyDomContentRestrictions

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

    /**
     * Adds an optgroup element with any global attributes as the next child of the element under construction.
     */
    fun optionGroup(
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
        defineContent: KatyDomOptionContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomOptionGroup(this, selector, key, accesskey, contenteditable, dir, disabled,
                hidden, label, lang, name, spellcheck, style,
                tabindex, title, translate, defineContent)
        )
    }

////

    /**
     * Creates a new attributes content builder for the given child [element].
     */
    internal fun attributesContent(element: KatyDomHtmlElement) : KatyDomElementContentBuilder {
        return KatyDomElementContentBuilder(element)
    }

    /**
     * Creates a new text content builder for the given child [element].
     */
    internal fun textContent(element: KatyDomHtmlElement) : KatyDomTextContentBuilder {
        return KatyDomTextContentBuilder(element)
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun withNoAddedRestrictions(element: KatyDomHtmlElement) : KatyDomOptionContentBuilder {
        return KatyDomOptionContentBuilder(element, contentRestrictions)
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no option groups allowed.
     */
    internal fun withOptionGroupNotAllowed(element: KatyDomHtmlElement) : KatyDomOptionContentBuilder {
        return KatyDomOptionContentBuilder(element, contentRestrictions.withOptionGroupNotAllowed())
    }


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
