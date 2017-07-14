//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements


/**
 * Implementation of abstract packaged element.
 */
abstract class PackagedElement(

    id: String,
    name: String

) : NamedElement( id, name ) {

    abstract val parentPackage : Package

}

