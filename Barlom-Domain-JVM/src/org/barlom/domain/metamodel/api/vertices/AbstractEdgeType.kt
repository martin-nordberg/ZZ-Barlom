//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.EdgeAttributeTypeContainment
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Interface of edge types (either directed or undirected). Edge types can be abstract or concrete, have attributes,
 * and constrain general edge properties like cyclicity, multiple edges between two vertexes, or self looping.
 */
abstract class AbstractEdgeType internal constructor() : AbstractPackagedElement() {

    /** Whether this edge type is abstract. */
    abstract var abstractness: EAbstractness

    /** The attribute types of this edge type sorted by name. */
    abstract val attributeTypes: List<EdgeAttributeType>

    /** Whether this edge type is acyclic. */
    abstract var cyclicity: ECyclicity

    /** Whether this is a root edge type (directed or undirected). */
    abstract val isRoot: Boolean

    /** Whether this edge type allows multiple edges between two given vertexes. */
    abstract var multiEdgedness: EMultiEdgedness

    /** Whether this edge type allows an edge from a vertex to itself. */
    abstract var selfLooping: ESelfLooping


    /** Adds an edge attribute type to this, its parent edge type's, list of edge attribute type containments. */
    abstract internal fun addEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment)

}