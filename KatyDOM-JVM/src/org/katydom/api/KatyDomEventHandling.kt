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
 * Generic event handler: input is any [event]. To cancel an event throw EventCancellationException.
 */
typealias EventHandler = (event: Event) -> Unit

/**
 * Mouse event handler: input is a mouse [event]. To cancel an event throw EventCancellationException.
 */
typealias MouseEventHandler = (event: MouseEvent) -> Unit


/**
 * Message-generating event handler: input is any [event], output is a message.
 * To cancel an event throw EventCancellationException.
 */
typealias Event2Message<Message> = (event: Event) -> Iterable<Message>

/**
 * Message-generating event handler: input is any [event], output is a message.
 * To cancel an event throw EventCancellationException.
 */
typealias MouseEvent2Message<Message> = (event: MouseEvent) -> Iterable<Message>
