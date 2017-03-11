//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an <hr> element.
 * @param selector The "selector" for the element: "#" followed by the id plus a repetition of "." followed by a class
 *                 name. E.g. "#mybutton.big-button.warning".
 * @param key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
 * @param style The CSS style attribute for the element.
 */
internal class KatyDomHr(selector: String?, key: String?, style: String?) : KatyDomHtmlElement(selector, key, style) {

    override val nodeName = "HR"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
