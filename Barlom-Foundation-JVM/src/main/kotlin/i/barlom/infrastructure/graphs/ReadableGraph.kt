//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

internal class ReadableGraph(

    private val data: GraphData = GraphData()

) : IGraphImpl {

    private var _isReadable = true

    ////

    override val isReadable: Boolean
        get() = _isReadable

    override val isWriteable: Boolean =
        false

    override val numConcepts: Int
        get() = data.concepts.size

    override val numConnections: Int
        get() = data.connections.size

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {

        require(isReadable)

        return data.concepts[conceptId.uuid] as V?

    }

    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? {

        require(isReadable)

        return data.connections[connectionId.uuid] as E?

    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {

        require(isReadable)

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

    override fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap =
        data.connectionsFrom.getMap(conceptUuid)

    override fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap =
        data.connectionsTo.getMap(conceptUuid)

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun startWriting(): IWriteableGraph =
        throw IllegalStateException( "Method should be handled by wrapper." )

    override fun stopReading() {
        _isReadable = false
    }

    override fun stopWriting(): IGraph =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <V : IConcept<V>> updateConcept(concept: V) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E) =
        throw IllegalStateException( "Graph is not writeable." )

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> updateConnection(connection: E) =
        throw IllegalStateException( "Graph is not writeable." )

    fun coalesceWithSuccessor(
        addedConcepts: ConceptMap,
        updatedConcepts: ConceptMap,
        removedConcepts: HashSet<Uuid>,
        addedConnections: ConnectionMap,
        updatedConnections: ConnectionMap,
        removedConnections: HashSet<Uuid>,
        addedConnectionsFrom: ConceptConnectionMap,
        updatedConnectionsFrom: ConceptConnectionMap,
        removedConnectionsFrom: ConceptConnectionMap,
        addedConnectionsTo: ConceptConnectionMap,
        updatedConnectionsTo: ConceptConnectionMap,
        removedConnectionsTo: ConceptConnectionMap,
        isReadable: Boolean
    ) {

        with(data) {

            concepts.putAll(addedConcepts)
            concepts.putAll(updatedConcepts)
            removedConcepts.forEach { uuid -> concepts.remove(uuid) }

            connections.putAll(addedConnections)
            connections.putAll(updatedConnections)
            removedConnections.forEach { uuid -> connections.remove(uuid) }

            connectionsFrom.putAll(addedConnectionsFrom)
            connectionsFrom.putAll(updatedConnectionsFrom)
            connectionsFrom.removeAll(removedConnectionsFrom)

            connectionsTo.putAll(addedConnectionsTo)
            connectionsTo.putAll(updatedConnectionsTo)
            connectionsTo.removeAll(removedConnectionsTo)

        }

        _isReadable = isReadable

    }

}

//---------------------------------------------------------------------------------------------------------------------

