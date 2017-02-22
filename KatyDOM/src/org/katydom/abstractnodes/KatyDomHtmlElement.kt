//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

abstract class KatyDomHtmlElement(
    selector: String?,
    style: String?,
    content: KatyDomContent
) : KatyDomElement(selector, content.withFilteredAttributes { it.name != "style" }) {

    val style: String?

    init {

        val style0 = content.attributes.find { it.name == "style" }?.value

        if (style != null) {

            if (style0 != null) {
                // TODO: not sure simple concatenation is enough to keep valid CSS
                this.style = style + ";" + style0
            }
            else {
                this.style = style
            }

        }
        else {
            this.style = style0
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

