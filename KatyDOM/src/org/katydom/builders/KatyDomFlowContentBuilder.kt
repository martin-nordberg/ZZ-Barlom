//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.*
import org.katydom.concretenodes.grouping.*
import org.katydom.concretenodes.sections.*
import org.katydom.types.*

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for the normal case of HTML "flow content".
 *
 * Typical global attribute parameters for builder methods:
 *  selector the "selector" for the element, e.g. "#myid.my-class.my-other-class".
 *  key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
 *  accesskey a string specifying the HTML accesskey value.
 *  contenteditable whether the element has editable content.
 *  dir the left-to-right direction of text inside this element.
 *  hidden true if the element is to be hidden.
 *  lang the language of text within this element.
 *  spellcheck whether the element is subject to spell checking.
 *  style a string containing CSS for this element.
 *  tabindex the tab index for the element.
 *  title a tool tip for the element.
 *  translate whether to translate text within this element.
 *  defineContent a DSL-style lambda that builds the child nodes of the new element.
 */
@Suppress("unused")
class KatyDomFlowContentBuilder(

    /** The element whose content is being built. */
    element: KatyDomHtmlElement,

    /** Restrictions on content enforced at run time. */
    contentRestrictions: KatyDomContentRestrictions = KatyDomContentRestrictions()

) : KatyDomPhrasingContentBuilder(element, contentRestrictions) {

    /**
     * Adds an article element with any global attributes as the next child of the element under construction.
     */
    fun article(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomArticle(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                           tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an aside element with any global attributes as the next child of the element under construction.
     */
    fun aside(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomAside(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                             tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a div element with any global attributes as the next child of the element under construction.
     */
    fun div(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomDiv(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                           tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an footer element with any global attributes as the next child of the element under construction.
     */
    fun footer(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomFooter(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                              tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a form element with its attributes as the next child of the element under construction.
     */
    fun form(
        selector: String? = null,
        key: String? = null,
        acceptCharset: String?,
        accesskey: String?,
        action: String?,
        autocomplete: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        enctype: EFormEncodingType?,
        hidden: Boolean?,
        lang: String?,
        method: EFormSubmissionMethod?,
        name: String?,
        novalidate: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        target: String?,
        title: String?,
        translate: Boolean?,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomForm(this, selector, key, acceptCharset, accesskey, action, autocomplete, contenteditable, dir,
                            enctype, hidden, lang, method, name, novalidate, spellcheck, style, tabindex, title,
                            target, translate, defineContent)
        )
    }

    /**
     * Adds a first level heading element with any global attributes as the next child of the element under construction.
     */
    fun h1(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH1(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a second level heading element with any global attributes as the next child of the element under construction.
     */
    fun h2(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH2(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a third level heading element with any global attributes as the next child of the element under construction.
     */
    fun h3(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH3(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a fourth level heading element with any global attributes as the next child of the element under construction.
     */
    fun h4(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH4(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a fifth level heading element with any global attributes as the next child of the element under construction.
     */
    fun h5(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH5(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a sixth level heading element with any global attributes as the next child of the element under construction.
     */
    fun h6(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH6(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a header element with any global attributes as the next child of the element under construction.
     */
    fun header(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomHeader(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                              tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an hr element as the next child of the element under construction.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun hr(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineAttributes: KatyDomAttributesContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomHr(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                      tabindex, title, translate, defineAttributes)

        )
    }

    /**
     * Adds a main element with any global attributes as the next child of the element under construction.
     */
    fun main(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomMain(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                            tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a nav element with any global attributes as the next child of the element under construction.
     */
    fun nav(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomNav(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                           tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an ol element as the next child of the element under construction. Allows setting all global HTML
     * attributes.
     * @param reversed whether the list is to appear in reverse order.
     * @param start the numeric value for the first list item.
     * @param type the type of list counter to use.
     */
    fun ol(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        reversed: Boolean? = null,
        spellcheck: Boolean? = null,
        start: Int? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        type: EOrderedListType? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomOl(this, selector, key, accesskey, contenteditable, dir, hidden, lang, reversed, spellcheck,
                          start, style, tabindex, title, translate, type, defineContent)
        )
    }

    /**
     * Adds a paragraph element with any global attributes as the next child of the element under construction.
     */
    fun p(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomP(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                         tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a section element with any global attributes as the next child of the element under construction.
     */
    fun section(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomSection(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                               tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a new ul element as the next child of the element under construction. Allows setting all global HTML
     * attributes.
     */
    fun ul(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomUl(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

////

    /**
     * Creates a new phrasing content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun phrasingContent(element: KatyDomHtmlElement) : KatyDomPhrasingContentBuilder {
        return KatyDomPhrasingContentBuilder(element, contentRestrictions)
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no footer, header or main elements allowed.
     */
    internal fun withFooterHeaderMainNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withFooterHeaderMainNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no form element allowed.
     */
    internal fun withFormNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withFormNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no main element allowed.
     */
    internal fun withMainNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withMainNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    override fun withNoAddedRestrictions(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

