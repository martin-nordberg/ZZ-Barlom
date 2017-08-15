//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.IDirectedEdgeType

/**
 * Interface for Barlom directed type containment.
 */
interface IDirectedEdgeTypeContainment : IPackagedElementContainment {

    /** The directed edge type that is contained by the parent package. */
    override val child: IDirectedEdgeType

}
