//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.types.EAttributeOptionality

/**
 * Implementation class for edge attribute declarations.
 */
class EdgeAttributeDecl(
    id: String,
    name: String,

    /** The parent edge type with this attribute. */
    val parentEdgeType: NamedEdgeType,

    /** The type of this attribute declaration. */
    val type: AttributeType,

    /** Whether this attribute is required for instances of the parent edge type. */
    val optionality : EAttributeOptionality

) : NamedElement( id, name) {

    init {
        parentEdgeType.addAttribute( this )
    }

}
