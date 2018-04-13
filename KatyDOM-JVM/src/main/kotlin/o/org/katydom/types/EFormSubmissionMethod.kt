//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.types

/**
 * Enumeration of allowed values for the method attribute for a form element.
 * See https://www.w3.org/TR/html51/sec-forms.html#forms-method.
 */
enum class EFormSubmissionMethod {

    GET,
    POST;

    fun toHtmlString(): String {
        return when (this) {
            GET  -> "get"
            POST -> "post"
        }
    }

}