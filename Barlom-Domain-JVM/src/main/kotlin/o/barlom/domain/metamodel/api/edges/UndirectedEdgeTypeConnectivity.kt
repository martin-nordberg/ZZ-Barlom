//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.edges

import o.barlom.domain.metamodel.api.vertices.UndirectedEdgeType
import o.barlom.domain.metamodel.api.vertices.VertexType
import x.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom undirected edge type containment.
 */
class UndirectedEdgeTypeConnectivity internal constructor(

    override val id: Uuid,
    override val connectingEdgeType: UndirectedEdgeType,
    override val connectedVertexType: VertexType

) : AbstractEdgeTypeConnectivity() {

    init {

        check(!connectingEdgeType.isRoot || connectedVertexType.isRoot) {
            "Root edge type can connect only to root vertex type."
        }

        // Register both ends.
        connectingEdgeType.addUndirectedEdgeTypeConnectivity(this)
        connectedVertexType.addUndirectedEdgeTypeConnectivity(this)

    }


    override fun remove() {

        // Unregister both ends.
        connectingEdgeType.removeUndirectedEdgeTypeConnectivity(this)
        connectedVertexType.removeUndirectedEdgeTypeConnectivity(this)

    }

}
