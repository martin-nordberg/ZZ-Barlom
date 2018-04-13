//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.AbstractElement
import o.org.barlom.domain.metamodel.api.vertices.UndirectedEdgeType
import x.org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom undirected edge type inheritance
 */
class UndirectedEdgeTypeInheritance internal constructor(

    override val id: Uuid,
    val superType: UndirectedEdgeType,
    val subType: UndirectedEdgeType

) : AbstractElement() {

    init {

        require(!subType.isRoot) { "Root undirected edge type cannot have a super type." }

        // Register both ends.
        superType.addSubTypeUndirectedEdgeTypeInheritance(this)
        subType.addSuperTypeUndirectedEdgeTypeInheritance(this)

    }


    override fun remove() {

        // Unregister both ends.
        superType.removeSubTypeUndirectedEdgeTypeInheritance(this)
        subType.removeSuperTypeUndirectedEdgeTypeInheritance(this)

    }

}