//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.dom

import org.katydom.dom.events.Event

interface Node {

    val firstChild: Node?

    val lastChild: Node?

    val nextSibling: Node?

    val nodeName: String

    var nodeValue: String?

    val ownerDocument: Document?

    val parentNode: Node?

    fun appendChild(newChild: Node): Node

    fun addEventListener(eventName: String, eventHandler: (Event) -> Unit)

    fun hasChildNodes(): Boolean

    fun insertBefore(newChild: Node, refChild: Node?): Node

    fun removeChild(oldChild: Node): Node

    fun removeEventListener(eventName: String, eventHandler: (Event) -> Unit)

}