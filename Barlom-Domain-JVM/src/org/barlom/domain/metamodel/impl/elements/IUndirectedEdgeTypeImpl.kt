//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType

/**
 * Internal interface to an undirected edge type implementation (container of subtypes).
 */
internal interface IUndirectedEdgeTypeImpl : IUndirectedEdgeType {

    /** Adds the given edge type as a sub type of this one. */
    fun addSubType(edgeType: UndirectedEdgeType)

}