//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.types

/**
 * Enumeration of allowed values for the ol element's type attribute.
 * See https://www.w3.org/TR/html5/grouping-content.html#attr-ol-type.
 */
enum class EOrderedListType {

    DECIMAL_NUMBERS,
    LOWER_CASE_LETTERS,
    UPPER_CASE_LETTERS,
    LOWER_CASE_ROMAN_NUMERALS,
    UPPER_CASE_ROMAN_NUMERALS;

    fun toHtmlString(): String {
        return when (this) {
            DECIMAL_NUMBERS           -> "1"
            LOWER_CASE_LETTERS        -> "a"
            UPPER_CASE_LETTERS        -> "A"
            LOWER_CASE_ROMAN_NUMERALS -> "i"
            UPPER_CASE_ROMAN_NUMERALS -> "I"
        }
    }

}
