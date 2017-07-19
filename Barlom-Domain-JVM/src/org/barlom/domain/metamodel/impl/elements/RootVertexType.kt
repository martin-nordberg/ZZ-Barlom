//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.Uuid


/**
 * Implementation of the top-level root vertex type.
 */
internal data class RootVertexType(

    override val id: Uuid,
    override val parentPackage: RootPackage

) : IVertexTypeImpl {

    /** The subtypes of this vertex type. */
    private val _subTypes: MutableList<VertexType> = mutableListOf()


    override val abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT

    override val attributeTypes: List<VertexAttributeType>
        get() = listOf()

    override val name: String
        get() = "Vertex"

    override val subTypes: List<VertexType>
        get() = _subTypes

    override val superType: IVertexType
        get() = this


    override fun addSubType(vertexType: VertexType) {
        _subTypes.add(vertexType)
    }

    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return vertexType === this
    }

}
