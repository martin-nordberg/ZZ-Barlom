//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting

/**
 * Interface to vertex attribute types. A vertex attribute type includes the name, optionality, and constrained data
 * type for attributes in a given parent vertex type.
 */
interface IVertexAttributeType : INamedElement {

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    var labelDefaulting: ELabelDefaulting

    /** Whether this attribute is required for instances of the parent vertex type. */
    var optionality: EAttributeOptionality

    /** The vertex type with attributes meeting this type. */
    val parentVertexType: IVertexType

    /** The constrained data type of this attribute type. */
    val type: IConstrainedDataType

}
