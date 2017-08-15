//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.types.EAbstractness


/**
 * Implementation class for vertex types.
 */
interface IVertexType : IPackagedElement {

    /** Whether this vertex type is abstract. */
    var abstractness: EAbstractness

    /** The types of attributes of vertexes of this type. */
    val attributeTypes: List<IVertexAttributeType>

    /** The direct subtypes of this vertex type sorted by path. */
    val subTypes: List<IVertexType>

    /** The super type of this vertex type. */
    val superType: IVertexType

    /** The direct and indirect subtypes of this vertex type sorted by path. */
    val transitiveSubTypes: List<IVertexType>


    /** Whether this vertex type is a direct or indirect subtype of the given [vertexType]. */
    fun isSubTypeOf(vertexType: IVertexType): Boolean

}
