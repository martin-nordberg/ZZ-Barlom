//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.types

/**
 * Enumeration of allowed values for the method attribute for a form element.
 * See https://www.w3.org/TR/html51/sec-forms.html#forms-method.
 */
enum class EFormSubmissionMethod {

    GET,
    POST;

    fun toHtmlString(): String {
        when (this) {
            GET  -> return "get"
            POST -> return "post"
        }
    }

}
