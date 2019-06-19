//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IConnection
import o.barlom.infrastructure.graphs.IWritableGraph
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface adding implementation details for a graph.
 */
internal interface IGraphImpl : IWritableGraph {

    /** @return the connection map for outgoing connections from the concept with UUID [conceptUuid]. */
    fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap

    /** @return the connection set for outgoing connections of type [connectionTypeName] from the concept with UUID [conceptUuid]. */
    fun <E : IConnection<E>> mappedConnectionsFrom(conceptUuid: Uuid, connectionTypeName: String): Collection<E>

    /** @return the connection map for incoming connections to the concept with UUID [conceptUuid]. */
    fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap

    /** @return the connection set for incoming connections of type [connectionTypeName] to the concept with UUID [conceptUuid]. */
    fun <E : IConnection<E>> mappedConnectionsTo(conceptUuid: Uuid, connectionTypeName: String): Collection<E>

}

//---------------------------------------------------------------------------------------------------------------------
