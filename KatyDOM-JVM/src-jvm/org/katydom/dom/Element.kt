//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.dom

interface Element : Node {

    val tagName: String

    fun removeAttribute(name: String)

    fun setAttribute(name: String, value: String)

}