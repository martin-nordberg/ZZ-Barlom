//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.kdom

import org.katydom.dom.Comment
import org.katydom.infrastructure.indent

/**
 * Implementation of DOM Text for generating HTML text for testing or server-side rendering.
 */
open class KDomComment(
    override val ownerDocument: KDomDocument,
    private var _data: String
) : KDomNode(), Comment {

    override var data: String
        get() = _data
        set(value) {
            _data = value
        }

    override val nodeName = "#text"

    override var nodeValue: String?
        get() = _data
        set(value) {
            _data = value ?: ""
        }

    override fun toHtmlString(indent: Int): String {
        val result = StringBuilder()
        result.indent(indent)
        result.append(_data)
        return result.toString()
    }

}
