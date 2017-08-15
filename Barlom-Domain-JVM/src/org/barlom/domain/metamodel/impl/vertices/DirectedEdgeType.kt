//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IDirectedEdgeType
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
    forwardName: String?,
    headRoleName: String?,
    headVertexType: VertexType,
    maxHeadInDegree: Int?,
    maxTailOutDegree: Int?,
    minHeadInDegree: Int?,
    minTailOutDegree: Int?,
    reverseName: String?,
    superType: IDirectedEdgeTypeImpl,
    tailRoleName: String?,
    tailVertexType: VertexType

) : IDirectedEdgeTypeImpl, INonRootEdgeTypeImpl {

    private val _abstractness = V(abstractness)
    private val _attributeTypes = VLinkedList<EdgeAttributeType>()
    private val _cyclicity = V(cyclicity)
    private val _forwardName = V(forwardName)
    private val _headRoleName = V(headRoleName)
    private val _headVertexType = V(headVertexType)
    private val _maxHeadInDegree = V(maxHeadInDegree)
    private val _maxTailOutDegree = V(maxTailOutDegree)
    private val _minHeadInDegree = V(minHeadInDegree)
    private val _minTailOutDegree = V(minTailOutDegree)
    private val _multiEdgedness = V(multiEdgedness)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)
    private val _reverseName = V(reverseName)
    private val _selfLooping = V(selfLooping)
    private val _subTypes = VLinkedList<DirectedEdgeType>()
    private val _superType = V(superType)
    private val _tailRoleName = V(tailRoleName)
    private val _tailVertexType = V(tailVertexType)


    init {
        superType.addSubType(this)
    }


    override var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) = _abstractness.set(value)

    override val attributeTypes: List<EdgeAttributeType>
        get() = _attributeTypes.sortedBy { at -> at.name }

    override var cyclicity: ECyclicity
        get() = _cyclicity.get()
        set(value) = _cyclicity.set(value)

    override var forwardName: String?
        get() = _forwardName.get()
        set(value) = _forwardName.set(value)

    override var headRoleName: String?
        get() = _headRoleName.get()
        set(value) = _headRoleName.set(value)

    override val headVertexType: VertexType
        get() = _headVertexType.get()

    override var maxHeadInDegree: Int?
        get() = _maxHeadInDegree.get()
        set(value) = _maxHeadInDegree.set(value)

    override var maxTailOutDegree: Int?
        get() = _maxTailOutDegree.get()
        set(value) = _maxTailOutDegree.set(value)

    override var minHeadInDegree: Int?
        get() = _minHeadInDegree.get()
        set(value) = _minHeadInDegree.set(value)

    override var minTailOutDegree: Int?
        get() = _minTailOutDegree.get()
        set(value) = _minTailOutDegree.set(value)

    override var multiEdgedness: EMultiEdgedness
        get() = _multiEdgedness.get()
        set(value) = _multiEdgedness.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackage: Package
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

    override var reverseName: String?
        get() = _reverseName.get()
        set(value) = _reverseName.set(value)

    override var selfLooping: ESelfLooping
        get() = _selfLooping.get()
        set(value) = _selfLooping.set(value)

    override val subTypes: List<DirectedEdgeType>
        get() = _subTypes.sortedBy { et -> et.path }

    override val superType: IDirectedEdgeTypeImpl
        get() = _superType.get()

    override var tailRoleName: String?
        get() = _tailRoleName.get()
        set(value) = _tailRoleName.set(value)

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
