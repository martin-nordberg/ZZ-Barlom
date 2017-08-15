//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.vertices.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

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
    private val _subTypes = VLinkedList<UndirectedEdgeType>()


    override var abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override val attributeTypes: List<EdgeAttributeType>
        get() = listOf()

    override var cyclicity: ECyclicity
        get() = ECyclicity.UNCONSTRAINED
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override var maxDegree: Int?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override var minDegree: Int?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override var multiEdgedness: EMultiEdgedness
        get() = EMultiEdgedness.UNCONSTRAINED
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override var name: String
        get() = "undirectedEdge"
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override val path: String
        get() = name

    override var selfLooping: ESelfLooping
        get() = ESelfLooping.UNCONSTRAINED
        set(value) = throw UnsupportedOperationException("Cannot change RootUndirectedEdgeType attributes.")

    override val subTypes: List<UndirectedEdgeType>
        get() = _subTypes.sortedBy { et -> et.path }

    override val superType: IUndirectedEdgeType
        get() = this

    override val transitiveSubTypes: List<UndirectedEdgeType>
        get() {

            val result: MutableList<UndirectedEdgeType> = mutableListOf()

            for (subType in subTypes) {
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
        return false
    }

}
