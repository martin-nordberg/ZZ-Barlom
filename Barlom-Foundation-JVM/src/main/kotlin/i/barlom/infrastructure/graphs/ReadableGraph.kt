//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/** A graph with fixed state. Operations to modify the graph result in IllegalStateException throws. */
internal class ReadableGraph(

    private val data: GraphData = GraphData()

) : IGraphImpl {

    override val hasPredecessor
        get() = throw IllegalStateException("Method should be handled by wrapper.")

    override val isReadable =
        true

    override val isWritable =
        false

    override val numConcepts
        get() = data.concepts.size

    override val numConnections
        get() = data.connections.size

    ////

    override fun <V : IConcept<V>> addConcept(concept: V): Nothing =
        throw IllegalStateException("Graph is not writeable.")

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E): Nothing =
        throw IllegalStateException("Graph is not writeable.")

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E) =
        throw IllegalStateException("Graph is not writeable.")

    override fun clone() =
        ReadableGraph(data.clone())

    @Suppress("UNCHECKED_CAST")
    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? =
        data.concepts[conceptId.uuid] as V?

    @Suppress("UNCHECKED_CAST")
    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? =
        data.connections[connectionId.uuid] as E?

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {

        val result = ConnectionMap()

        result.putAll(data.connectionsTo.getMap(conceptId.uuid))
        result.putAll(data.connectionsFrom.getMap(conceptId.uuid))

        return result

    }

    override fun <V : IConcept<V>> connectionsFrom(conceptId: Id<V>): Set<IConnection<*>> =
        data.connectionsFrom.getMap(conceptId.uuid)

    override fun <V : IConcept<V>, E : IConnection<E>> connectionsFrom(
        conceptId: Id<V>,
        connectionType: ConnectionTypeId<E>
    ): Collection<E> =
        data.connectionsFrom.getMapForConnectionType(conceptId.uuid, connectionType)

    override fun <V : IConcept<V>> connectionsTo(conceptId: Id<V>): Set<IConnection<*>> =
        data.connectionsTo.getMap(conceptId.uuid)

    override fun <V : IConcept<V>, E : IConnection<E>> connectionsTo(
        conceptId: Id<V>,
        connectionType: ConnectionTypeId<E>
    ): Collection<E> =
        data.connectionsTo.getMapForConnectionType(conceptId.uuid, connectionType)

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

    override fun <E : IConnection<E>> mappedConnectionsFrom(conceptUuid: Uuid, connectionType: ConnectionTypeId<E>): Collection<E> =
        data.connectionsFrom.getMapForConnectionType(conceptUuid, connectionType)

    override fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap =
        data.connectionsTo.getMap(conceptUuid)

    override fun <E : IConnection<E>> mappedConnectionsTo(conceptUuid: Uuid, connectionType: ConnectionTypeId<E>): Collection<E> =
        data.connectionsTo.getMapForConnectionType(conceptUuid, connectionType)

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) =
        throw IllegalStateException("Graph is not writeable.")

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) =
        throw IllegalStateException("Graph is not writeable.")

    override fun startWriting() =
        throw IllegalStateException("Method should be handled by wrapper.")

    override fun stopReading() {
        throw IllegalStateException("Method should be handled by wrapper.")
    }

    override fun stopWriting() =
        throw IllegalStateException("Graph is not writeable.")

    override fun <V : IConcept<V>> updateConcept(concept: V) =
        throw IllegalStateException("Graph is not writeable.")

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E) =
        throw IllegalStateException("Graph is not writeable.")

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> updateConnection(connection: E) =
        throw IllegalStateException("Graph is not writeable.")

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
        removedConnectionsTo: ConceptConnectionMap
    ) {

        with(data) {

            concepts.putAll(addedConcepts)
            concepts.putAll(updatedConcepts)
            removedConcepts.forEach { conceptUuid -> concepts.remove(conceptUuid) }

            connections.putAll(addedConnections)
            connections.putAll(updatedConnections)
            removedConnections.forEach { connectionUuid -> connections.remove(connectionUuid) }

            connectionsFrom.putAll(addedConnectionsFrom)
            connectionsFrom.putAll(updatedConnectionsFrom)
            connectionsFrom.removeAll(removedConnectionsFrom)

            connectionsTo.putAll(addedConnectionsTo)
            connectionsTo.putAll(updatedConnectionsTo)
            connectionsTo.removeAll(removedConnectionsTo)

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

