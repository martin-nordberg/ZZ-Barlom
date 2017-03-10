//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell
import org.w3c.dom.Element

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract KatyDOM class corresponding to a DOM HTMLElement node.
 * @param selector the selector for the node consisting of an optional id preceded by '#' plus zero or more class
 *                 names, each preceded by '.'. For example, "#topbanner.squished.full-width" means id="topbanner"
 *                 and class="squished full-width".
 * @param key a key for this KatyDOM element that is unique among all the siblings of this element.
 * @param style a string containing CSS for this element.
 */
@Suppress("unused")
abstract class KatyDomHtmlElement(
    selector: String?,
    key: String?,
    style: String?
) : KatyDomElement(selector,key,style) {


////

    // TODO: global attributes unique to HTML elements

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

