//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.grouping.KatyDomLi
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomListItemContentBuilder(

    internal val flowContent: KatyDomFlowContentBuilder,

    internal val isOrdered: Boolean,

    element: KatyDomHtmlElement

) : KatyDomAttributesContentBuilder(element) {

    /**
     * Adds an li element with any global attributes as the next child of the element under construction.
     * @param selector the "selector" for the element, e.g. "#myid.my-class.my-other-class".
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
     * @param value the value attribute for the list item (its ordinal number in the list).
     * @param defineContent a DSL-style lambda that builds the child nodes of the new element.
     */
    fun li(
        selector: String? = null,
        key: Any? = null,
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
        value: Int? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomLi(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                      tabindex, title, translate, value, defineContent)
        )
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////