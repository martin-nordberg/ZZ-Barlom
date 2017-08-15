//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.edges

import org.barlom.domain.metamodel.api.edges.IPackageDependency
import org.barlom.domain.metamodel.impl.vertices.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for package dependencies.
 */
internal data class PackageDependency(

    override val id: Uuid,
    override val clientPackage: Package,
    override val supplierPackage: Package

) : IPackageDependency {

    init {

        // Register both ends.
        clientPackage.addSupplierPackageDependency(this)
        supplierPackage.addClientPackageDependency(this)

    }

}
