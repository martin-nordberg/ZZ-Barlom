//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness


/**
 * Implementation of the top-level root vertex type.
 */
internal data class RootVertexType(

    override val id: String,
    override val parentPackage: RootPackage

) : IVertexType {

    override val abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT

    override val attributes: List<VertexAttributeDecl>
        get() = listOf()

    override val name: String
        get() = "VertexType"

    override val superType: IVertexType
        get() = this


    override fun isSubTypeOf(vertexType: IVertexType): Boolean {
        return vertexType === this
    }

}
