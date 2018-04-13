//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.AbstractElement
import o.org.barlom.domain.metamodel.api.vertices.DirectedEdgeType
import x.org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom directed edge type inheritance
 */
class DirectedEdgeTypeInheritance internal constructor(

    override val id: Uuid,
    val superType: DirectedEdgeType,
    val subType: DirectedEdgeType

) : AbstractElement() {

    init {

        require(!subType.isRoot) { "Root directed edge type cannot have a super type." }

        // Register both ends.
        superType.addSubTypeDirectedEdgeTypeInheritance(this)
        subType.addSuperTypeDirectedEdgeTypeInheritance(this)

    }

    override fun remove() {

        // Unregister both ends.
        superType.removeSubTypeDirectedEdgeTypeInheritance(this)
        subType.removeSuperTypeDirectedEdgeTypeInheritance(this)

    }

}