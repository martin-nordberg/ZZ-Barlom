//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.edges

import o.barlom.domain.metamodel.api.vertices.AbstractElement
import o.barlom.domain.metamodel.api.vertices.VertexType
import x.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom vertex type inheritance
 */
class VertexTypeInheritance internal constructor(

    override val id: Uuid,
    val superType: VertexType,
    val subType: VertexType

) : AbstractElement() {

    init {

        require(!subType.isRoot) { "Root vertex type cannot have a super type." }

        // Register both ends.
        superType.addSubTypeVertexTypeInheritance(this)
        subType.addSuperTypeVertexTypeInheritance(this)

    }


    override fun remove() {

        // Unregister both ends.
        superType.removeSubTypeVertexTypeInheritance(this)
        subType.removeSuperTypeVertexTypeInheritance(this)

    }

}
