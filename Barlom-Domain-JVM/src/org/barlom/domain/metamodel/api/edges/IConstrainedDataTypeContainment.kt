//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.IConstrainedDataType

/**
 * Interface for Barlom constrained data type containment.
 */
interface IConstrainedDataTypeContainment : IPackagedElementContainment {

    /** The vertex type that is contained by the parent package. */
    override val child: IConstrainedDataType

}
