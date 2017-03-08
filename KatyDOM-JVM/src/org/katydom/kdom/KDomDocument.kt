//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.kdom

import org.w3c.dom.*

/**
 * Implementation of DOM Document for generating HTML text for testing or server-side rendering.
 */
class KDomDocument : KDomNode(), Document {
    override fun createElement(tagName: String): KDomElement {
        return KDomElement(this,tagName)
    }

    override fun createTextNode(data: String): Text {
        return KDomText(this,data)
    }

    override fun getNodeName(): String {
        return "#document"
    }

    override fun getOwnerDocument(): Document {
        return this
    }

    override fun toHtmlString(indent: Int): String {
        throw UnsupportedOperationException( "Whole document is not meant to be converted to HTML" )
    }

////

    override fun setXmlVersion(xmlVersion: String?) {
        TODO("not yet needed")
    }

    override fun importNode(importedNode: Node?, deep: Boolean): KDomNode {
        TODO("not yet needed")
    }

    override fun createComment(data: String?): Comment {
        TODO("not yet needed")
    }

    override fun createProcessingInstruction(target: String?, data: String?): ProcessingInstruction {
        TODO("not yet needed")
    }

    override fun getImplementation(): DOMImplementation {
        TODO("not yet needed")
    }

    override fun getXmlVersion(): String {
        TODO("not yet needed")
    }

    override fun getElementsByTagName(tagname: String?): NodeList {
        TODO("not yet needed")
    }

    override fun adoptNode(source: Node?): KDomNode {
        TODO("not yet needed")
    }

    override fun normalizeDocument() {
        TODO("not yet needed")
    }

    override fun getDomConfig(): DOMConfiguration {
        TODO("not yet needed")
    }

    override fun getXmlStandalone(): Boolean {
        TODO("not yet needed")
    }

    override fun createCDATASection(data: String?): CDATASection {
        TODO("not yet needed")
    }

    override fun setStrictErrorChecking(strictErrorChecking: Boolean) {
        TODO("not yet needed")
    }

    override fun setDocumentURI(documentURI: String?) {
        TODO("not yet needed")
    }

    override fun setXmlStandalone(xmlStandalone: Boolean) {
        TODO("not yet needed")
    }

    override fun getXmlEncoding(): String {
        TODO("not yet needed")
    }

    override fun getElementsByTagNameNS(namespaceURI: String?, localName: String?): NodeList {
        TODO("not yet needed")
    }

    override fun renameNode(n: Node?, namespaceURI: String?, qualifiedName: String?): KDomNode {
        TODO("not yet needed")
    }

    override fun getDocumentElement(): KDomElement {
        TODO("not yet needed")
    }

    override fun createEntityReference(name: String?): EntityReference {
        TODO("not yet needed")
    }

    override fun createElementNS(namespaceURI: String?, qualifiedName: String?): KDomElement {
        TODO("not yet needed")
    }

    override fun getStrictErrorChecking(): Boolean {
        TODO("not yet needed")
    }

    override fun getInputEncoding(): String {
        TODO("not yet needed")
    }

    override fun getDocumentURI(): String {
        TODO("not yet needed")
    }

    override fun getDoctype(): DocumentType {
        TODO("not yet needed")
    }

    override fun createDocumentFragment(): DocumentFragment {
        TODO("not yet needed")
    }

    override fun createAttributeNS(namespaceURI: String?, qualifiedName: String?): Attr {
        TODO("not yet needed")
    }

    override fun getElementById(elementId: String?): KDomElement {
        TODO("not yet needed")
    }

    override fun createAttribute(name: String?): Attr {
        TODO("not yet needed")
    }

}