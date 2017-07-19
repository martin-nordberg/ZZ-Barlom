//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.types.*

/**
 * Implementation class for undirected edge types.
 */
internal data class UndirectedEdgeType(

    override val id: Uuid,
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

) : IUndirectedEdgeTypeImpl, INonRootEdgeTypeImpl {

    /** The attribute types within this edge type. */
    private val _attributeTypes: MutableList<EdgeAttributeType> = mutableListOf()

    /** The subtypes of this undirected edge type. */
    private val _subTypes: MutableList<UndirectedEdgeType> = mutableListOf()


    init {
        superType.addSubType(this)
    }


    override val attributeTypes: List<EdgeAttributeType>
        get() = _attributeTypes

    override val subTypes: List<UndirectedEdgeType>
        get() = _subTypes


    override fun addAttributeType(attributeType: EdgeAttributeType) {
        _attributeTypes.add(attributeType)
    }

    override fun addSubType(edgeType: UndirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean {
        return this === edgeType || this.superType.isSubTypeOf(edgeType)
    }

}
