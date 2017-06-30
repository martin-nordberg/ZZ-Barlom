//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.types

/**
 * Enumeration of allowed values for the type attribute of a button element.
 */
enum class EButtonType {

    BUTTON,
    MENU,
    RESET,
    SUBMIT;

    fun toHtmlString(): String {
        return when (this) {
            BUTTON -> "button"
            MENU -> "menu"
            RESET -> "reset"
            SUBMIT -> "submit"
        }
    }

}
