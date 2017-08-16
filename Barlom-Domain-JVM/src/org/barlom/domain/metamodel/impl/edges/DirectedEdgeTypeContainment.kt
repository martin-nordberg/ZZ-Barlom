//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.edges

import org.barlom.domain.metamodel.api.edges.IDirectedEdgeTypeContainment
import org.barlom.domain.metamodel.impl.vertices.DirectedEdgeType
import org.barlom.domain.metamodel.impl.vertices.INonRootPackageImpl
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for directed edge type containment.
 */
internal data class DirectedEdgeTypeContainment(

    override val id: Uuid,
    override val parent: INonRootPackageImpl,
    override val child: DirectedEdgeType

) : IDirectedEdgeTypeContainment {

    init {

        // Register both ends.
        parent.addDirectedEdgeTypeContainment(this)
        child.addDirectedEdgeTypeContainment(this)

    }

}
