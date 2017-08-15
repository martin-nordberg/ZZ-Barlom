//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IVertexType

/**
 * Internal interface to a vertex type implementation (keeper of subtypes).
 */
internal interface IVertexTypeImpl : IVertexType {

    /**
     * Adds the given vertex type as a sub type of this one.
     */
    fun addSubType(vertexType: VertexType)

}