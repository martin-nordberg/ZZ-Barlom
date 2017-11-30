//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.AbstractElement
import org.barlom.domain.metamodel.api.vertices.VertexAttributeType
import org.barlom.domain.metamodel.api.vertices.VertexType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom vertex attribute type containment.
 */
class VertexAttributeTypeContainment internal constructor(

    override val id: Uuid,
    val vertexType: VertexType,
    val attributeType: VertexAttributeType

) : AbstractElement() {

    init {

        // Register both ends.
        vertexType.addVertexAttributeTypeContainment(this)
        attributeType.addVertexAttributeTypeContainment(this)

    }

}