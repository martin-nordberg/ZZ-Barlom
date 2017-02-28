//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.spi

/**
 * Interface to a node proxy - encapsulates a DOM or other Node implementation. Allows a level of separation for
 * testing or server side rendering.
 */
interface NodeProxy {

    fun appendChild(childNode: NodeProxy): NodeProxy

    fun insertBefore(newChildNode: NodeProxy, referenceChildNode: NodeProxy?): NodeProxy

    val ownerDocument: DocumentProxy

    val parentNode: NodeProxy?

    fun removeChild(childNode: NodeProxy): NodeProxy

}