//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IWriteableGraph
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface adding implementation details for a graph.
 */
internal interface IGraphImpl : IWriteableGraph {

    fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap

    fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap

}

//---------------------------------------------------------------------------------------------------------------------
