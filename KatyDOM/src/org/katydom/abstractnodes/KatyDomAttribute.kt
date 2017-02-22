//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Class representing a KDom virtual attribute beyond those defined in the API. Similar to DOM Attr but not inheriting
 * from KatyDomNode.
 */
class KatyDomAttribute(

    /** The name of the attribute. */
    val name: String,

    /** The string value of the attribute. */
    val value: String

)

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
