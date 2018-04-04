//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.kdom

import org.katydom.dom.Text
import org.katydom.infrastructure.indent

/**
 * Implementation of DOM Document for generating HTML text for testing or server-side rendering.
 */
open class KDomText(
    private val _ownerDocument: KDomDocument,
    private var _data: String
) : KDomNode(), Text {

    override fun getData(): String {
        return _data
    }

    override fun getNodeName(): String {
        return "#text"
    }

    override fun getNodeValue(): String {
        return _data
    }

    override fun setNodeValue(nodeValue: String?) {
        _data = nodeValue ?: ""
    }

    override fun getOwnerDocument(): KDomDocument {
        return _ownerDocument
    }

    override fun toHtmlString(indent: Int): String {
        val result = StringBuilder()
        result.indent(indent)
        result.append(_data)
        return result.toString()
    }

////

    override fun insertData(offset: Int, arg: String?) {
        TODO("not yet needed")
    }

    override fun isElementContentWhitespace(): Boolean {
        TODO("not yet needed")
    }

    override fun setData(data: String?) {
        TODO("not yet needed")
    }

    override fun appendData(arg: String?) {
        TODO("not yet needed")
    }

    override fun getLength(): Int {
        TODO("not yet needed")
    }

    override fun replaceData(offset: Int, count: Int, arg: String?) {
        TODO("not yet needed")
    }

    override fun deleteData(offset: Int, count: Int) {
        TODO("not yet needed")
    }

    override fun substringData(offset: Int, count: Int): String {
        TODO("not yet needed")
    }

    override fun replaceWholeText(content: String?): Text {
        TODO("not yet needed")
    }

    override fun splitText(offset: Int): Text {
        TODO("not yet needed")
    }

    override fun getWholeText(): String {
        TODO("not yet needed")
    }

}
