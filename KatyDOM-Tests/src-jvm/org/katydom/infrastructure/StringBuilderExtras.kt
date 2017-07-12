//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

/**
 * Indents the current line a given number of spaces.
 */
fun StringBuilder.indent(spaces: Int) {
    this.append(FIFTY_SPACES.substring(0, Math.min(spaces, 50)))
}


private val FIFTY_SPACES = "                                                  "
