//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.impl.edges.DirectedEdgeTypeContainment

/**
 * Internal interface to an unidrected edge type implementation (container of edge attributes).
 */
internal interface INonRootDirectedEdgeTypeImpl : INonRootEdgeTypeImpl {

    /** Adds a containment edge to this undirected edge. */
    fun addDirectedEdgeTypeContainment(edgeTypeContainment: DirectedEdgeTypeContainment)

}