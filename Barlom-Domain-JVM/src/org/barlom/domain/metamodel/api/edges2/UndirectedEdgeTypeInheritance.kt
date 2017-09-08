//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.UndirectedEdgeType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom undirected edge type inheritance
 */
class UndirectedEdgeTypeInheritance internal constructor(

    override val id: Uuid,
    val superType: UndirectedEdgeType,
    val subType: UndirectedEdgeType

) : AbstractDocumentedElement() {

    init {

        require(!subType.isRoot) { "Root undirected edge type cannot have a super type." }

        // Register both ends.
        superType.addSubTypeUndirectedEdgeTypeInheritance(this)
        subType.addSuperTypeUndirectedEdgeTypeInheritance(this)

    }

}