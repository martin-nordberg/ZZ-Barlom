//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.browser

import org.katydom.spi.DomSpi
import org.w3c.dom.Comment
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.Text
import kotlin.browser.document

/**
 * Default SPI for manipulating DOM elements in the browser.
 */
object browserSpi : DomSpi {

    override fun appendChild(parentNode: Node, childNode: Node) {
        parentNode.appendChild(childNode)
    }

    override fun createComment(commentText: String): Comment {
        return document.createComment(commentText)
    }

    override fun createElement(tagName: String): Element {
        return document.createElement(tagName)
    }

    override fun createElementNS(namespaceURI: String, qualifiedName: String): Element {
        return document.createElementNS(namespaceURI, qualifiedName)
    }

    override fun createTextNode(textChars: String): Text {
        return document.createTextNode(textChars)
    }

    override fun getTextContent(node: Node): String? {
        return node.textContent
    }

    override fun insertBefore(parentNode: Node, newNode: Node, referenceNode: Node?) {
        parentNode.insertBefore(newNode, referenceNode)
    }

    override fun isComment(node: Node): Boolean {
        return node.nodeType == Node.COMMENT_NODE
    }

    override fun isElement(node: Node): Boolean {
        return node.nodeType == Node.ELEMENT_NODE
    }

    override fun isText(node: Node): Boolean {
        return node.nodeType == Node.TEXT_NODE
    }

    override fun nextSibling(node: Node): Node? {
        return node.nextSibling
    }

    override fun parentNode(node: Node): Node? {
        return node.parentNode
    }

    override fun removeChild(parentNode: Node, childNode: Node) {
        parentNode.removeChild(childNode)
    }

    override fun setTextContent(node: Node, textChars: String?) {
        node.textContent = textChars
    }

    override fun tagName(element: Element): String {
        return element.tagName
    }
}