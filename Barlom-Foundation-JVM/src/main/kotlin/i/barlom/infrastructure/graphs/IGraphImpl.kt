//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IWritableGraph
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface adding implementation details for a graph.
 */
internal interface IGraphImpl : IWritableGraph {

    /** @return the connection map for outgoing connections from the concept with UUID [conceptUuid]. */
    fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap

    /** @return the connection map for incoming connections to the concept with UUID [conceptUuid]. */
    fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap

}

//---------------------------------------------------------------------------------------------------------------------
