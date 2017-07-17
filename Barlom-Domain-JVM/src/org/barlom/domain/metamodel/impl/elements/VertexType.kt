//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness


/**
 * Implementation class for non-root vertex types.
 */
internal data class VertexType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val abstractness: EAbstractness,
    override val superType: VertexType

) : IVertexType {

    /** The attribute types of this vertex type. */
    private val _attributeTypes: MutableList<VertexAttributeType> = mutableListOf()


    override val attributeTypes: List<IVertexAttributeType>
        get() = _attributeTypes


    fun addAttributeType(attributeType: VertexAttributeType) {

        require(attributeType.parentVertexType === this) {
            "Vertex attribute type may not be added to a vertex type not its parent."
        }

        _attributeTypes.add(attributeType)

    }

    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return superType === vertexType || superType.isSubTypeOf(vertexType)
    }

}
