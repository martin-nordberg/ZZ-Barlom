//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IDirectedEdgeType
import org.barlom.domain.metamodel.api.types.*

/**
 * Implementation class for directed edge types.
 */
internal data class DirectedEdgeType(

    override val id: Uuid,
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

) : IDirectedEdgeTypeImpl, INonRootEdgeTypeImpl {

    /** The attribute types within this edge type. */
    private val _attributeTypes: MutableList<EdgeAttributeType> = mutableListOf()

    /** The subtypes of this directed edge type. */
    private val _subTypes: MutableList<DirectedEdgeType> = mutableListOf()


    init {
        superType.addSubType(this)
    }


    override val attributeTypes: List<EdgeAttributeType>
        get() = _attributeTypes

    override val subTypes: List<DirectedEdgeType>
        get() = _subTypes


    override fun addAttributeType(attributeType: EdgeAttributeType) {
        _attributeTypes.add(attributeType);
    }

    override fun addSubType(edgeType: DirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean {
        return this === edgeType || this.superType.isSubTypeOf(edgeType);
    }

}
