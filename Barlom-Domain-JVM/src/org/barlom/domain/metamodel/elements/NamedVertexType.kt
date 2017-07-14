//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EAbstractness


/**
 * Implementation class for non-root vertex types.
 */
class NamedVertexType(

    id: String,
    name: String,
    override val parentPackage: NamedPackage,
    abstractness: EAbstractness,
    superType: VertexType

) : VertexType(id, name, abstractness, superType) {

    internal fun addAttribute(attribute: VertexAttributeDecl) {

        require(
            attribute.parentVertexType === this) { "Vertex attribute type may not be added to a vertex type not its parent." }

        _attributes.add(attribute)

    }

    override val attributes: List<VertexAttributeDecl>
        get() = _attributes

    /** The attributes of this vertex type. */
    private val _attributes: MutableList<VertexAttributeDecl> = mutableListOf()

}
