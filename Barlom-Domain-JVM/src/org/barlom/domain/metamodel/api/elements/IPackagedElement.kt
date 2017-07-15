//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

/**
 * Interface for an abstract packaged element.
 */
interface IPackagedElement : INamedElement {

    /** The package containing this model element. */
    val parentPackage: IPackage

}

