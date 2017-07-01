//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.*
import org.katydom.concretenodes.grouping.*
import org.katydom.concretenodes.sections.*
import org.katydom.concretenodes.text.KatyDomA
import org.katydom.concretenodes.text.KatyDomBr
import org.katydom.concretenodes.text.KatyDomSpan
import org.katydom.concretenodes.text.KatyDomText
import org.katydom.types.*

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for content that must be text.
 */
@Suppress("unused")
class KatyDomTextContentBuilder(

    /** The element whose content is being built. */
    private val element: KatyDomHtmlElement

) : KatyDomElementContentBuilder(element) {

    /**
     * Adds a text node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     */
    fun text(nodeValue: String) {
        element.addChildNode(KatyDomText(nodeValue))
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

