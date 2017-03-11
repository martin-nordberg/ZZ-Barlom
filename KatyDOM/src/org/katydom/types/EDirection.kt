package org.katydom.types

/**
 * Enumeration of allowed values for the dir attribute.
 * See https://www.w3.org/TR/html5/dom.html#the-dir-attribute.
 */
enum class EDirection {

    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    AUTO;

    fun toHtmlString(): String {
        when (this) {
            LEFT_TO_RIGHT -> return "ltr"
            RIGHT_TO_LEFT -> return "rtl"
            AUTO          -> return "auto"
        }
    }

}
