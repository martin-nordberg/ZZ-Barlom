//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <div> element.
 * @param selector The "selector" for the element: "#" followed by the id plus a repetition of "." followed by a class
 *                 name. E.g. "#mybutton.big-button.warning".
 * @param key A key for this element that is unique among all child nodes of the same parent.
 * @param style The CSS style attribute for the element.
 */
internal class KatyDomDiv(selector: String?, key: String?, style: String?) : KatyDomHtmlElement(selector, key, style) {

    override val nodeName = "DIV"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
