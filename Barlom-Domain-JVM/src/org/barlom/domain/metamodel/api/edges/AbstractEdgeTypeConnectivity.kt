//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices.AbstractEdgeType
import org.barlom.domain.metamodel.api.vertices.VertexType

/**
 * Interface for Barlom edge type connectivity.
 */
abstract class AbstractEdgeTypeConnectivity : AbstractDocumentedElement() {

    /** The connecting edge type. */
    abstract val connectingEdgeType: AbstractEdgeType

    /** The connected vertex type. */
    abstract val connectedVertexType: VertexType

}