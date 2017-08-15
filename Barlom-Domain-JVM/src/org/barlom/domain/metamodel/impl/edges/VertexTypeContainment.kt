//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.edges

import org.barlom.domain.metamodel.api.edges.IVertexTypeContainment
import org.barlom.domain.metamodel.impl.vertices.INonRootPackageImpl
import org.barlom.domain.metamodel.impl.vertices.VertexType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for vertex type containment.
 */
internal data class VertexTypeContainment(

    override val id: Uuid,
    override val parent: INonRootPackageImpl,
    override val child: VertexType

) : IVertexTypeContainment {

    init {

        // Register both ends.
        parent.addVertexTypeContainment(this)
        child.addParentVertexTypeContainment(this)

    }

}
