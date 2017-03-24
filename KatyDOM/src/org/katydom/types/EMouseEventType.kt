package org.katydom.types

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
            when (this) {
                CLICK       -> return "click"
                DBL_CLICK   -> return "dblclick"
                MOUSE_DOWN  -> return "mousedown"
                MOUSE_ENTER -> return "mouseenter"
                MOUSE_LEAVE -> return "mouseleave"
                MOUSE_MOVE  -> return "mousemove"
                MOUSE_OUT   -> return "mouseout"
                MOUSE_OVER  -> return "mouseover"
                MOUSE_UP    -> return "mouseup"
            }
        }

}
