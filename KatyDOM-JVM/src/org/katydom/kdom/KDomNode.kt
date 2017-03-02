//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.kdom

import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.UserDataHandler


/**
 * Implementation of Node for producing HTML text (server side or testing).
 */
abstract class KDomNode : Node {

    private var _firstChild: KDomNode? = null

    private var _nextSibling: KDomNode? = null

    private var _parentNode: KDomNode? = null

    override fun appendChild(newChild: Node): KDomNode {
        val result = newChild as KDomNode
        if ( _firstChild == null ) {
            _firstChild = result
        }
        else {
            lastChild!!._nextSibling = result
        }
        result.setParentNode(this)
        return result
    }

    override fun getFirstChild(): KDomNode? {
        return _firstChild
    }

    override fun getLastChild(): KDomNode? {

        var result = _firstChild

        while ( result != null && result._nextSibling != null ) {
            result = result._nextSibling
        }

        return result

    }

    override fun getNextSibling(): KDomNode? {
        return _nextSibling
    }

    override fun getParentNode(): KDomNode? {
        return _parentNode
    }

    override fun insertBefore(newChild: Node, refChild: Node?): KDomNode {

        val result = newChild as KDomNode

        if ( refChild == null || _firstChild == null ) {
            return appendChild(newChild)
        }

        if ( _firstChild == refChild ) {
            _firstChild = result
        }
        else {
            var child = _firstChild
            while ( child != null && child._nextSibling != null && child._nextSibling != refChild ) {
                child = child._nextSibling
            }

            if ( child == null ) {
                return appendChild( newChild )
            }
            else {
                child._nextSibling = newChild
            }
        }

        result._nextSibling = refChild as KDomNode
        result.setParentNode(this)
        return result

    }

    override fun removeChild(oldChild: Node): KDomNode {
        val result = oldChild as KDomNode
        if ( _firstChild == result ) {
            _firstChild = result._nextSibling
        }
        else {
            var child = _firstChild
            while ( child != null && child._nextSibling != result ) {
                child = child._nextSibling
            }
            if ( child != null ) {
                child._nextSibling = result._nextSibling
            }
        }
        result.setParentNode(null)
        return result
    }

    internal fun setParentNode(parentNode: KDomNode?) {
        _parentNode = parentNode
    }

    abstract fun toHtmlString(indent : Int = 0) : String

////

    override fun isSameNode(other: Node?): Boolean {
        TODO("not yet needed")
    }

    override fun getNodeName(): String {
        TODO("not yet needed")
    }

    override fun getBaseURI(): String {
        TODO("not yet needed")
    }

    override fun isDefaultNamespace(namespaceURI: String?): Boolean {
        TODO("not yet needed")
    }

    override fun getFeature(feature: String?, version: String?): Any {
        TODO("not yet needed")
    }

    override fun getAttributes(): NamedNodeMap {
        TODO("not yet needed")
    }

    override fun cloneNode(deep: Boolean): KDomNode {
        TODO("not yet needed")
    }

    override fun isSupported(feature: String?, version: String?): Boolean {
        TODO("not yet needed")
    }

    override fun getTextContent(): String {
        TODO("not yet needed")
    }

    override fun hasChildNodes(): Boolean {
        TODO("not yet needed")
    }

    override fun getNodeValue(): String {
        TODO("not yet needed")
    }

    override fun setTextContent(textContent: String?) {
        TODO("not yet needed")
    }

    override fun compareDocumentPosition(other: Node?): Short {
        TODO("not yet needed")
    }

    override fun getLocalName(): String {
        TODO("not yet needed")
    }

    override fun normalize() {
        TODO("not yet needed")
    }

    override fun getNamespaceURI(): String {
        TODO("not yet needed")
    }

    override fun setNodeValue(nodeValue: String?) {
        TODO("not yet needed")
    }

    override fun replaceChild(newChild: Node?, oldChild: Node?): KDomNode {
        TODO("not yet needed")
    }

    override fun getPreviousSibling(): KDomNode {
        TODO("not yet needed")
    }

    override fun setPrefix(prefix: String?) {
        TODO("not yet needed")
    }

    override fun setUserData(key: String?, data: Any?, handler: UserDataHandler?): Any {
        TODO("not yet needed")
    }

    override fun getUserData(key: String?): Any {
        TODO("not yet needed")
    }

    override fun hasAttributes(): Boolean {
        TODO("not yet needed")
    }

    override fun getNodeType(): Short {
        TODO("not yet needed")
    }

    override fun getPrefix(): String {
        TODO("not yet needed")
    }

    override fun getChildNodes(): NodeList {
        TODO("not yet needed")
    }

    override fun lookupPrefix(namespaceURI: String?): String {
        TODO("not yet needed")
    }

    override fun lookupNamespaceURI(prefix: String?): String {
        TODO("not yet needed")
    }

    override fun isEqualNode(arg: Node?): Boolean {
        TODO("not yet needed")
    }


}