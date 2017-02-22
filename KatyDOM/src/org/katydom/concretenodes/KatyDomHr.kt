//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomContent
import org.katydom.abstractnodes.KatyDomHtmlElement

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an <hr> element.
 */
internal class KatyDomHr(
    /**
     * The "selector" for the element: "#" followed by the id plus a repetition of "." followed by a class name.
     * E.g. "#mybutton.big-button.warning".
     */
    selector: String,

    /**
     * The CSS style attribute for the element.
     */
    style: String,

    /**
     * The other attributes for the element.
     */
    content: KatyDomContent

) : KatyDomHtmlElement(selector, style, content) {

    override val nodeName = "hr"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
