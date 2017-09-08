//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices.AbstractEdgeType
import org.barlom.domain.metamodel.api.vertices.EdgeAttributeType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom vertex attribute type containment.
 */
class EdgeAttributeTypeContainment internal constructor(

    override val id: Uuid,
    val edgeType: AbstractEdgeType,
    val attributeType: EdgeAttributeType

) : AbstractDocumentedElement() {

    init {

        // Register both ends.
        edgeType.addEdgeAttributeTypeContainment(this)
        attributeType.addEdgeAttributeTypeContainment(this)

    }

}