//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.IDocumentedElement

/**
 * Implementation class for package dependencies.
 */
class PackageDependency(

    override val id: String,

    val clientPackage: Package,

    val supplierPackage: Package

) : IDocumentedElement {

    init {

        // Register both ends.
        clientPackage.addSupplierPackageDependency(this)
        supplierPackage.addClientPackageDependency(this)

    }

}
