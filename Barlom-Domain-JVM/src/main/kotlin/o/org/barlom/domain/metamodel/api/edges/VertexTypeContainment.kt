//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.Package
import o.org.barlom.domain.metamodel.api.vertices.VertexType
import x.org.barlom.infrastructure.uuids.Uuid

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


    override fun remove() {

        // Unregister both ends.
        parent.removeVertexTypeContainment(this)
        child.removeVertexTypeContainment(this)

    }

}