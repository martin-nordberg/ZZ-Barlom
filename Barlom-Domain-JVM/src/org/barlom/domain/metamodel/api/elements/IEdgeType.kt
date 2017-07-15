//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Interface of edge types (either directed or undirected). Edge types can be abstract or concrete, have attributes,
 * and constrain general edge properties like cyclicity, multiple edges between two vertexes, or self looping.
 */
interface IEdgeType : IPackagedElement {

    /** Whether this edge type is abstract. */
    val abstractness: EAbstractness

    /** The attributes of this edge type. */
    val attributes: List<IEdgeAttributeDecl>

    /** Whether this edge type is acyclic. */
    val cyclicity: ECyclicity

    /** Whether this edge type allows multiple edges between two given vertexes. */
    val multiEdgedness: EMultiEdgedness

    /** Whether this edge type allows an edge from a vertex to itself. */
    val selfLooping: ESelfLooping

}
