//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.api.elements.IEdgeAttributeDecl
import org.barlom.domain.metamodel.api.types.EAttributeOptionality

/**
 * Implementation class for edge attribute declarations.
 */
class EdgeAttributeDecl(

    override val id: String,

    override val name: String,

    /** The parent edge type with this attribute. */
    override val parentEdgeType: IEdgeTypeImpl,

    /** The type of this attribute declaration. */
    override val type: AttributeType,

    /** Whether this attribute is required for instances of the parent edge type. */
    override val optionality : EAttributeOptionality

) : IEdgeAttributeDecl {

    init {
        parentEdgeType.addAttribute( this )
    }

}
