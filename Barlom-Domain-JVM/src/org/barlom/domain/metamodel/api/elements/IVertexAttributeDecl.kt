//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting

/**
 * Interface to vertex attribute declarations.
 */
interface IVertexAttributeDecl : INamedElement {

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    val labelDefaulting: ELabelDefaulting

    /** Whether this attribute is required for instances of the parent vertex type. */
    val optionality: EAttributeOptionality

    val parentVertexType: IVertexType

    /** The type of this attribute declaration. */
    val type: IAttributeType

}
