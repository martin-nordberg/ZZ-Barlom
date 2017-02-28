//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.browserspi

import org.katydom.spi.TextProxy
import org.w3c.dom.Text

/**
 * Implementation of TextProxy for the browser DOM.
 */
class DomTextProxy(text: Text) : DomNodeProxy(text), TextProxy {

}