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
    var headRoleName: String?

    /** The vertex type at the head of edges of this type. */
    val headVertexType: IVertexType

    /** The maximum in-degree for the head vertex of edges of this type. */
    var maxHeadInDegree: Int?

    /** The maximum out-degree for the tail vertex of edges of this type. */
    var maxTailOutDegree: Int?

    /** The minimum in-degree for the head vertex of edges of this type. */
    var minHeadInDegree: Int?

    /** The minimum out-degree for the tail vertex of edges of this type. */
    var minTailOutDegree: Int?

    /** The name of this edge type when considered from head to tail (vs. the primary name from tail to head). */
    var reverseName: String?

    /** The direct subtypes of this directed edge type sorted by path. */
    val subTypes: List<IDirectedEdgeType>

    /** The super type of this edge type. */
    val superType: IDirectedEdgeType

    /** The name of the role for the vertex at the tail of edges of this type. */
    var tailRoleName: String?

    /** The vertex type at the tail of edges of this type. */
    val tailVertexType: IVertexType

    /** The direct and indirect subtypes of this directed edge type sorted by path. */
    val transitiveSubTypes: List<IDirectedEdgeType>


    /** Whether this edge type is the same as or inherits from the given [edgeType]. */
    fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean

}
