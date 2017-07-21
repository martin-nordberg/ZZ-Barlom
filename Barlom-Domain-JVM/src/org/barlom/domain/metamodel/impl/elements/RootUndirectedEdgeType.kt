//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.*

/**
 * Implementation of the top-level root directed edge type.
 */
internal data class RootUndirectedEdgeType(

    override val id: Uuid,
    override val parentPackage: RootPackage,

    /** The root edge type forming the end types of this root undirected edge type. */
    private val _rootVertexType: RootVertexType

) : IUndirectedEdgeTypeImpl {

    /** The subtypes of this undirected edge type. */
    private val _subTypes: MutableList<UndirectedEdgeType> = mutableListOf()


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

    override val path: String
        get() = name

    override val selfLooping: ESelfLooping
        get() = ESelfLooping.UNCONSTRAINED

    override val subTypes: List<UndirectedEdgeType>
        get() = _subTypes.sortedBy { et -> et.path }

    override val superType: IUndirectedEdgeType
        get() = this

    override val transitiveSubTypes: List<UndirectedEdgeType>
        get() {

            val result : MutableList<UndirectedEdgeType> = mutableListOf()

            for ( subType in subTypes ) {
                result.add(subType)
                result.addAll(subType.transitiveSubTypes)
            }

            return result.sortedBy { et -> et.path }

        }

    override val vertexType: IVertexType
        get() = _rootVertexType


    override fun addSubType(edgeType: UndirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean {
        return edgeType === this
    }

}
