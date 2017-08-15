//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IDirectedEdgeType

/**
 * Internal interface to a directed edge type implementation (container of subtypes).
 */
internal interface IDirectedEdgeTypeImpl : IDirectedEdgeType {

    /** Adds the given edge type as a sub type of this one. */
    fun addSubType(edgeType: DirectedEdgeType)

}