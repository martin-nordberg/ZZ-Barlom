//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.DirectedEdgeType
import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom directed edge type containment.
 */
class DirectedEdgeTypeContainment internal constructor(

    override val id: Uuid,
    override val parent: Package,
    override val child: DirectedEdgeType

) : AbstractEdgeTypeContainment() {

    init {

        check(!child.isRoot || parent.isRoot) {
            "Root directed edge type must be contained by root package."
        }

        // Register both ends.
        parent.addDirectedEdgeTypeContainment(this)
        child.addDirectedEdgeTypeContainment(this)

    }

}