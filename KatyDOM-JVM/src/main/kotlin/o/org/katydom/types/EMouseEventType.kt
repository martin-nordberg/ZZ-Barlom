//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.types

//---------------------------------------------------------------------------------------------------------------------

/**
 * Enumeration of DOM mouse event types.
 * See https://w3c.github.io/uievents/#events-mouseevents
 */
enum class EMouseEventType {

    CLICK,
    DBL_CLICK,
    MOUSE_DOWN,
    MOUSE_ENTER,
    MOUSE_LEAVE,
    MOUSE_MOVE,
    MOUSE_OUT,
    MOUSE_OVER,
    MOUSE_UP;

    /**
     * The name of the event in the DOM.
     */
    val domName: String
        get() {
            return when (this) {
                CLICK       -> "click"
                DBL_CLICK   -> "dblclick"
                MOUSE_DOWN  -> "mousedown"
                MOUSE_ENTER -> "mouseenter"
                MOUSE_LEAVE -> "mouseleave"
                MOUSE_MOVE  -> "mousemove"
                MOUSE_OUT   -> "mouseout"
                MOUSE_OVER  -> "mouseover"
                MOUSE_UP    -> "mouseup"
            }
        }

}

//---------------------------------------------------------------------------------------------------------------------

