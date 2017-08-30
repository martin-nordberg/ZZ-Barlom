//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.domain.metamodel.api.vertices2.VertexType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom vertex type containment.
 */
class VertexTypeContainment internal constructor(

    override val id: Uuid,
    override val parent: Package,
    override val child: VertexType

) : AbstractPackagedElementContainment() {

    init {

        check(!child.isRoot || parent.isRoot) {
            "Root vertex type must be contained by root package."
        }

        // Register both ends.
        parent.addVertexTypeContainment(this)
        child.addVertexTypeContainment(this)

    }

}