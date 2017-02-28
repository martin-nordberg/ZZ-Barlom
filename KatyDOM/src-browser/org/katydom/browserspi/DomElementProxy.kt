//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.browserspi

import org.katydom.spi.ElementProxy
import org.w3c.dom.Element

/**
 * Implementation of ElementProxy for the browser DOM.
 */
class DomElementProxy(val element: Element) : DomNodeProxy(element), ElementProxy {

    override fun setAttribute(qualifiedName: String, value: String) {
        element.setAttribute(qualifiedName, value)
    }

}