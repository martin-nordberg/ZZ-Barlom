//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EAbstractness


/**
 * Implementation class for vertex types.
 */
abstract class VertexType(
    id: String,
    name: String,

    /** Whether this vertex type is abstract. */
    val abstractness: EAbstractness,

    /** The super type of this vertex type. */
    val superType: VertexType

) : PackagedElement(id, name) {

    abstract val attributes: List<VertexAttributeDecl>

    fun isSubTypeOf(vertexType: VertexType): Boolean {
        return this == vertexType || this.superType.isSubTypeOf(vertexType)
    }

}
