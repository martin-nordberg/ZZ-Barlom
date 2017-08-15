//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.impl.edges.VertexTypeContainment

/**
 * Internal interface to a vertex type implementation (keeper of subtypes).
 */
internal interface INonRootVertexTypeImpl : IVertexTypeImpl {

    /** Registers the given vertex type containment in this package. */
    fun addParentVertexTypeContainment(vertexTypeContainment: VertexTypeContainment)

    /** Establishes the parent container of this vertex type. */
    fun containedBy(pkg: INonRootPackageImpl) : Unit

}