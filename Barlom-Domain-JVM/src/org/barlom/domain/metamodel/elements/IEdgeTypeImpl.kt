//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.IEdgeType

/**
 * Internal interface to an edge type implementation (container of edge attributes).
 */
interface IEdgeTypeImpl : IEdgeType {

    fun addAttribute( attribute: EdgeAttributeDecl)

}