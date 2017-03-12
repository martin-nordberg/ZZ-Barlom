//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract KatyDOM class corresponding to a DOM HTMLElement node.
 */
@Suppress("unused")
abstract class KatyDomHtmlElement : KatyDomElement {

    /**
     * Constructs a new HTML element with minimal attributes.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
     * @param style a string containing CSS for this element.
     */
    constructor(
        selector: String?,
        key: String?,
        style: String?
    ) : super(selector, key, style)

    /**
     * Constructs a new HTML element with global attributes beyond id and class.
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
     */
    constructor(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        hidden: Boolean?,
        lang: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?
    ) : super(selector, key, style, tabindex) {

        setAttribute("accesskey", accesskey)
        setTrueFalseAttribute("contenteditable", contenteditable)
        setAttribute("dir", dir?.toHtmlString())
        setBooleanAttribute("hidden", hidden)
        setAttribute("lang", lang)
        setTrueFalseAttribute("spellcheck", spellcheck)
        setAttribute("title", title)
        setYesNoAttribute("translate", translate)

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

