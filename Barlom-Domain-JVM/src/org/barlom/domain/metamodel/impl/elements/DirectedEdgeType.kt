//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IDirectedEdgeType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Implementation class for directed edge types.
 */
internal data class DirectedEdgeType(

    override val id: String,
    override val name: String,
    override val parentPackage: Package,
    override val abstractness: EAbstractness,
    override val cyclicity: ECyclicity,
    override val multiEdgedness: EMultiEdgedness,
    override val selfLooping: ESelfLooping,
    override val headRoleName: String,
    override val headVertexType: VertexType,
    override val maxHeadInDegree: Int?,
    override val maxTailOutDegree: Int?,
    override val minHeadInDegree: Int?,
    override val minTailOutDegree: Int?,
    override val superType: DirectedEdgeType,
    override val tailRoleName: String?,
    override val tailVertexType: VertexType

) : IDirectedEdgeType, IEdgeTypeImpl {

    /** The attribute declarations within this edge type. */
    private val _attributes : MutableList<EdgeAttributeDecl> = mutableListOf()


    override val attributes : List<EdgeAttributeDecl>
        get() = _attributes


    override fun addAttribute(attribute: EdgeAttributeDecl) {
        _attributes.add( attribute );
    }

    override fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean {
        return this === edgeType || this.superType.isSubTypeOf(edgeType);
    }

}
