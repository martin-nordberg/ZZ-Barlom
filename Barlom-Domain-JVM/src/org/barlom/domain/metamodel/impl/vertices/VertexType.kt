//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.vertices.IVertexAttributeType
import org.barlom.domain.metamodel.api.vertices.IVertexType
import org.barlom.domain.metamodel.impl.edges.VertexTypeContainment
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid


/**
 * Implementation class for non-root vertex types.
 */
internal class VertexType(

    override val id: Uuid,
    name: String,
    abstractness: EAbstractness,
    superType: IVertexTypeImpl,
    initialize: VertexType.()->Unit

) : INonRootVertexTypeImpl {

    private val _abstractness = V(abstractness)
    private val _attributeTypes = VLinkedList<VertexAttributeType>()
    private val _name = V(name)
    private val _vertexTypeContainments = VLinkedList<VertexTypeContainment>()
    private val _subTypes = VLinkedList<VertexType>()
    private val _superType = V(superType)


    init {
        superType.addSubType(this)
        initialize()
    }


    override var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) = _abstractness.set(value)

    override val attributeTypes: List<IVertexAttributeType>
        get() = _attributeTypes.sortedBy { at -> at.name }

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<IPackageImpl>
        get() = _vertexTypeContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val path: String
        get() {

            if (_vertexTypeContainments.isEmpty) {
                return name
            }

            val parentPath = parentPackages[0].path

            if ( parentPath.isEmpty() ) {
                return name
            }

            return parentPath + "." + name

        }

    override val subTypes: List<VertexType>
        get() = _subTypes.sortedBy { vt -> vt.path }

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

    override fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(vertexTypeContainment.child === this) {
            "Parent package containment can only be added to its child."
        }

        _vertexTypeContainments.add(vertexTypeContainment)

    }

    override fun addSubType(vertexType: VertexType) {
        _subTypes.add(vertexType)
    }

    override fun containedBy(pkg: INonRootPackageImpl) {
        VertexTypeContainment(makeUuid(), pkg, this)
    }

    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return superType === vertexType || superType.isSubTypeOf(vertexType)
    }

}
