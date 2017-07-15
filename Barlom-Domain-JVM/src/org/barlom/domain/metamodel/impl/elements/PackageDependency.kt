//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IDocumentedElement
import org.barlom.domain.metamodel.api.elements.IPackageDependency

/**
 * Implementation class for package dependencies.
 */
internal data class PackageDependency(

    override val id: String,
    override val clientPackage: Package,
    override val supplierPackage: Package

) : IPackageDependency {

    init {

        // Register both ends.
        clientPackage.addSupplierPackageDependency(this)
        supplierPackage.addClientPackageDependency(this)

    }

}
