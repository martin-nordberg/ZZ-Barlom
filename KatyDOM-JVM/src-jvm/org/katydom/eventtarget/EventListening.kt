
package org.katydom.eventtarget

import org.w3c.dom.Node
import org.w3c.dom.events.Event

/**
 * Event target NO-OP extension functions for the JVM.
 */

fun Node.addEventListener(eventName: String, eventHandler:(Event)->Unit ) {
    // do nothing in JVM
}

fun Node.removeEventListener(eventName: String, eventHandler:(Event)->Unit) {
    // do nothing in JVM
}
