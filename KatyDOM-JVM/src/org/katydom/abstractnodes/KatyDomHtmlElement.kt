//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.types.EDirection
import org.w3c.dom.Document
import org.w3c.dom.Node

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract KatyDOM class corresponding to a DOM HTMLElement node.
 * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class".
 * @param key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
 * @param accesskey a string specifying the HTML accesskey value.
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
@Suppress("unused")
abstract class KatyDomHtmlElement<Msg>(
    selector: String?,
    key: Any?,
    accesskey: String? = null,
    contenteditable: Boolean? = null,
    dir: EDirection? = null,
    hidden: Boolean? = null,
    lang: String? = null,
    spellcheck: Boolean? = null,
    style: String? = null,
    tabindex: Int? = null,
    title: String? = null,
    translate: Boolean? = null
) : KatyDomElement<Msg>(selector, key, style, tabindex) {

    override fun createDomNode(document: Document, domNode: Node, domChild: Node?) {

        val childElement = document.createElement(nodeName)
        establish(childElement)
        domNode.insertBefore(childElement, domChild)

    }

    init {
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

