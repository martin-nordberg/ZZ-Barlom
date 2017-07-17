//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IDirectedEdgeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping


/**
 * Implementation of the top-level root directed edge type.
 */
internal data class RootDirectedEdgeType(

    override val id: String,
    override val parentPackage: RootPackage,

    private val _rootVertexType: RootVertexType

) : IDirectedEdgeType {

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

    override val superType: IDirectedEdgeType
        get() = this

    override val tailRoleName: String?
        get() = null

    override val tailVertexType: IVertexType
        get() = _rootVertexType


    override fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean {
        return edgeType === this
    }

}
