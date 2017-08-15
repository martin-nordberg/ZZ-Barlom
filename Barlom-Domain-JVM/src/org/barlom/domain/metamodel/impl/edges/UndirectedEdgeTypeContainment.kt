//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.edges

import org.barlom.domain.metamodel.api.edges.IUndirectedEdgeTypeContainment
import org.barlom.domain.metamodel.impl.vertices.INonRootPackageImpl
import org.barlom.domain.metamodel.impl.vertices.UndirectedEdgeType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for undirected edge type containment.
 */
internal data class UndirectedEdgeTypeContainment(

    override val id: Uuid,
    override val parent: INonRootPackageImpl,
    override val child: UndirectedEdgeType

) : IUndirectedEdgeTypeContainment {

    init {

        // Register both ends.
        parent.addUndirectedEdgeTypeContainment(this)
        child.addUndirectedEdgeTypeContainment(this)

    }

}
