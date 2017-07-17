//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Implementation of the top-level root directed edge type.
 */
internal data class RootUndirectedEdgeType(

    override val id: String,
    override val parentPackage: RootPackage,

    private val _rootVertexType: RootVertexType

) : IUndirectedEdgeType {

    override val abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT

    override val attributeTypes: List<EdgeAttributeType>
        get() = listOf()

    override val cyclicity: ECyclicity
        get() = ECyclicity.UNCONSTRAINED

    override val maxDegree: Int?
        get() = null

    override val minDegree: Int?
        get() = null

    override val multiEdgedness: EMultiEdgedness
        get() = EMultiEdgedness.UNCONSTRAINED

    override val name: String
        get() = "undirectedEdge"

    override val selfLooping: ESelfLooping
        get() = ESelfLooping.UNCONSTRAINED

    override val superType: IUndirectedEdgeType
        get() = this

    override val vertexType: IVertexType
        get() = _rootVertexType


    override fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean {
        return edgeType === this
    }

}
