//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeDecl
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting

/**
 * Implementation class for vertex attribute declarations.
 */
class VertexAttributeDecl(

    override val id: String,

    override val name: String,

    override val parentVertexType: VertexType,

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    override val labelDefaulting: ELabelDefaulting,

    /** Whether this attribute is required for instances of the parent vertex type. */
    override val optionality: EAttributeOptionality,

    /** The type of this attribute declaration. */
    override val type: AttributeType

) : IVertexAttributeDecl {

    init {
        parentVertexType.addAttribute(this)
    }

}
