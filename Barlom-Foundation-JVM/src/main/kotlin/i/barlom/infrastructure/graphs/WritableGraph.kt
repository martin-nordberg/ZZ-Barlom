//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import x.barlom.infrastructure.graphs.getClassName
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

internal class WritableGraph(

    private val originalGraph: IGraphImpl

) : IWritableGraph, IGraphImpl {

    private val addedConcepts = ConceptMap()

    private val addedConnections = ConnectionMap()

    private val addedConnectionsFrom = ConceptConnectionMap()

    private val addedConnectionsTo = ConceptConnectionMap()

    private var isStillWriting = true

    private val removedConcepts = HashSet<Uuid>()

    private val removedConnections = HashSet<Uuid>()

    private val removedConnectionsFrom = ConceptConnectionMap()

    private val removedConnectionsTo = ConceptConnectionMap()

    private val updatedConcepts = ConceptMap()

    private val updatedConnections = ConnectionMap()

    private val updatedConnectionsFrom = ConceptConnectionMap()

    private val updatedConnectionsTo = ConceptConnectionMap()

    ////

    override val hasPredecessor
        get() = throw IllegalStateException("Method should be handled by wrapper.")

    override val isReadable
        get() = true

    override val isWritable
        get() = isStillWriting

    ////

    override val numConcepts
        get() =
            originalGraph.numConcepts + addedConcepts.size - removedConcepts.size

    override val numConnections
        get() =
            originalGraph.numConnections + addedConnections.size - removedConnections.size

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) {

        require(isWritable)
        require(!containsConcept(concept)) { "Concept $concept already added." }

        val conceptUuid = concept.id.uuid

        if ( removedConcepts.remove(conceptUuid) ) {
            updateConcept(concept)
        }
        else {
            addedConcepts.put(concept)
        }

        check(containsConcept(concept))

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) {

        require(isWritable)
        require(!containsConnectionWithId(connection.id))
        require(containsConceptWithId(connection.fromConceptId))
        require(containsConceptWithId(connection.toConceptId))

        val fromConceptUuid = connection.fromConceptId.uuid
        val toConceptUuid = connection.toConceptId.uuid
        val connectionUuid = connection.id.uuid

        addedConnections.put(connection)
        addedConnectionsFrom.put(fromConceptUuid, connection)
        addedConnectionsTo.put(toConceptUuid, connection)

        removedConnections.remove(connectionUuid)
        removedConnectionsFrom.remove(fromConceptUuid, connectionUuid)
        removedConnectionsTo.remove(toConceptUuid, connectionUuid)

        check(containsConnection(connection))
        check(connections(connection.fromConceptId).contains(connection))
        check(connections(connection.toConceptId).contains(connection))

    }

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E) {

        require(isWritable)
        require(!containsConnection(connection))
        require(containsConceptWithId(connection.conceptIdA))
        require(containsConceptWithId(connection.conceptIdB))

        val conceptAUuid = connection.conceptIdA.uuid
        val conceptBUuid = connection.conceptIdB.uuid
        val connectionUuid = connection.id.uuid

        addedConnections.put(connection)
        addedConnectionsFrom.put(conceptAUuid, connection)
        addedConnectionsTo.put(conceptAUuid, connection)
        addedConnectionsFrom.put(conceptBUuid, connection)
        addedConnectionsTo.put(conceptBUuid, connection)

        removedConnections.remove(connectionUuid)
        removedConnectionsFrom.remove(conceptAUuid, connectionUuid)
        removedConnectionsTo.remove(conceptAUuid, connectionUuid)
        removedConnectionsFrom.remove(conceptBUuid, connectionUuid)
        removedConnectionsTo.remove(conceptBUuid, connectionUuid)

        check(containsConnection(connection))
        check(connections(connection.conceptIdA).contains(connection))
        check(connections(connection.conceptIdB).contains(connection))

    }

    override fun clone() =
        throw IllegalStateException("Method should be handled by wrapper.")

    internal fun coalesceIntoPredecessor(precedingGraph: ReadableGraph) {

        require(!isStillWriting)

        precedingGraph.coalesceWithSuccessor(
            addedConcepts,
            updatedConcepts,
            removedConcepts,
            addedConnections,
            updatedConnections,
            removedConnections,
            addedConnectionsFrom,
            updatedConnectionsFrom,
            removedConnectionsFrom,
            addedConnectionsTo,
            updatedConnectionsTo,
            removedConnectionsTo
        )

    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {

        val conceptUuid = conceptId.uuid

        if (removedConcepts.contains(conceptUuid)) {
            return null
        }

        return updatedConcepts[conceptUuid] as V?
            ?: addedConcepts[conceptUuid] as V?
            ?: originalGraph.concept(conceptId)

    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? {

        val connectionUuid = connectionId.uuid

        if (removedConnections.contains(connectionUuid)) {
            return null
        }

        return updatedConnections[connectionUuid] as E?
            ?: addedConnections[connectionUuid] as E?
            ?: originalGraph.connection(connectionId)

    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {

        val result = ConnectionMap()

        result.putAll(originalGraph.mappedConnectionsFrom(conceptId.uuid))
        result.putAll(originalGraph.mappedConnectionsTo(conceptId.uuid))

        result.putAll(addedConnectionsFrom.getMap(conceptId.uuid))
        result.putAll(addedConnectionsTo.getMap(conceptId.uuid))

        result.putAll(updatedConnectionsFrom.getMap(conceptId.uuid))
        result.putAll(updatedConnectionsTo.getMap(conceptId.uuid))

        removedConnectionsFrom.getMap(conceptId.uuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }
        removedConnectionsTo.getMap(conceptId.uuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result
    }

    override fun <V : IConcept<V>> connectionsFrom(conceptId: Id<V>): Set<IConnection<*>> {

        val result = ConnectionMap()

        result.putAll(originalGraph.mappedConnectionsFrom(conceptId.uuid))

        result.putAll(addedConnectionsFrom.getMap(conceptId.uuid))

        result.putAll(updatedConnectionsFrom.getMap(conceptId.uuid))

        removedConnectionsFrom.getMap(conceptId.uuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result

    }

    override fun <V : IConcept<V>, E : IConnection<E>> connectionsFrom(conceptId: Id<V>, connectionType: ConnectionType<E>): Collection<E> {

        val result = mutableSetOf<E>()

        result.addAll(originalGraph.mappedConnectionsFrom(conceptId.uuid, connectionType))

        result.addAll(addedConnectionsFrom.getMapForConnectionType(conceptId.uuid, connectionType))

        result.addAll(updatedConnectionsFrom.getMapForConnectionType(conceptId.uuid, connectionType))

        removedConnectionsFrom.getMapForConnectionType(conceptId.uuid, connectionType).forEach { connection ->
            result.remove(connection)
        }

        return result

    }

    override fun <V : IConcept<V>> connectionsTo(conceptId: Id<V>): Set<IConnection<*>> {

        val result = ConnectionMap()

        result.putAll(originalGraph.mappedConnectionsTo(conceptId.uuid))

        result.putAll(addedConnectionsTo.getMap(conceptId.uuid))

        result.putAll(updatedConnectionsTo.getMap(conceptId.uuid))

        removedConnectionsTo.getMap(conceptId.uuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result
    }

    override fun <V : IConcept<V>, E : IConnection<E>> connectionsTo(conceptId: Id<V>, connectionType: ConnectionType<E>): Collection<E> {

        val result = mutableSetOf<E>()

        result.addAll(originalGraph.mappedConnectionsTo(conceptId.uuid, connectionType))

        result.addAll(addedConnectionsTo.getMapForConnectionType(conceptId.uuid, connectionType))

        result.addAll(updatedConnectionsTo.getMapForConnectionType(conceptId.uuid, connectionType))

        removedConnectionsTo.getMapForConnectionType<E>(conceptId.uuid, connectionType).forEach { connection ->
            result.remove(connection)
        }

        return result

    }

    override fun <V : IConcept<V>> containsConceptWithId(conceptId: Id<V>): Boolean {

        val conceptUuid = conceptId.uuid

        return (originalGraph.containsConceptWithId(conceptId) || addedConcepts.containsUuid(conceptUuid)) &&
            !removedConcepts.contains(conceptUuid)

    }

    override fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean {

        val connectionUuid = connectionId.uuid

        return (originalGraph.containsConnectionWithId(connectionId) || addedConnections.containsUuid(connectionUuid)) &&
            !removedConnections.contains(connectionUuid)

    }

    override fun isEmpty(): Boolean =
        numConcepts == 0

    override fun isNotEmpty() =
        !isEmpty()

    override fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap {

        val result = ConnectionMap()

        result.putAll(originalGraph.mappedConnectionsFrom(conceptUuid))

        result.putAll(addedConnectionsFrom.getMap(conceptUuid))
        result.putAll(updatedConnectionsFrom.getMap(conceptUuid))

        removedConnectionsFrom.getMap(conceptUuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result

    }

    override fun <E : IConnection<E>> mappedConnectionsFrom(conceptUuid: Uuid, connectionType: ConnectionType<E>): Collection<E> {

        val result = mutableSetOf<E>()

        result.addAll(originalGraph.mappedConnectionsFrom(conceptUuid, connectionType))

        result.addAll(addedConnectionsFrom.getMapForConnectionType(conceptUuid, connectionType))
        result.addAll(updatedConnectionsFrom.getMapForConnectionType(conceptUuid, connectionType))

        removedConnectionsFrom.getMapForConnectionType<E>(conceptUuid, connectionType).forEach { connection ->
            result.remove(connection)
        }

        return result

    }

    override fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap {

        val result = ConnectionMap()

        result.putAll(originalGraph.mappedConnectionsTo(conceptUuid))

        result.putAll(addedConnectionsTo.getMap(conceptUuid))
        result.putAll(updatedConnectionsTo.getMap(conceptUuid))

        removedConnectionsTo.getMap(conceptUuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result

    }

    override fun <E : IConnection<E>> mappedConnectionsTo(conceptUuid: Uuid, connectionType: ConnectionType<E>): Collection<E> {

        val result = mutableSetOf<E>()

        result.addAll(originalGraph.mappedConnectionsTo(conceptUuid, connectionType))

        result.addAll(addedConnectionsTo.getMapForConnectionType(conceptUuid, connectionType))
        result.addAll(updatedConnectionsTo.getMapForConnectionType(conceptUuid, connectionType))

        removedConnectionsTo.getMapForConnectionType<E>(conceptUuid, connectionType).forEach { connection ->
            result.remove(connection)
        }

        return result

    }

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) {

        require(isWritable)

        require(containsConceptWithId(conceptId))

        val conceptUuid = conceptId.uuid

        if (updatedConcepts.remove(conceptUuid) == null && addedConcepts.remove(conceptUuid) == null) {
            removedConcepts.add(conceptUuid)
        }

        updatedConnectionsFrom.removeConcept(conceptUuid)
        updatedConnectionsTo.removeConcept(conceptUuid)

        addedConnectionsFrom.removeConcept(conceptUuid)
        addedConnectionsTo.removeConcept(conceptUuid)

        for (connection in originalGraph.connections(conceptId)) {
            removedConnections.add(connection.id.uuid)
            if (connection is IDirectedConnection<*, *, *>) {
                removedConnectionsFrom.put(connection.fromConceptId.uuid, connection)
                removedConnectionsTo.put(connection.toConceptId.uuid, connection)
            }
            else {
                connection as IUndirectedConnection<*, *>
                removedConnectionsFrom.put(connection.conceptIdA.uuid, connection)
                removedConnectionsFrom.put(connection.conceptIdB.uuid, connection)
                removedConnectionsTo.put(connection.conceptIdA.uuid, connection)
                removedConnectionsTo.put(connection.conceptIdB.uuid, connection)
            }
        }

        check(!containsConceptWithId(conceptId))
        check(connections(conceptId).isEmpty())

    }

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) {

        require(isWritable)

        val connection = connection(connectionId)

        require(connection != null)

        val connectionUuid = connectionId.uuid

        if (connection is IDirectedConnection<*, *, *>) {

            val fromConceptUuid = connection.fromConceptId.uuid
            val toConceptUuid = connection.toConceptId.uuid

            if (updatedConnections.remove(connectionUuid) != null) {
                updatedConnectionsFrom.remove(fromConceptUuid, connectionUuid)
                updatedConnectionsTo.remove(toConceptUuid, connectionUuid)
            }
            else if (addedConnections.remove(connectionUuid) != null) {
                addedConnectionsFrom.remove(fromConceptUuid, connectionUuid)
                addedConnectionsTo.remove(toConceptUuid, connectionUuid)
            }
            else {
                removedConnections.add(connectionUuid)
                removedConnectionsFrom.put(fromConceptUuid, connection)
                removedConnectionsTo.put(toConceptUuid, connection)
            }

        }
        else if (connection is IUndirectedConnection<*, *>) {

            val conceptAUuid = connection.conceptIdA.uuid
            val conceptBUuid = connection.conceptIdB.uuid

            if (updatedConnections.remove(connectionUuid) != null) {
                updatedConnectionsFrom.remove(conceptAUuid, connectionUuid)
                updatedConnectionsTo.remove(conceptAUuid, connectionUuid)
                updatedConnectionsFrom.remove(conceptBUuid, connectionUuid)
                updatedConnectionsTo.remove(conceptBUuid, connectionUuid)
            }
            else if (addedConnections.remove(connectionUuid) != null) {
                addedConnectionsFrom.remove(conceptAUuid, connectionUuid)
                addedConnectionsTo.remove(conceptAUuid, connectionUuid)
                addedConnectionsFrom.remove(conceptBUuid, connectionUuid)
                addedConnectionsTo.remove(conceptBUuid, connectionUuid)
            }
            else {
                removedConnections.add(connectionUuid)
                removedConnectionsFrom.put(conceptAUuid, connection)
                removedConnectionsTo.put(conceptAUuid, connection)
                removedConnectionsFrom.put(conceptBUuid, connection)
                removedConnectionsTo.put(conceptBUuid, connection)
            }

        }

        check(!containsConnectionWithId(connectionId))

    }

    override fun startWriting(): Nothing =
        throw IllegalStateException("Method should be handled by wrapper.")

    override fun stopReading(): Nothing =
        throw IllegalStateException("Method should be handled by wrapper.")

    override fun stopWriting(): IGraph {

        require(isWritable)

        isStillWriting = false

        return this

    }

    override fun <V : IConcept<V>> updateConcept(concept: V) {

        require(isWritable)

        val conceptUuid = concept.id.uuid

        require(!removedConcepts.contains(conceptUuid))

        var existingConcept = addedConcepts[conceptUuid]

        if (existingConcept != null) {
            require(getClassName(concept) == getClassName(existingConcept))

            addedConcepts.put(concept)
        }
        else {
            existingConcept = originalGraph.concept(concept.id)

            require(existingConcept != null)
            require(getClassName(concept) == getClassName(existingConcept))

            updatedConcepts.put(concept)
        }

        check(containsConcept(concept))

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E) {

        require(isWritable)

        val connectionUuid = connection.id.uuid

        require(!removedConnections.contains(connectionUuid))

        val fromConceptUuid = connection.fromConceptId.uuid
        val toConceptUuid = connection.toConceptId.uuid

        var existingConnection = addedConnections[connectionUuid]

        if (existingConnection != null) {
            require(getClassName(connection) == getClassName(existingConnection))
            existingConnection as IDirectedConnection<*, *, *>
            require(connection.fromConceptId == existingConnection.fromConceptId)
            require(connection.toConceptId == existingConnection.toConceptId)

            addedConnections.put(connection)
            addedConnectionsFrom.put(fromConceptUuid, connection)
            addedConnectionsTo.put(toConceptUuid, connection)
        }
        else {
            existingConnection = originalGraph.connection(connection.id)

            require(existingConnection != null)
            require(getClassName(connection) == getClassName(existingConnection))
//            existingConnection as IDirectedConnection<*,*,*>
            require(connection.fromConceptId == existingConnection.fromConceptId)
            require(connection.toConceptId == existingConnection.toConceptId)

            updatedConnections.put(connection)
            updatedConnectionsFrom.put(fromConceptUuid, connection)
            addedConnectionsTo.put(toConceptUuid, connection)
        }

        check(containsConnection(connection))

    }

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> updateConnection(connection: E) {

        require(isWritable)

        val connectionUuid = connection.id.uuid

        require(!removedConnections.contains(connectionUuid))

        val conceptAUuid = connection.conceptIdA.uuid
        val conceptBUuid = connection.conceptIdB.uuid

        var existingConnection = addedConnections[connectionUuid]

        if (existingConnection != null) {
            require(getClassName(connection) == getClassName(existingConnection))

            addedConnections.put(connection)
            addedConnectionsFrom.put(conceptAUuid, connection)
            addedConnectionsTo.put(conceptAUuid, connection)
            addedConnectionsFrom.put(conceptBUuid, connection)
            addedConnectionsTo.put(conceptBUuid, connection)
        }
        else {
            existingConnection = originalGraph.connection(connection.id)

            require(existingConnection != null)
            require(getClassName(connection) == getClassName(existingConnection))

            updatedConnections.put(connection)
            updatedConnectionsFrom.put(conceptAUuid, connection)
            updatedConnectionsTo.put(conceptAUuid, connection)
            updatedConnectionsFrom.put(conceptBUuid, connection)
            updatedConnectionsTo.put(conceptBUuid, connection)
        }

        check(containsConnection(connection))

    }

}

//---------------------------------------------------------------------------------------------------------------------

