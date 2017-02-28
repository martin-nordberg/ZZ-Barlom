//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.browserspi

import org.katydom.spi.CommentProxy
import org.katydom.spi.DocumentProxy
import org.katydom.spi.ElementProxy
import org.katydom.spi.TextProxy
import org.w3c.dom.Document
import kotlin.browser.document

/**
 * Implementation of DocumentProxy for the browser DOM.
 */
class DomDocumentProxy(document: Document) : DomNodeProxy(document), DocumentProxy {

    override fun createComment(commentText: String): CommentProxy {
        return DomCommentProxy(document.createComment(commentText))
    }

    override fun createElement(tagName: String): ElementProxy {
        return DomElementProxy(document.createElement(tagName))
    }

    override fun createElementNS(namespaceURI: String, qualifiedName: String): ElementProxy {
        return DomElementProxy(document.createElementNS(namespaceURI, qualifiedName))
    }

    override fun createTextNode(textChars: String): TextProxy {
        return DomTextProxy(document.createTextNode(textChars))
    }

}