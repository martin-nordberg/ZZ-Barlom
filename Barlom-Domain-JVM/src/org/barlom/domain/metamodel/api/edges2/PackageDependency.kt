//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom package dependencies.
 */
class PackageDependency internal constructor(

    override val id: Uuid,
    val consumer: Package,
    val supplier: Package

) : AbstractDocumentedElement() {

    init {

        require(!consumer.isRoot) { "Root package cannot have dependencies." }
        require(!supplier.isRoot) { "Dependency upon root package is implicit." }

        // Register both ends.
        consumer.addSupplierPackageDependency(this)
        supplier.addConsumerPackageDependency(this)

    }

}