//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.types.EDirection
import org.katydom.types.EOrderedListType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an ordered list <ol> element.
 */
internal class KatyDomOl : KatyDomHtmlElement {

    /**
     * Constructs a new ordered list element with minimal attributes.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key A non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
     * @param reversed Whether the list is to appear in reverse order.
     * @param start The numeric value for the first list item.
     * @param style The CSS style attribute for the element.
     * @param type The type of list counter to use.
     */
    constructor(
        selector: String?,
        key: String?,
        reversed: Boolean?,
        start: Int?,
        style: String?,
        type: EOrderedListType?
    ) : super(selector, key, style) {

        setBooleanAttribute("reversed", reversed)
        setAttribute("start", start?.toString())
        setAttribute("type", type?.toHtmlString())

    }

    /**
     * Constructs a new ordered list element with full attributes.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key A non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
     * @param accesskey a string specifiying the HTML accesskey value.
     * @param contenteditable whether the element has editable content.
     * @param dir the left-to-right direction of text inside this element.
     * @param hidden true if the element is to be hidden.
     * @param lang the language of text within this element.
     * @param reversed Whether the list is to appear in reverse order.
     * @param spellcheck whether the element is subject to spell checking.
     * @param start The numeric value for the first list item.
     * @param style a string containing CSS for this element.
     * @param tabindex the tab index for the element.
     * @param title a tool tip for the element.
     * @param translate whether to translate text within this element.
     * @param type The type of list counter to use.
     */
    constructor(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        hidden: Boolean?,
        lang: String?,
        reversed: Boolean?,
        spellcheck: Boolean?,
        start: Int?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        type: EOrderedListType?
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        setBooleanAttribute("reversed", reversed)
        setAttribute("start", start?.toString())
        setAttribute("type", type?.toHtmlString())

    }


    override val nodeName = "OL"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

