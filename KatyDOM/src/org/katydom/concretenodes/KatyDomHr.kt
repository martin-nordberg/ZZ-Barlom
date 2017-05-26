//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes

import org.katydom.abstractnodes.KatyDomHtmlElement

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an <hr> element.
 */
internal class KatyDomHr(
        selector: String?,
        key: String?,
        style: String?
) : KatyDomHtmlElement(selector, key, style=style) {

    override val nodeName = "HR"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
