//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.IPackage

/**
 * Interface for Barlom package containment.
 */
interface IPackageContainment : IPackagedElementContainment {

    /** The package that is contained by the parent package. */
    override val child: IPackage

}
