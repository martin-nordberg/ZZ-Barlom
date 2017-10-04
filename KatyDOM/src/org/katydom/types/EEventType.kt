//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.types

/**
 * Enumeration of DOM event types.
 */
enum class EEventType {

    BLUR,
    FOCUS;

    /**
     * The name of the event in the DOM.
     */
    val domName: String
        get() {
            return when (this) {
                BLUR  -> "blur"
                FOCUS -> "focus"
            }
        }

}