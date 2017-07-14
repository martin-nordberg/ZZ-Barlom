//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.types.EAbstractness
import org.barlom.domain.metamodel.types.ECyclicity
import org.barlom.domain.metamodel.types.EMultiEdgedness
import org.barlom.domain.metamodel.types.ESelfLooping

/**
 * Implementation of the top-level root directed edge type.
 */
class RootUndirectedEdgeType(

    id: String,
    abstractness : EAbstractness,
    cyclicity : ECyclicity,
    multiEdgedness : EMultiEdgedness,
    selfLooping : ESelfLooping,

    override val parentPackage : RootPackage

) :EdgeType(id,"DirectedEdge",abstractness,cyclicity,multiEdgedness,selfLooping) {

    override val attributes: List<EdgeAttributeDecl>
        get() = listOf()

}
