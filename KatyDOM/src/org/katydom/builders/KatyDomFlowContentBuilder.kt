//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.*
import org.katydom.types.EDirection
import org.katydom.types.EOrderedListType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for the normal case of HTML "flow content".
 */
@Suppress("unused")
class KatyDomFlowContentBuilder(

    /** The element whose content is being built. */
    private val element: KatyDomHtmlElement

) : KatyDomElementContentBuilder(element) {

    /**
     * Adds a div element with minimal attributes as the next child of the element under construction.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key a non-DOM key for the KatyDOM element that is unique among all the siblings of the element.
     * @param style a string containing CSS for the element.
     * @param defineContent a DSL-style lambda that builds the child nodes of the new element.
    */
    fun div(
        selector: String? = null,
        key: String? = null,
        style: String? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomDiv(selector, key, style)
        KatyDomFlowContentBuilder(childElement).defineContent()
        childElement.freeze()
        element.addChildNode(childElement)
    }

    /**
     * Adds a div element with any global attributes as the next child of the element under construction.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
     * @param accesskey a string specifiying the HTML accesskey value.
     * @param contenteditable whether the element has editable content.
     * @param dir the left-to-right direction of text inside this element.
     * @param hidden true if the element is to be hidden.
     * @param lang the language of text within this element.
     * @param spellcheck whether the element is subject to spell checking.
     * @param style a string containing CSS for this element.
     * @param tabindex the tab index for the element.
     * @param title a tool tip for the element.
     * @param translate whether to translate text within this element.
     * @param defineContent a DSL-style lambda that builds the child nodes of the new element.
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
        val childElement = KatyDomDiv(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate)
        KatyDomFlowContentBuilder(childElement).defineContent()
        childElement.freeze()
        element.addChildNode(childElement)
    }

    /**
     * Adds an hr element as the next child of the element under construction.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key a non-DOM key for the KatyDOM element that is unique among all the siblings of the element.
     * @param style a string containing CSS for the element.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun hr(
        selector: String = "",
        key: String? = null,
        style: String? = null,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomHr(selector, key, style)
        KatyDomElementContentBuilder(childElement).defineAttributes()
        childElement.freeze()
        element.addChildNode(childElement)
    }

    /**
     * Adds an ol element as the next child of the element under construction.
     */
    fun ol(
        selector: String = "",
        key: String? = null,
        reversed: Boolean? = null,
        start: Int? = null,
        style: String? = null,
        type: EOrderedListType? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomOl(selector, key, reversed, start, style, type)
        KatyDomListItemContentBuilder(childElement).defineContent()
        childElement.freeze()
        element.addChildNode(childElement)
    }

    // TODO: ol with full attributes

    /**
     * Adds a text node as the next child of the element under construction.
     */
    fun text(nodeValue: String) {
        val textNode = KatyDomText(nodeValue)
        textNode.freeze()
        element.addChildNode(textNode)
    }

    /**
     * Adds a ul element as the next child of the element under construction.
     */
    fun ul(
        selector: String = "",
        key: String? = null,
        style: String? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        val childElement = KatyDomUl(selector, key, style)
        KatyDomListItemContentBuilder(childElement).defineContent()
        childElement.freeze()
        element.addChildNode(childElement)
    }

    // TODO: ul with full attributes

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

