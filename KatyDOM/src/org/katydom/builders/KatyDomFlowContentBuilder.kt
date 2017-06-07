//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.grouping.*
import org.katydom.concretenodes.sections.*
import org.katydom.concretenodes.text.KatyDomA
import org.katydom.concretenodes.text.KatyDomSpan
import org.katydom.concretenodes.text.KatyDomText
import org.katydom.types.EAnchorHtmlLinkType
import org.katydom.types.EDirection
import org.katydom.types.EOrderedListType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for the normal case of HTML "flow content".
 *
 * Typical glocal attribute parameters for builder methods:
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
    private val element: KatyDomHtmlElement,

    /** Restrictions on content enforced at run time. */
    internal val contentRestrictions: KatyDomContentRestrictions = KatyDomContentRestrictions()

) : KatyDomElementContentBuilder(element) {

    /**
     * Adds an a element with any global attributes as the next child of the element under construction.
     */
    fun a(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        download: String? = null,
        hidden: Boolean? = null,
        href: String? = null,
        hreflang: String? = null,
        lang: String? = null,
        rel: Iterable<EAnchorHtmlLinkType>? = null,
        rev: Iterable<EAnchorHtmlLinkType>? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        target: String? = null,
        title: String? = null,
        translate: Boolean? = null,
        type: String? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomA(this, selector, key, accesskey, contenteditable, dir, download, hidden, href, hreflang, lang,
                     rel, rev, spellcheck, style, tabindex, target, title, translate, type, defineContent)
        )
    }

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
                              tabindex, title, translate,defineContent)
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
                          tabindex, title, translate,defineContent)
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
                          tabindex, title, translate,defineContent)
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
                          tabindex, title, translate,defineContent)
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
                          tabindex, title, translate,defineContent)
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
                          tabindex, title, translate,defineContent)
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
                          tabindex, title, translate,defineContent)
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
                              tabindex, title, translate,defineContent)
        )
    }

    /**
     * Adds an hr element as the next child of the element under construction.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun hr(
        selector: String? = null,
        key: String? = null,
        style: String? = null,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(KatyDomHr(selector, key, style,defineAttributes))
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
                           tabindex, title, translate,defineContent)
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
     * Adds a span element with any global attributes as the next child of the element under construction.
     */
    fun span(
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
                KatyDomSpan(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a text node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     */
    fun text(nodeValue: String) {
        element.addChildNode(KatyDomText(nodeValue))
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
     * as this builder plus no main element allowed.
     */
    internal fun withMainNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withMainNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun withNoAddedRestrictions(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

