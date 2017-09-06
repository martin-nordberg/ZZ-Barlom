//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.VertexAttributeType
import org.barlom.domain.metamodel.api.vertices2.VertexType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom vertex attribute type containment.
 */
class VertexAttributeTypeContainment internal constructor(

    override val id: Uuid,
    val vertexType: VertexType,
    val attributeType: VertexAttributeType

) : AbstractDocumentedElement() {

    init {

        // Register both ends.
        vertexType.addVertexAttributeTypeContainment(this)
        attributeType.addVertexAttributeTypeContainment(this)

    }

}