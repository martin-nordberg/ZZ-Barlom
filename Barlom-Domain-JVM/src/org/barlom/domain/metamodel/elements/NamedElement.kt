//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements


/**
 * Implementation of abstract named element.
 */
abstract class NamedElement(

    id: String,

    /** The name of this element. */
    val name : String

) : DocumentedElement( id )
