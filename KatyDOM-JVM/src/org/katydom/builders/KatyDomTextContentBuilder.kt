//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.text.KatyDomComment
import org.katydom.concretenodes.text.KatyDomText

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for content that must be text.
 */
@Suppress("unused")
class KatyDomTextContentBuilder<Message>(

    /** The element whose content is being built. */
    element: KatyDomHtmlElement<Message>,

    /** Dispatcher of event handling results for when we want event handling to be reactive or Elm-like. */
    dispatchMessages: (messages: Iterable<Message>) -> Unit

) : KatyDomAttributesContentBuilder<Message>(element, dispatchMessages) {

    /**
     * Adds a comment node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     * @param key unique key for this comment within its parent node.
     */
    fun comment(nodeValue: String,
                key: Any? = null) {
        element.addChildNode(KatyDomComment(nodeValue, key))
    }

    /**
     * Adds a text node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     */
    fun text(nodeValue: String) {
        element.addChildNode(KatyDomText(nodeValue))
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

