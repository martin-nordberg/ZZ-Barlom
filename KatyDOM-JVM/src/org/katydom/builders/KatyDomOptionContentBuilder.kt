//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.KatyDomOption
import org.katydom.concretenodes.forms.KatyDomOptionGroup
import org.katydom.concretenodes.text.KatyDomComment
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomOptionContentBuilder<Msg>(

    element: KatyDomHtmlElement<Msg>,

    /** Restrictions on content enforced at run time. */
    internal val contentRestrictions: KatyDomContentRestrictions,

    /** Dispatcher of event handling results for when we want event handling to be reactive or Elm-like. */
    dispatchMessages: (messages: Iterable<Msg>) -> Unit

) : KatyDomAttributesContentBuilder<Msg>(element, dispatchMessages) {

    /**
     * Adds a comment node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     * @param key unique key for this comment within its parent node.
     */
    fun comment(nodeValue: String,
                key: Any? = null) {
        element.addChildNode(KatyDomComment(nodeValue, key))
    }

    /**
     * Adds an option element with any global attributes as the next child of the element under construction.
     */
    fun option(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        hidden: Boolean? = null,
        label: String? = null,
        lang: String? = null,
        name: String? = null,
        selected: Boolean? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        hidden: Boolean? = null,
        label: String? = null,
        lang: String? = null,
        name: String? = null,
        selected: Boolean? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomTextContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        hidden: Boolean? = null,
        label: String? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomOptionContentBuilder<Msg>.() -> Unit
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
    internal fun attributesContent(element: KatyDomHtmlElement<Msg>): KatyDomAttributesContentBuilder<Msg> {
        return KatyDomAttributesContentBuilder(element, dispatchMessages)
    }

    /**
     * Creates a new text content builder for the given child [element].
     */
    internal fun textContent(element: KatyDomHtmlElement<Msg>): KatyDomTextContentBuilder<Msg> {
        return KatyDomTextContentBuilder(element, dispatchMessages)
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun withNoAddedRestrictions(element: KatyDomHtmlElement<Msg>): KatyDomOptionContentBuilder<Msg> {
        return KatyDomOptionContentBuilder(
            element,
            contentRestrictions,
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no option groups allowed.
     */
    internal fun withOptionGroupNotAllowed(element: KatyDomHtmlElement<Msg>): KatyDomOptionContentBuilder<Msg> {
        return KatyDomOptionContentBuilder(
            element,
            contentRestrictions.withOptionGroupNotAllowed(),
            dispatchMessages
        )
    }


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
