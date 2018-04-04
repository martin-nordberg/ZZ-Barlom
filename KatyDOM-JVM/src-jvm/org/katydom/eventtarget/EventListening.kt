//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.eventtarget

import org.katydom.dom.Node
import org.katydom.dom.events.Event

/**
 * Event target NO-OP extension functions for the JVM.
 */

fun Node.addEventListener(eventName: String, eventHandler: (Event) -> Unit) {
    // do nothing in JVM
}

fun Node.removeEventListener(eventName: String, eventHandler: (Event) -> Unit) {
    // do nothing in JVM
}
