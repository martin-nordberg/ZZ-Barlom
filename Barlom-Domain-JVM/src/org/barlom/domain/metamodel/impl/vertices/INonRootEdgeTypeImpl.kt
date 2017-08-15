//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IEdgeType

/**
 * Internal interface to an edge type implementation (container of edge attributes).
 */
internal interface INonRootEdgeTypeImpl : IEdgeType {

    /** Adds an attribute to this, its parent edge type's, list of attributes. */
    fun addAttributeType(attributeType: EdgeAttributeType)

}