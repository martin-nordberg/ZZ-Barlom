//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.AbstractElement
import o.org.barlom.domain.metamodel.api.vertices.VertexAttributeType
import o.org.barlom.domain.metamodel.api.vertices.VertexType
import x.org.barlom.infrastructure.uuids.Uuid

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


    override fun remove() {

        // Unregister both ends.
        vertexType.removeVertexAttributeTypeContainment(this)
        attributeType.removeVertexAttributeTypeContainment(this)

    }

}