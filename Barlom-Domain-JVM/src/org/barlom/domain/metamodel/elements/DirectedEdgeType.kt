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
 * Implementation class for directed edge types.
 */
class DirectedEdgeType(

    id: String,
    name: String,
    parentPackage: NamedPackage,
    abstractness: EAbstractness,
    cyclicity: ECyclicity,
    multiEdgedness: EMultiEdgedness,
    selfLooping: ESelfLooping,

    /** The name of the role for the vertex at the head of edges of this type. */
    val headRoleName: String,

    /** The vertex type at the head of edges of this type. */
    val headVertexType: VertexType,

    /** The maximum in-degree for the head vertex of edges of this type. */
    val maxHeadInDegree: Int?,

    /** The maximum out-degree for the tail vertex of edges of this type. */
    val maxTailOutDegree: Int?,

    /** The minimum in-degree for the head vertex of edges of this type. */
    val minHeadInDegree: Int?,

    /** The minimum out-degree for the tail vertex of edges of this type. */
    val minTailOutDegree: Int?,

    /** The super type of this edge type. */
    val superType: DirectedEdgeType,

    /** The name of the role for the vertex at the tail of edges of this type. */
    val tailRoleName: String?,

    /** The vertex type at the tail of edges of this type. */
    val tailVertexType: VertexType

) : NamedEdgeType(id, name, parentPackage, abstractness, cyclicity, multiEdgedness, selfLooping) {

    fun isSubTypeOf(edgeType: DirectedEdgeType): Boolean {
        return this === edgeType || this.superType.isSubTypeOf(edgeType);
    }

}
