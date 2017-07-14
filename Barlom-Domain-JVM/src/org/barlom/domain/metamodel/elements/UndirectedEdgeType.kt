//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EAbstractness
import org.barlom.domain.metamodel.types.ECyclicity
import org.barlom.domain.metamodel.types.EMultiEdgedness
import org.barlom.domain.metamodel.types.ESelfLooping

/**
 * Implementation class for undirected edge types.
 */
class UndirectedEdgeType(

    id: String,
    name: String,
    parentPackage: NamedPackage,
    abstractness: EAbstractness,
    cyclicity: ECyclicity,
    multiEdgedness: EMultiEdgedness,
    selfLooping: ESelfLooping,

    /** The maximum in-degree for the head vertex of edges of this type. */
    val maxDegree: Int?,

    /** The minimum in-degree for the head vertex of edges of this type. */
    val minDegree: Int?,

    /** The super type of this type. */
    val superType: UndirectedEdgeType,

    /** The vertex type for edges of this type. */
    val vertexType: VertexType

) : NamedEdgeType(id, name, parentPackage, abstractness, cyclicity, multiEdgedness, selfLooping) {

    fun isSubTypeOf(edgeType: UndirectedEdgeType): Boolean {
        return this === edgeType || this.superType.isSubTypeOf(edgeType)
    }

}
