//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

class Graph(

    private val data: GraphData = GraphData()

) : IGraph {

    private var isAvailableForUse = true

    ////

    override val numConcepts: Int =
        data.concepts.size

    override val numConnections: Int =
        data.connections.size

    ////

    override fun beginTransaction(): IWriteableGraph {

        require(isAvailableForUse)

        isAvailableForUse = false

        return WriteableGraph(data)

    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {

        require(isAvailableForUse)

        return data.concepts[conceptId.uuid] as V?

    }

    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? {

        require(isAvailableForUse)

        return data.connections[connectionId.uuid] as E?

    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {

        require(isAvailableForUse)

        val result = ConnectionMap()

        result.putAll(data.connectionsTo.getMap(conceptId.uuid))
        result.putAll(data.connectionsFrom.getMap(conceptId.uuid))

        return result

    }

    override fun <V : IConcept<V>> containsConceptWithId(conceptId: Id<V>): Boolean =
        data.concepts.containsUuid(conceptId.uuid)

    override fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean =
        data.connections.containsUuid(connectionId.uuid)

    override fun isEmpty() =
        data.concepts.isEmpty()

    override fun isNotEmpty() =
        !isEmpty()

}

//---------------------------------------------------------------------------------------------------------------------

fun graphOf(): IGraph =
    Graph()

//---------------------------------------------------------------------------------------------------------------------

