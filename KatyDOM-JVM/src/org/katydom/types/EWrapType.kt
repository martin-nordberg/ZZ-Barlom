//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.types

/**
 * Enumeration of allowed values for the wrap attribute of a textarea element.
 */
enum class EWrapType {

    HARD,
    SOFT;

    fun toHtmlString(): String {
        return when (this) {
            HARD -> "hard"
            SOFT -> "soft"
        }
    }

}
