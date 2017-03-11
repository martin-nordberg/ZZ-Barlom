package org.katydom.types

/**
 * Enumeration of allowed values for the ol element's type attribute.
 * See https://www.w3.org/TR/html5/grouping-content.html#attr-ol-type.
 */
enum class OrderedListType {

    DECIMAL_NUMBERS,
    LOWER_CASE_LETTERS,
    UPPER_CASE_LETTERS,
    LOWER_CASE_ROMAN_NUMERALS,
    UPPER_CASE_ROMAN_NUMERALS;

    fun toHtmlString(): String {
        when (this) {
            DECIMAL_NUMBERS           -> return "1"
            LOWER_CASE_LETTERS        -> return "a"
            UPPER_CASE_LETTERS        -> return "A"
            LOWER_CASE_ROMAN_NUMERALS -> return "i"
            UPPER_CASE_ROMAN_NUMERALS -> return "I"
        }
    }

}