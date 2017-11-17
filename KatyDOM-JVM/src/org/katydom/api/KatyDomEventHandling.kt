//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

/**
 * Exception indicating that default handling of an event should be cancelled.
 */
@Suppress("unused")
class EventCancellationException : RuntimeException()

/**
 * Generic event handler: input is any event. To cancel an event throw EventCancellationException.
 */
typealias EventHandler = (e: Event) -> Unit

/**
 * Mouse event handler: input is a mouse event. To cancel an event throw EventCancellationException.
 */
typealias MouseEventHandler = (e: MouseEvent) -> Unit
