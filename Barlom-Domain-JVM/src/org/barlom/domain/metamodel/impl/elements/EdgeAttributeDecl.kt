//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IEdgeAttributeDecl
import org.barlom.domain.metamodel.api.types.EAttributeOptionality

/**
 * Implementation class for edge attribute declarations.
 */
internal data class EdgeAttributeDecl(

    override val id: String,
    override val name: String,
    override val parentEdgeType: IEdgeTypeImpl,
    override val type: AttributeType,
    override val optionality : EAttributeOptionality

) : IEdgeAttributeDecl {

    init {
        parentEdgeType.addAttribute( this )
    }

}
