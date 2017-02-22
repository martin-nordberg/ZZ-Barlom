//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Topmost abstract base class for KatyDOM virtual DOM. Corresponds to DOM Node.
 */
abstract class KatyDomNode(

    /** The child nodes within this node. Defaults to an empty list. */
    val childNodes: List<KatyDomNode> = arrayListOf()

) {

    /** The name of this node (usually the HTML tag name, otherwise a pseudo tag name like "#text"). */
    abstract val nodeName: String

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
