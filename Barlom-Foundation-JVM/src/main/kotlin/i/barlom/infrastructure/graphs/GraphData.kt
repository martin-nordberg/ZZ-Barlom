//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * Holds the data structures representing a graph.
 */
internal data class GraphData(

    /** The concepts (vertices) of the graph. */
    val concepts: ConceptMap = ConceptMap(),

    /** The connections (edges) of the graph. */
    val connections: ConnectionMap = ConnectionMap(),

    /** Adjacency list for outgoing connections (edges) including undirected edges. */
    val connectionsFrom: ConceptConnectionMap = ConceptConnectionMap(),

    /** Adjacency list for incoming connections (edges) including undirected connections. */
    val connectionsTo: ConceptConnectionMap = ConceptConnectionMap()

) {

    /** @return a deep copy of this graph data. Concepts and connections are assumed to be immutable. */
    fun clone(): GraphData {

        val result = GraphData()

        result.concepts.putAll(concepts)
        result.connections.putAll(connections)
        result.connectionsFrom.putAll(connectionsFrom)
        result.connectionsTo.putAll(connectionsTo)

        return result

    }

}

//---------------------------------------------------------------------------------------------------------------------

