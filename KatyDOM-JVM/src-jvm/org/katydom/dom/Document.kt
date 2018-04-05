//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.dom

interface Document : Node {

    fun createComment(data: String): Comment

    fun createElement(tagName: String): Element

    fun createTextNode(data: String): Text

}
