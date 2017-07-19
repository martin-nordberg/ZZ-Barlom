//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

/**
 * Implementation class for directed edge types.
 */
interface IDirectedEdgeType : IEdgeType {

    /** The name of the role for the vertex at the head of edges of this type. */
    val headRoleName: String?

    /** The vertex type at the head of edges of this type. */
    val headVertexType: IVertexType

    /** The maximum in-degree for the head vertex of edges of this type. */
    val maxHeadInDegree: Int?

    /** The maximum out-degree for the tail vertex of edges of this type. */
    val maxTailOutDegree: Int?

    /** The minimum in-degree for the head vertex of edges of this type. */
    val minHeadInDegree: Int?

    /** The minimum out-degree for the tail vertex of edges of this type. */
    val minTailOutDegree: Int?

    /** The subtypes of this directed edge type. */
    val subTypes: List<IDirectedEdgeType>

    /** The super type of this edge type. */
    val superType: IDirectedEdgeType

    /** The name of the role for the vertex at the tail of edges of this type. */
    val tailRoleName: String?

    /** The vertex type at the tail of edges of this type. */
    val tailVertexType: IVertexType


    /** Whether this edge type is the same as or inherits from the given [edgeType]. */
    fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean

}