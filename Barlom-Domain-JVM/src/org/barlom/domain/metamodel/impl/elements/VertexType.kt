//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid


/**
 * Implementation class for non-root vertex types.
 */
internal class VertexType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl,
    abstractness: EAbstractness,
    superType: IVertexTypeImpl

) : IVertexTypeImpl {

    private val _abstractness = V(abstractness)
    private val _attributeTypes = VLinkedList<VertexAttributeType>()
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)
    private val _subTypes = VLinkedList<VertexType>()
    private val _superType = V(superType)


    init {
        superType.addSubType(this)
    }


    override var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) = _abstractness.set(value)

    override val attributeTypes: List<IVertexAttributeType>
        get() = _attributeTypes.asSortedList { at -> at.name }

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

    override val subTypes: List<VertexType>
        get() = _subTypes.asSortedList { vt -> vt.path }

    override val superType: IVertexTypeImpl
        get() = _superType.get()

    override val transitiveSubTypes: List<VertexType>
        get() {

            val result: MutableList<VertexType> = mutableListOf()

            for (subType in subTypes) {
                result.add(subType)
                result.addAll(subType.transitiveSubTypes)
            }

            return result.sortedBy { vt -> vt.path }

        }


    /**
     * Adds the given attribute type to this vertex type.
     */
    fun addAttributeType(attributeType: VertexAttributeType) {

        require(attributeType.parentVertexType === this) {
            "Vertex attribute type may not be added to a vertex type not its parent."
        }

        _attributeTypes.add(attributeType)

    }

    override fun addSubType(vertexType: VertexType) {
        _subTypes.add(vertexType)
    }

    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return superType === vertexType || superType.isSubTypeOf(vertexType)
    }

}
