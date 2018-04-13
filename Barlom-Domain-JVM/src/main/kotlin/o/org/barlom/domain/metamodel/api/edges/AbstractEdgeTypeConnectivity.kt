//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.AbstractEdgeType
import o.org.barlom.domain.metamodel.api.vertices.AbstractElement
import o.org.barlom.domain.metamodel.api.vertices.VertexType

/**
 * Interface for Barlom edge type connectivity.
 */
abstract class AbstractEdgeTypeConnectivity : AbstractElement() {

    /** The connecting edge type. */
    abstract val connectingEdgeType: AbstractEdgeType

    /** The connected vertex type. */
    abstract val connectedVertexType: VertexType

}