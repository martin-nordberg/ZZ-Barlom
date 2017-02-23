//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.spi

import org.w3c.dom.*

/**
 * Service provider interface defining the services needed to construct and patch a DOM tree.
 * Note: Based upon Snabbdom DOMAPI.
 */
interface DomSpi {

    fun appendChild(parentNode: Node, childNode: Node)

    fun createComment(commentText: String): Comment

    fun createElement(tagName: String): Element

    fun createElementNS(namespaceURI: String, qualifiedName: String): Element

    fun createTextNode(textChars: String): Text

    fun getTextContent(node: Node): String?

    fun insertBefore(parentNode: Node, newNode: Node, referenceNode: Node?)

    fun isComment(node: Node): Boolean

    fun isElement(node: Node): Boolean

    fun isText(node: Node): Boolean

    fun nextSibling(node: Node): Node?

    fun parentNode(node: Node): Node?

    fun removeChild(parentNode: Node, childNode: Node)

    fun setTextContent(node: Node, textChars: String?)

    fun tagName(element: Element): String

}
