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

    /** The types of attributes of vertexes of this type. */
    val attributeTypes: List<IVertexAttributeType>

    /** The subtypes of this vertex type. */
    val subTypes: List<IVertexType>

    /** The super type of this vertex type. */
    val superType: IVertexType


    /** Whether this vertex type is the same as or a subtype of the given [vertexType]. */
    fun isSubTypeOf(vertexType: IVertexType): Boolean

}
