//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.Uuid


/**
 * Implementation class for non-root vertex types.
 */
internal data class VertexType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl,
    override val abstractness: EAbstractness,
    override val superType: IVertexTypeImpl

) : IVertexTypeImpl {

    /** The attribute types of this vertex type. */
    private val _attributeTypes: MutableList<VertexAttributeType> = mutableListOf()

    /** The subtypes of this vertex type. */
    private val _subTypes: MutableList<VertexType> = mutableListOf()


    init {
        superType.addSubType(this)
    }


    override val attributeTypes: List<IVertexAttributeType>
        get() = _attributeTypes

    override val path: String
        get() = parentPackage.path + "." + name

    override val subTypes: List<VertexType>
        get() = _subTypes.sortedBy { vt -> vt.path }

    override val transitiveSubTypes: List<VertexType>
        get() {

            val result : MutableList<VertexType> = mutableListOf()

            for ( subType in subTypes ) {
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
