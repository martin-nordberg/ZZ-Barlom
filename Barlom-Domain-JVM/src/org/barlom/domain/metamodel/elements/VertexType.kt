//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeDecl
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness


/**
 * Implementation class for non-root vertex types.
 */
class VertexType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val abstractness: EAbstractness,
    override val superType: VertexType

) : IVertexType {

    override val attributes: List<IVertexAttributeDecl>
        get() = _attributes

    internal fun addAttribute(attribute: VertexAttributeDecl) {

        require(
            attribute.parentVertexType === this) { "Vertex attribute type may not be added to a vertex type not its parent." }

        _attributes.add(attribute)

    }

    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return superType === vertexType || superType.isSubTypeOf(vertexType)
    }

    /** The attributes of this vertex type. */
    private val _attributes: MutableList<VertexAttributeDecl> = mutableListOf()

}
