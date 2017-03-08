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
open class KDomText(
    private val _ownerDocument: KDomDocument,
    private val _data : String
) : KDomNode(), Text {

    override fun getData(): String {
        return _data
    }

    override fun getNodeName(): String {
        return "#text"
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

private val FIFTY_SPACES = "                                                  "
