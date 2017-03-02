//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//
package org.katydom.kdom

import org.katydom.infrastructure.indent
import org.w3c.dom.*

/**
 * Implementation of DOM Document for generating HTML text for testing or server-side rendering.
 */
open class KDomElement(
    private val _ownerDocument: KDomDocument,
    private val _tagName: String
) : KDomNode(), Element {
    private val _attributes : MutableMap<String,String> = mutableMapOf()

    override fun getOwnerDocument(): KDomDocument {
        return _ownerDocument
    }

    override fun getTagName(): String {
        return _tagName
    }

    override fun setAttribute(name: String, value: String) {
        _attributes.put(name, value)
    }

    override fun toHtmlString(indent : Int) : String {

        val result = StringBuilder()

        result.indent(indent)
        result.append("<",_tagName)

        for ( attr in _attributes ) {
            result.append(" ",attr.key,"=\"",attr.value,"\"" )
        }

        var child = this.firstChild

        if ( child == null ) {
            result.append("></",_tagName,">")
        }
        else {
            result.appendln(">")

            while (child != null) {
                result.appendln(child.toHtmlString(indent + 2))
                child = child.nextSibling
            }

            result.indent(indent)
            result.append("</",_tagName,">")
        }

        return result.toString()

    }

////

    override fun setIdAttribute(name: String?, isId: Boolean) {
        TODO("not yet needed")
    }

    override fun setIdAttributeNS(namespaceURI: String?, localName: String?, isId: Boolean) {
        TODO("not yet needed")
    }

    override fun getAttributeNodeNS(namespaceURI: String?, localName: String?): Attr {
        TODO("not yet needed")
    }

    override fun getElementsByTagName(name: String?): NodeList {
        TODO("not yet needed")
    }

    override fun setAttributeNodeNS(newAttr: Attr?): Attr {
        TODO("not yet needed")
    }

    override fun getAttribute(name: String?): String {
        TODO("not yet needed")
    }

    override fun setIdAttributeNode(idAttr: Attr?, isId: Boolean) {
        TODO("not yet needed")
    }

    override fun setAttributeNode(newAttr: Attr?): Attr {
        TODO("not yet needed")
    }

    override fun removeAttributeNS(namespaceURI: String?, localName: String?) {
        TODO("not yet needed")
    }

    override fun getElementsByTagNameNS(namespaceURI: String?, localName: String?): NodeList {
        TODO("not yet needed")
    }

    override fun hasAttributeNS(namespaceURI: String?, localName: String?): Boolean {
        TODO("not yet needed")
    }

    override fun getAttributeNode(name: String?): Attr {
        TODO("not yet needed")
    }

    override fun getSchemaTypeInfo(): TypeInfo {
        TODO("not yet needed")
    }

    override fun hasAttribute(name: String?): Boolean {
        TODO("not yet needed")
    }

    override fun setAttributeNS(namespaceURI: String?, qualifiedName: String?, value: String?) {
        TODO("not yet needed")
    }

    override fun removeAttribute(name: String?) {
        TODO("not yet needed")
    }

    override fun removeAttributeNode(oldAttr: Attr?): Attr {
        TODO("not yet needed")
    }

    override fun getAttributeNS(namespaceURI: String?, localName: String?): String {
        TODO("not yet needed")
    }

}

