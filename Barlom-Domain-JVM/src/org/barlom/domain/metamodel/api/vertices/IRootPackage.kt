//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

/**
 * Additional interface for a Barlom root package.
 */
interface IRootPackage : IPackage {

    /** The root directed edge type. */
    val rootDirectedEdgeType: IDirectedEdgeType

    /** The root undirected edge type. */
    val rootUndirectedEdgeType: IUndirectedEdgeType

    /** The root vertex type. */
    val rootVertexType: IVertexType

}
