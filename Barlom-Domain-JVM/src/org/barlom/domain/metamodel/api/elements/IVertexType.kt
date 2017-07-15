//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EAbstractness


/**
 * Implementation class for vertex types.
 */
interface IVertexType : IPackagedElement {

    /** Whether this vertex type is abstract. */
    val abstractness: EAbstractness

    val attributes: List<IVertexAttributeDecl>

    /** The super type of this vertex type. */
    val superType: IVertexType


    fun isSubTypeOf(vertexType: IVertexType): Boolean

}
