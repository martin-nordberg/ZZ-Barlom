//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IDirectedEdgeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.*

/**
 * Implementation of the top-level root directed edge type.
 */
internal data class RootDirectedEdgeType(

    override val id: Uuid,
    override val parentPackage: RootPackage,

    /** The root edge type forming the head and tail types of this root directed edge type. */
    private val _rootVertexType: RootVertexType

) : IDirectedEdgeTypeImpl {

    /** The subtypes of this directed edge type. */
    private val _subTypes: MutableList<DirectedEdgeType> = mutableListOf()


    override val abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT

    override val attributeTypes: List<EdgeAttributeType>
        get() = listOf()

    override val cyclicity: ECyclicity
        get() = ECyclicity.UNCONSTRAINED

    override val headRoleName: String?
        get() = null

    override val headVertexType: IVertexType
        get() = _rootVertexType

    override val maxHeadInDegree: Int?
        get() = null

    override val maxTailOutDegree: Int?
        get() = null

    override val minHeadInDegree: Int?
        get() = null

    override val minTailOutDegree: Int?
        get() = null

    override val multiEdgedness: EMultiEdgedness
        get() = EMultiEdgedness.UNCONSTRAINED

    override val name: String
        get() = "directedEdge"

    override val selfLooping: ESelfLooping
        get() = ESelfLooping.UNCONSTRAINED

    override val subTypes: List<DirectedEdgeType>
        get() = _subTypes

    override val superType: IDirectedEdgeType
        get() = this

    override val tailRoleName: String?
        get() = null

    override val tailVertexType: IVertexType
        get() = _rootVertexType


    override fun addSubType(edgeType: DirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean {
        return edgeType === this
    }

}
