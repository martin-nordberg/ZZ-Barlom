//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.DirectedEdgeType
import o.org.barlom.domain.metamodel.api.vertices.VertexType
import x.org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom directed edge type connectivity.
 */
class DirectedEdgeTypeHeadConnectivity internal constructor(

    override val id: Uuid,
    override val connectingEdgeType: DirectedEdgeType,
    override val connectedVertexType: VertexType

) : AbstractEdgeTypeConnectivity() {

    init {

        check(!connectingEdgeType.isRoot || connectedVertexType.isRoot) {
            "Root edge type can connect only to root vertex type."
        }

        // Register both ends.
        connectingEdgeType.addDirectedEdgeTypeHeadConnectivity(this)
        connectedVertexType.addDirectedEdgeTypeHeadConnectivity(this)

    }


    override fun remove() {

        // Unregister both ends.
        connectingEdgeType.removeDirectedEdgeTypeHeadConnectivity(this)
        connectedVertexType.removeDirectedEdgeTypeHeadConnectivity(this)

    }

}