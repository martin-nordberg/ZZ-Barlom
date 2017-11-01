//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.types

/**
 * Enumeration of allowed values for the inputmode attribute of an input element.
 * See https://www.w3.org/TR/html51/sec-forms.html#element-attrdef-input-inputmode.
 */
enum class EInputMode {

    FULL_WIDTH_LATIN,
    KANA,
    KANA_NAME,
    KATAKANA,
    LATIN,
    LATIN_NAME,
    LATIN_PROSE,
    NUMERIC,
    VERBATIM;

    fun toHtmlString(): String {
        return when (this) {
            FULL_WIDTH_LATIN -> "full-width-latin"
            KANA -> "kana"
            KANA_NAME -> "kana-name"
            KATAKANA -> "katakana"
            LATIN -> "latin"
            LATIN_NAME -> "latin-name"
            LATIN_PROSE -> "latin-prose"
            NUMERIC -> "numeric"
            VERBATIM -> "verbatim"
        }
    }

}
