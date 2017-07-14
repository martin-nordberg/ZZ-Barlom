//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.types.EAbstractness
import org.barlom.domain.metamodel.types.ECyclicity
import org.barlom.domain.metamodel.types.EMultiEdgedness
import org.barlom.domain.metamodel.types.ESelfLooping


/**
 * Implementation class for edge types.
 */
abstract class EdgeType(

    id: String,
    name: String,

    /** Whether this edge type is abstract. */
    val abstractness : EAbstractness,

    /** Whether this edge type is acyclic. */
    val cyclicity : ECyclicity,

    /** Whether this edge type allows multiple edges between two given vertexes. */
    val multiEdgedness : EMultiEdgedness,

    /** Whether this edge type allows an edge from a vertex to itself. */
    val selfLooping : ESelfLooping

) : PackagedElement( id, name ) {

    abstract val attributes : List<EdgeAttributeDecl>

}
