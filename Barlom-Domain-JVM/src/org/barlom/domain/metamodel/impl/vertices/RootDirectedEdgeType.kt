//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IDirectedEdgeType
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
internal data class RootDirectedEdgeType(

    override val id: Uuid,
    val parentPackage: RootPackage,

    /** The root edge type forming the head and tail types of this root directed edge type. */
    private val _rootVertexType: RootVertexType

) : IDirectedEdgeTypeImpl {

    /** The subtypes of this directed edge type. */
    private val _subTypes = VLinkedList<DirectedEdgeType>()


    override var abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override val attributeTypes: List<EdgeAttributeType>
        get() = listOf()

    override var cyclicity: ECyclicity
        get() = ECyclicity.UNCONSTRAINED
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var forwardName: String?
        get() = "directedEdge"
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var headRoleName: String?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override val headVertexType: IVertexType
        get() = _rootVertexType

    override var maxHeadInDegree: Int?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var maxTailOutDegree: Int?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var minHeadInDegree: Int?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var minTailOutDegree: Int?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var multiEdgedness: EMultiEdgedness
        get() = EMultiEdgedness.UNCONSTRAINED
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var name: String
        get() = "directedEdge"
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf()

    override val path: String
        get() = name

    override var reverseName: String?
        get() = "directedEdge"
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override var selfLooping: ESelfLooping
        get() = ESelfLooping.UNCONSTRAINED
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override val subTypes: List<DirectedEdgeType>
        get() = _subTypes.sortedBy { et -> et.path }

    override val superType: IDirectedEdgeType
        get() = this

    override var tailRoleName: String?
        get() = null
        set(value) = throw UnsupportedOperationException("Cannot change RootDirectedEdgeType attributes.")

    override val tailVertexType: IVertexType
        get() = _rootVertexType

    override val transitiveSubTypes: List<DirectedEdgeType>
        get() {

            val result: MutableList<DirectedEdgeType> = mutableListOf()

            for (subType in subTypes) {
                result.add(subType)
                result.addAll(subType.transitiveSubTypes)
            }

            return result.sortedBy { et -> et.path }

        }


    override fun addSubType(edgeType: DirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean {
        return false
    }

}
