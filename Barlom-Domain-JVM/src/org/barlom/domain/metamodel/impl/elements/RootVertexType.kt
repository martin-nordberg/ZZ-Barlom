//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.infrastructure.uuids.Uuid

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

    override val path: String
        get() = name

    override val subTypes: List<VertexType>
        get() = _subTypes.sortedBy { vt -> vt.path }

    override val superType: IVertexType
        get() = this

    override val transitiveSubTypes: List<IVertexType>
        get() {

            val result: MutableList<VertexType> = mutableListOf()

            for (subType in subTypes) {
                result.add(subType)
                result.addAll(subType.transitiveSubTypes)
            }

            return result.sortedBy { vt -> vt.path }

        }

    override fun addSubType(vertexType: VertexType) {
        _subTypes.add(vertexType)
    }

    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return false
    }

}
