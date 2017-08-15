//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

/**
 * Implementation class for undirected edge types.
 */
interface IUndirectedEdgeType : IEdgeType {

    /** The maximum in-degree for the head vertex of edges of this type. */
    var maxDegree: Int?

    /** The minimum in-degree for the head vertex of edges of this type. */
    var minDegree: Int?

    /** The subtypes of this undirected edge type sorted by path. */
    val subTypes: List<IUndirectedEdgeType>

    /** The super type of this type. */
    val superType: IUndirectedEdgeType

    /** The direct and indirect subtypes of this undirected edge type sorted by path. */
    val transitiveSubTypes: List<IUndirectedEdgeType>

    /** The vertex type for edges of this type. */
    val vertexType: IVertexType


    /** Whether this edge type is a direct or indirect subtype of the given [edgeType]. */
    fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean

}
