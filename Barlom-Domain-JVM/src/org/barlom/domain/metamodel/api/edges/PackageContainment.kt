//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom package containment.
 */
class PackageContainment internal constructor(

    override val id: Uuid,
    override val parent: Package,
    override val child: Package

) : AbstractPackagedElementContainment() {

    init {

        check(!child.isRoot) {
            "Root package cannot have a parent."
        }

        // Register both ends.
        parent.addChildPackageContainment(this)
        child.addParentPackageContainment(this)

    }

}