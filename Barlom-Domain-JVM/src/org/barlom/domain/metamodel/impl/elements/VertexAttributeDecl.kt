//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeDecl
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting

/**
 * Implementation class for vertex attribute declarations.
 */
internal data class VertexAttributeDecl(

    override val id: String,
    override val name: String,
    override val parentVertexType: VertexType,
    override val labelDefaulting: ELabelDefaulting,
    override val optionality: EAttributeOptionality,
    override val type: AttributeType

) : IVertexAttributeDecl {

    init {
        parentVertexType.addAttribute(this)
    }

}
