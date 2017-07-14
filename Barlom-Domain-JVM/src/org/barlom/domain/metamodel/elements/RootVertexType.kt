//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.types.EAbstractness


/**
 * Implementation of the top-level root vertex type.
 */
class RootVertexType(

    id: String,
    override val parentPackage: RootPackage,
    abstractness: EAbstractness,
    superType: VertexType

) : VertexType(id, "VertexType",abstractness,superType) {

    override val attributes: List<VertexAttributeDecl>
        get() = listOf()

}
