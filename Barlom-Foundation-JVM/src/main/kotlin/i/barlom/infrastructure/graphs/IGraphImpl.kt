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

    fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap

    fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap

}

//---------------------------------------------------------------------------------------------------------------------
