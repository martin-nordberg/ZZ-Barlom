//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.DirectedEdgeType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom directed edge type inheritance
 */
class DirectedEdgeTypeInheritance internal constructor(

    override val id: Uuid,
    val superType: DirectedEdgeType,
    val subType: DirectedEdgeType

) : AbstractDocumentedElement() {

    init {

        require(!subType.isRoot) { "Root directed edge type cannot have a super type." }

        // Register both ends.
        superType.addSubTypeDirectedEdgeTypeInheritance(this)
        subType.addSuperTypeDirectedEdgeTypeInheritance(this)

    }

}