//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.edges

import o.barlom.domain.metamodel.api.vertices.Package
import o.barlom.domain.metamodel.api.vertices.UndirectedEdgeType
import x.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom undirected edge type containment.
 */
class UndirectedEdgeTypeContainment internal constructor(

    override val id: Uuid,
    override val parent: Package,
    override val child: UndirectedEdgeType

) : AbstractEdgeTypeContainment() {

    init {

        check(!child.isRoot || parent.isRoot) {
            "Root undirected edge type must be contained by root package."
        }

        // Register both ends.
        parent.addUndirectedEdgeTypeContainment(this)
        child.addUndirectedEdgeTypeContainment(this)

    }


    override fun remove() {

        // Unregister both ends.
        parent.removeUndirectedEdgeTypeContainment(this)
        child.removeUndirectedEdgeTypeContainment(this)

    }

}
