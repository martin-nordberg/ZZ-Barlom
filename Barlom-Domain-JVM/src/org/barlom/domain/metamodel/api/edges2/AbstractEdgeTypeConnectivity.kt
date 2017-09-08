//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.AbstractEdgeType
import org.barlom.domain.metamodel.api.vertices2.VertexType

/**
 * Interface for Barlom edge type connectivity.
 */
abstract class AbstractEdgeTypeConnectivity : AbstractDocumentedElement() {

    /** The connecting edge type. */
    abstract val connectingEdgeType: AbstractEdgeType

    /** The connected vertex type. */
    abstract val connectedVertexType: VertexType

}