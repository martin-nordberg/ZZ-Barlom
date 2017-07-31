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
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for directed edge types.
 */
internal class DirectedEdgeType(

    override val id: Uuid,
    name: String,
    parentPackage: Package,
    abstractness: EAbstractness,
    cyclicity: ECyclicity,
    multiEdgedness: EMultiEdgedness,
    selfLooping: ESelfLooping,
    headRoleName: String?,
    headVertexType: VertexType,
    maxHeadInDegree: Int?,
    maxTailOutDegree: Int?,
    minHeadInDegree: Int?,
    minTailOutDegree: Int?,
    superType: IDirectedEdgeTypeImpl,
    tailRoleName: String?,
    tailVertexType: VertexType

) : IDirectedEdgeTypeImpl, INonRootEdgeTypeImpl {

    private val _abstractness = V(abstractness)
    private val _attributeTypes = VLinkedList<EdgeAttributeType>()
    private val _cyclicity = V(cyclicity)
    private val _headRoleName = V(headRoleName)
    private val _headVertexType = V(headVertexType)
    private val _maxHeadInDegree = V(maxHeadInDegree)
    private val _maxTailOutDegree = V(maxTailOutDegree)
    private val _minHeadInDegree = V(minHeadInDegree)
    private val _minTailOutDegree = V(minTailOutDegree)
    private val _multiEdgedness = V(multiEdgedness)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)
    private val _selfLooping = V(selfLooping)
    private val _subTypes = VLinkedList<DirectedEdgeType>()
    private val _superType = V(superType)
    private val _tailRoleName = V(tailRoleName)
    private val _tailVertexType = V(tailVertexType)


    init {
        superType.addSubType(this)
    }


    override val abstractness: EAbstractness
        get() = _abstractness.get()

    override val attributeTypes: List<EdgeAttributeType>
        get() = _attributeTypes.asSortedList { at -> at.name }

    override val cyclicity: ECyclicity
        get() = _cyclicity.get()

    override val headRoleName: String?
        get() = _headRoleName.get()

    override val headVertexType: VertexType
        get() = _headVertexType.get()

    override val maxHeadInDegree: Int?
        get() = _maxHeadInDegree.get()

    override val maxTailOutDegree: Int?
        get() = _maxTailOutDegree.get()

    override val minHeadInDegree: Int?
        get() = _minHeadInDegree.get()

    override val minTailOutDegree: Int?
        get() = _minTailOutDegree.get()

    override val multiEdgedness: EMultiEdgedness
        get() = _multiEdgedness.get()

    override val name: String
        get() = _name.get()

    override val parentPackage: Package
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

    override val selfLooping: ESelfLooping
        get() = _selfLooping.get()

    override val subTypes: List<DirectedEdgeType>
        get() = _subTypes.asSortedList { et -> et.path }

    override val superType: IDirectedEdgeTypeImpl
        get() = _superType.get()

    override val tailRoleName: String?
        get() = _tailRoleName.get()

    override val tailVertexType: VertexType
        get() = _tailVertexType.get()

    override val transitiveSubTypes: List<DirectedEdgeType>
        get() {

            val result: MutableList<DirectedEdgeType> = mutableListOf()

            for (subType in subTypes) {
                result.add(subType)
                result.addAll(subType.transitiveSubTypes)
            }

            return result.sortedBy { et -> et.path }

        }


    override fun addAttributeType(attributeType: EdgeAttributeType) {
        _attributeTypes.add(attributeType)
    }

    override fun addSubType(edgeType: DirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IDirectedEdgeType): Boolean {
        return superType === edgeType || superType.isSubTypeOf(edgeType)
    }

}
