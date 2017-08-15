//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.edges

import org.barlom.domain.metamodel.api.edges.IPackageContainment
import org.barlom.domain.metamodel.impl.vertices.IPackageImpl
import org.barlom.domain.metamodel.impl.vertices.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for package containment.
 */
internal data class PackageContainment(

    override val id: Uuid,
    override val parent: IPackageImpl,
    override val child: Package

) : IPackageContainment {

    init {

        // Register both ends.
        parent.addChildPackageContainment(this)
        child.addParentPackageContainment(this)

    }

}
