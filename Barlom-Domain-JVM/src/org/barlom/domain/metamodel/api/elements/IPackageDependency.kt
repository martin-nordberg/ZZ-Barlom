//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

/**
 * Interface for Barlom package dependencies.
 */
interface IPackageDependency : IDocumentedElement {

    /** The package that depends upon the supplier package. */
    val clientPackage: IPackage

    /** The package that is depended upon by the client package. */
    val supplierPackage: IPackage

    // TODO: may want other attributes such as whether the dependency is public or internal

}
