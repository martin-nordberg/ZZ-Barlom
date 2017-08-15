//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IPackage
import org.barlom.domain.metamodel.impl.edges.PackageContainment

/**
 * Internal interface to a package or root package.
 */
internal interface IPackageImpl : IPackage {

    /** Registers the given package containment in this package. */
    fun addChildPackageContainment(packageContainment: PackageContainment)

}