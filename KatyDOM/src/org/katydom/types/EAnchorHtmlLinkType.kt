//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.types

/**
 * Enumeration of allowed values for the rel or rev attribute of an <a> or <area> tag.
 * See https://www.w3.org/TR/html51/links.html#allowed-keywords-and-their-meanings.
 */
enum class EAnchorHtmlLinkType {

    ALTERNATE,
    AUTHOR,
    BOOKMARK,
    HELP,
    LICENSE,
    NEXT,
    NOFOLLOW,
    NOREFERRER,
    PREV,
    SEARCH,
    TAG;

    fun toHtmlString(): String {
        when (this) {
            ALTERNATE -> return "alternate"
            AUTHOR -> return "author"
            BOOKMARK -> return "bookmark"
            HELP -> return "help"
            LICENSE -> return "license"
            NEXT -> return "next"
            NOFOLLOW -> return "nofollow"
            NOREFERRER -> return "noreferrer"
            PREV -> return "prev"
            SEARCH -> return "search"
            TAG -> return "tag"
        }
    }

}
