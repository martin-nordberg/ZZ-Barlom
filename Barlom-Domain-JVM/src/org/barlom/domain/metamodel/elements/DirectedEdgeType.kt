//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.IDirectedEdgeType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Implementation class for directed edge types.
 */
class DirectedEdgeType(

    override val id: String,
    override val name: String,
    override val parentPackage: Package,
    override val abstractness: EAbstractness,
    override val cyclicity: ECyclicity,
    override val multiEdgedness: EMultiEdgedness,
    override val selfLooping: ESelfLooping,

    /** The name of the role for the vertex at the head of edges of this type. */
    override val headRoleName: String,

    /** The vertex type at the head of edges of this type. */
    override val headVertexType: VertexType,

    /** The maximum in-degree for the head vertex of edges of this type. */
    override val maxHeadInDegree: Int?,

    /** The maximum out-degree for the tail vertex of edges of this type. */
    override val maxTailOutDegree: Int?,

    /** The minimum in-degree for the head vertex of edges of this type. */
    override val minHeadInDegree: Int?,

    /** The minimum out-degree for the tail vertex of edges of this type. */
    override val minTailOutDegree: Int?,

    /** The super type of this edge type. */
    override val superType: DirectedEdgeType,

    /** The name of the role for the vertex at the tail of edges of this type. */
    override val tailRoleName: String?,

    /** The vertex type at the tail of edges of this type. */
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
