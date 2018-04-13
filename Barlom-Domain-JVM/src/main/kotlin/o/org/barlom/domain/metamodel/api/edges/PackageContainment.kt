//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.Package
import x.org.barlom.infrastructure.uuids.Uuid

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


    override fun remove() {

        // Unregister both ends.
        parent.removeChildPackageContainment(this)
        child.removeParentPackageContainment(this)

    }

}