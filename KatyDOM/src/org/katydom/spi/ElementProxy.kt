//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.spi

/**
 * Interface to an element proxy - encapsulates a DOM or other Element implementation.
 */
interface ElementProxy : NodeProxy {

    fun setAttribute(qualifiedName: String, value: String)

}