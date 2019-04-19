//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.edges

import o.barlom.domain.metamodel.api.vertices.DirectedEdgeType
import o.barlom.domain.metamodel.api.vertices.Package
import x.barlom.infrastructure.uuids.Uuid

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


    override fun remove() {

        // Unregister both ends.
        parent.removeDirectedEdgeTypeContainment(this)
        child.removeDirectedEdgeTypeContainment(this)

    }

}
