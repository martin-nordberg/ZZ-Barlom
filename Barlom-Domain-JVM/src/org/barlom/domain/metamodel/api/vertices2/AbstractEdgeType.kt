package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Interface of edge types (either directed or undirected). Edge types can be abstract or concrete, have attributes,
 * and constrain general edge properties like cyclicity, multiple edges between two vertexes, or self looping.
 */
abstract class AbstractEdgeType : AbstractPackagedElement() {

    /** Whether this edge type is abstract. */
    abstract var abstractness: EAbstractness

//    /** The attribute types of this edge type sorted by name. */
//    abstract val attributeTypes: List<IEdgeAttributeType>

    /** Whether this edge type is acyclic. */
    abstract var cyclicity: ECyclicity

    /** Whether this is a root edge type (directed or undirected). */
    abstract val isRoot: Boolean

    /** Whether this edge type allows multiple edges between two given vertexes. */
    abstract var multiEdgedness: EMultiEdgedness

    /** Whether this edge type allows an edge from a vertex to itself. */
    abstract var selfLooping: ESelfLooping

}