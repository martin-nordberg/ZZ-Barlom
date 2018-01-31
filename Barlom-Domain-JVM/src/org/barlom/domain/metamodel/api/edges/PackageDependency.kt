//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.AbstractElement
import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom package dependencies.
 */
class PackageDependency internal constructor(

    override val id: Uuid,
    val consumer: Package,
    val supplier: Package

) : AbstractElement() {

    init {

        require(!consumer.isRoot) { "Root package cannot have dependencies." }
        require(!supplier.isRoot) { "Dependency upon root package is implicit." }

        // Register both ends.
        consumer.addSupplierPackageDependency(this)
        supplier.addConsumerPackageDependency(this)

    }


    override fun remove() {

        // Unregister both ends.
        consumer.removeSupplierPackageDependency(this)
        supplier.removeConsumerPackageDependency(this)

    }

}