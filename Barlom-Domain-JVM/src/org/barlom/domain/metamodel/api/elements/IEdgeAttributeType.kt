//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EAttributeOptionality

/**
 * Interface of edge attribute types. An edge attribute type includes the name, optionality, and constrained data
 * type for attributes in a given parent edge type.
 */
interface IEdgeAttributeType : INamedElement {

    /** Whether this attribute is required for instances of the parent edge type. */
    var optionality: EAttributeOptionality

    /** The parent edge type with this attribute type. */
    val parentEdgeType: IEdgeType

    /** The constrained data type of this attribute type. */
    val type: IConstrainedDataType

}
