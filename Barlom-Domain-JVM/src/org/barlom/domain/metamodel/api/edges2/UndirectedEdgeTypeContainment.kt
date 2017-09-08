//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.domain.metamodel.api.vertices2.UndirectedEdgeType
import org.barlom.infrastructure.uuids.Uuid

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

}