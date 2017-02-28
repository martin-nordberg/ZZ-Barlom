//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.spi

/**
 * Interface to a document proxy - encapsulates a DOM or other Document implementation.
 */
interface DocumentProxy : NodeProxy {

    fun createComment(commentText: String): CommentProxy

    fun createElement(tagName: String): ElementProxy

    fun createElementNS(namespaceURI: String, qualifiedName: String): ElementProxy

    fun createTextNode(textChars: String): TextProxy

}