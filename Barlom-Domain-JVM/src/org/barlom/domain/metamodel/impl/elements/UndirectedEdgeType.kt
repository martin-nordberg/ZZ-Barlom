//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Implementation class for undirected edge types.
 */
internal data class UndirectedEdgeType(

    override val id: String,
    override val name: String,
    override val parentPackage: Package,
    override val abstractness: EAbstractness,
    override val cyclicity: ECyclicity,
    override val multiEdgedness: EMultiEdgedness,
    override val selfLooping: ESelfLooping,
    override val maxDegree: Int?,
    override val minDegree: Int?,
    override val superType: UndirectedEdgeType,
    override val vertexType: VertexType

) : IUndirectedEdgeType, IEdgeTypeImpl {

    /** The attribute declarations within this edge type. */
    private val _attributes : MutableList<EdgeAttributeDecl> = mutableListOf()


    override val attributes : List<EdgeAttributeDecl>
        get() = _attributes


    override fun addAttribute(attribute: EdgeAttributeDecl) {
        _attributes.add( attribute );
    }

    override fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean {
        return this === edgeType || this.superType.isSubTypeOf(edgeType)
    }

}
