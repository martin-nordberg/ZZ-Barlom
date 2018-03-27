//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.Event2Message
import org.katydom.api.EventHandler
import org.katydom.api.MouseEvent2Message
import org.katydom.api.MouseEventHandler
import org.katydom.types.EEventType
import org.katydom.types.EMouseEventType
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * KatyDOM content builder for attributes and event handlers available to all nodes. Serves as a base class for more
 * specialized content builders that also add child nodes of the right types for given context.
 */
@Suppress("unused")
@KatyDomContentBuilderDsl
open class KatyDomAttributesContentBuilder<Message>(

    /** The element whose attributes are being set. */
    protected val element: KatyDomHtmlElement<Message>,

    /** Dispatcher of event handling results for when we want event handling to be reactive or Elm-like. */
    private val dispatchMessage: (message:Message)->Unit = {}
            // TODO: no default

) {

    /**
     * Adds one attribute to the content.
     * @param name the name of the attribute.
     * @param value the string value of the attribute.
     */
    fun attribute(
        name: String,
        value: String
    ) {
        if (name.startsWith("data-")) {
            // TODO: Warning: use data(..) instead.
            element.setData(name.substring(5), value)
        }
        element.setAttribute(name, value)
    }

    /**
     * Adds multiple attributes to the content being built.
     * @param pairs a list of the names (first) and values (second) for the attributes to add.
     */
    fun attributes(vararg pairs: Pair<String, String>) {
        pairs.forEach { pair ->
            attribute(pair.first, pair.second)
        }
    }

    /**
     * Adds multiple classes to the content being built.
     * @param pairs a list of the classes (first) and on/off flags (second) for the classes to add.
     */
    fun classes(vararg pairs: Pair<String, Boolean>) {
        val classList = pairs.filter { it.second }.map { it.first }
        element.addClasses(classList)
    }

    /**
     * Adds one data attribute to the content being built.
     * @param name the name of the data attribute. May have the "data-" prefix omitted.
     * @param value the string value of the data attribute.
     */
    fun data(
        name: String,
        value: String
    ) {
        if (name.startsWith("data-")) {
            // TODO: Warning "data-" prefix not needed.
            element.setData(name.substring(5), value)
        }
        else {
            element.setData(name, value)
        }
    }

    /**
     * Adds multiple data attributes to the content being built.
     * @param pairs a list of the names (first) and values (second) for the attributes to add. Names may have the
     * "data-" prefix omitted.
     */
    fun dataset(vararg pairs: Pair<String, String>) {
        pairs.forEach { pair ->
            data(pair.first, pair.second)
        }
    }

    /**
     * Adds an event handler for change events.
     * @param handler the callback that listens to change events.
     */
    fun onchange(handler: Event2Message<Message>) {
        element.addEventHandler(EEventType.CHANGE) {
            e: Event ->
            for ( msg in handler(e) ) {
                dispatchMessage(msg)
            }
        }
    }

    /**
     * Adds an event handler for mouse clicks.
     * @param handler the callback that listens to mouse clicks.
     */
    fun onclick(handler: MouseEvent2Message<Message>) {
        element.addMouseEventHandler(EMouseEventType.CLICK) {
            e: MouseEvent ->
            for ( msg in handler(e) ) {
                dispatchMessage(msg)
            }
        }
    }

    /**
     * Adds an event handler for blur events.
     * @param handler the callback that listens to blur events.
     */
    fun onblur(handler: Event2Message<Message>) {
        element.addEventHandler(EEventType.BLUR) {
            e: Event ->
            for (msg in handler(e)) {
                dispatchMessage(msg)
            }
        }
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

