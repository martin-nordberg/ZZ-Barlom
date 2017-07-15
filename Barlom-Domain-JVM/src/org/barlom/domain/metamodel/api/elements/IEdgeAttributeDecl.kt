//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EAttributeOptionality

/**
 * Interface of edge attribute declarations. An edge type attribute declaration includes the name, optionality,
 * and data type for attributes in a given parent edge type.
 */
interface IEdgeAttributeDecl : INamedElement {

    /** Whether this attribute is required for instances of the parent edge type. */
    val optionality: EAttributeOptionality

    /** The parent edge type with this attribute. */
    val parentEdgeType: IEdgeType

    /** The type of this attribute declaration. */
    val type: IAttributeType

}
