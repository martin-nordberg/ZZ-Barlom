package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.DirectedEdgeType
import org.barlom.domain.metamodel.api.vertices.VertexType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom directed edge type connectivity.
 */
class DirectedEdgeTypeTailConnectivity internal constructor(

    override val id: Uuid,
    override val connectingEdgeType: DirectedEdgeType,
    override val connectedVertexType: VertexType

) : AbstractEdgeTypeConnectivity() {

    init {

        check(!connectingEdgeType.isRoot || connectedVertexType.isRoot) {
            "Root edge type can connect only to root vertex type."
        }

        // Register both ends.
        connectingEdgeType.addDirectedEdgeTypeTailConnectivity(this)
        connectedVertexType.addDirectedEdgeTypeTailConnectivity(this)

    }


    override fun remove() {

        // Unregister both ends.
        connectingEdgeType.removeDirectedEdgeTypeTailConnectivity(this)
        connectedVertexType.removeDirectedEdgeTypeTailConnectivity(this)

    }

}