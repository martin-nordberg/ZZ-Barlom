//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for undirected edge types.
 */
internal class UndirectedEdgeType(

    override val id: Uuid,
    name: String,
    parentPackage: Package,
    abstractness: EAbstractness,
    cyclicity: ECyclicity,
    multiEdgedness: EMultiEdgedness,
    selfLooping: ESelfLooping,
    maxDegree: Int?,
    minDegree: Int?,
    superType: IUndirectedEdgeTypeImpl,
    vertexType: VertexType

) : IUndirectedEdgeTypeImpl, INonRootEdgeTypeImpl {

    private val _abstractness = V(abstractness)
    private val _attributeTypes = VLinkedList<EdgeAttributeType>()
    private val _cyclicity = V(cyclicity)
    private val _maxDegree = V(maxDegree)
    private val _minDegree = V(minDegree)
    private val _multiEdgedness = V(multiEdgedness)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)
    private val _selfLooping = V(selfLooping)
    private val _subTypes = VLinkedList<UndirectedEdgeType>()
    private val _superType = V(superType)
    private val _vertexType = V(vertexType)


    init {
        superType.addSubType(this)
    }


    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<Package>
        get() = listOf(_parentPackage.get())

    override var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) = _abstractness.set(value)

    override var cyclicity: ECyclicity
        get() = _cyclicity.get()
        set(value) = _cyclicity.set(value)

    override var multiEdgedness: EMultiEdgedness
        get() = _multiEdgedness.get()
        set(value) = _multiEdgedness.set(value)

    override var selfLooping: ESelfLooping
        get() = _selfLooping.get()
        set(value) = _selfLooping.set(value)

    override var maxDegree: Int?
        get() = _maxDegree.get()
        set(value) = _maxDegree.set(value)

    override var minDegree: Int?
        get() = _minDegree.get()
        set(value) = _minDegree.set(value)

    override val superType: IUndirectedEdgeTypeImpl
        get() = _superType.get()

    override val vertexType: VertexType
        get() = _vertexType.get()

    override val attributeTypes: List<EdgeAttributeType>
        get() = _attributeTypes.sortedBy { at -> at.name }

    override val path: String
        get() = parentPackages[0].path + "." + name

    override val subTypes: List<UndirectedEdgeType>
        get() = _subTypes.sortedBy { et -> et.path }

    override val transitiveSubTypes: List<UndirectedEdgeType>
        get() {

            val result: MutableList<UndirectedEdgeType> = mutableListOf()

            for (subType in subTypes) {
                result.add(subType)
                result.addAll(subType.transitiveSubTypes)
            }

            return result.sortedBy { et -> et.path }

        }


    override fun addAttributeType(attributeType: EdgeAttributeType) {
        _attributeTypes.add(attributeType)
    }

    override fun addSubType(edgeType: UndirectedEdgeType) {
        _subTypes.add(edgeType)
    }

    override fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean {
        return superType === edgeType || superType.isSubTypeOf(edgeType)
    }

}
