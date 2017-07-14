//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EAttributeOptionality
import org.barlom.domain.metamodel.types.ELabelDefaulting

/**
 * Implementation class for vertex attribute declarations.
 */
class VertexAttributeDecl(

    id: String,
    name: String,

    val parentVertexType: NamedVertexType,

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    val labelDefaulting: ELabelDefaulting,

    /** Whether this attribute is required for instances of the parent vertex type. */
    val optionality: EAttributeOptionality,

    /** The type of this attribute declaration. */
    val type: AttributeType

) : NamedElement(id, name) {

    init {
        parentVertexType.addAttribute(this)
    }

}
