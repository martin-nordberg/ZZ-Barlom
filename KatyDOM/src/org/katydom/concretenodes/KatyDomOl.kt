//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.types.OrderedListType

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an ordered list <ol> element.
 * @param selector The "selector" for the element: "#" followed by the id plus a repetition of "." followed by a class
 *                 name. E.g. "#mybutton.big-button.warning".
 * @param key A key for this element that is unique among all child nodes of the same parent.
 * @param reversed Whether the list is to appear in reverse order.
 * @param start The numeric value for the first list item.
 * @param type The type of list counter to use.
 * @param style The CSS style attribute for the element.
 */
internal class KatyDomOl(
    selector: String?,
    key: String?,
    reversed: Boolean?,
    start: Int?,
    type: OrderedListType?,
    style: String?
) : KatyDomHtmlElement(selector, key, style) {

    override val nodeName = "OL"

    init {

        setBooleanAttribute("reversed", reversed)
        setAttribute("start", start?.toString())
        setAttribute("type", type?.toHtmlString())

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

