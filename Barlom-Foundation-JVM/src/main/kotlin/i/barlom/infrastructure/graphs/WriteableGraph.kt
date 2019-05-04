//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

class WriteableGraph(

    private val data: GraphData

) : IWriteableGraph {

    private val addedConcepts = ConceptMap()

    private val addedConnections = ConnectionMap()

    private val addedConnectionsFrom = ConceptConnectionMap()

    private val addedConnectionsTo = ConceptConnectionMap()

    private var isAvailableForUse = true

    private val removedConcepts = HashSet<Uuid>()

    private val removedConnections = HashSet<Uuid>()

    private val removedConnectionsFrom = ConceptConnectionMap()

    private val removedConnectionsTo = ConceptConnectionMap()

    private val updatedConcepts = ConceptMap()

    private val updatedConnections = ConnectionMap()

    private val updatedConnectionsFrom = ConceptConnectionMap()

    private val updatedConnectionsTo = ConceptConnectionMap()

    ////

    override val numConcepts: Int
        get() =
            data.concepts.size + addedConcepts.size - removedConcepts.size

    override val numConnections: Int
        get() =
            data.connections.size + addedConnections.size - removedConnections.size

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) {

        require(isAvailableForUse)
        require(!containsConcept(concept)) { "Concept $concept already added." }

        val conceptUuid = concept.id.uuid

        addedConcepts.put(concept)
        removedConcepts.remove(conceptUuid)

        check(containsConcept(concept))

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) {

        require(isAvailableForUse)
        require(!containsConnectionWithId(connection.id))

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

        require(isAvailableForUse)
        require(!containsConnection(connection))

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

    override fun beginTransaction(): Nothing {
        throw IllegalStateException("Already in a transaction.")
    }

    override fun commit(): IGraph {

        require(isAvailableForUse)

        data.concepts.putAll(addedConcepts)
        data.concepts.putAll(updatedConcepts)
        removedConcepts.forEach { uuid -> data.concepts.remove(uuid) }

        data.connections.putAll(addedConnections)
        data.connections.putAll(updatedConnections)
        removedConnections.forEach { uuid -> data.connections.remove(uuid) }

        data.connectionsFrom.putAll(addedConnectionsFrom)
        data.connectionsFrom.putAll(updatedConnectionsFrom)
        data.connectionsFrom.removeAll(removedConnectionsFrom)

        data.connectionsTo.putAll(addedConnectionsTo)
        data.connectionsTo.putAll(updatedConnectionsTo)
        data.connectionsTo.removeAll(removedConnectionsFrom)

        isAvailableForUse = false

        return Graph(data)

    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {

        require(isAvailableForUse)

        val conceptUuid = conceptId.uuid

        if (removedConcepts.contains(conceptUuid)) {
            return null
        }

        return updatedConcepts[conceptUuid] as V?
            ?: addedConcepts[conceptUuid] as V?
            ?: data.concepts[conceptUuid] as V?

    }

    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? {

        require(isAvailableForUse)

        val connectionUuid = connectionId.uuid

        if (removedConnections.contains(connectionUuid)) {
            return null
        }

        return updatedConnections[connectionUuid] as E?
            ?: addedConnections[connectionUuid] as E?
            ?: data.connections[connectionUuid] as E?

    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {

        require(isAvailableForUse)

        val result = ConnectionMap()

        result.putAll(data.connectionsTo.getMap(conceptId.uuid))
        result.putAll(data.connectionsFrom.getMap(conceptId.uuid))

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

    override fun <V : IConcept<V>> containsConceptWithId(conceptId: Id<V>): Boolean {

        require(isAvailableForUse)

        val conceptUuid = conceptId.uuid

        return ( data.concepts.containsUuid(conceptUuid) || addedConcepts.containsUuid(conceptUuid) ) &&
            !removedConcepts.contains(conceptUuid)

    }

    override fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean {

        require(isAvailableForUse)

        val connectionUuid = connectionId.uuid

        return ( data.connections.containsUuid(connectionUuid) || addedConnections.containsUuid(connectionUuid) ) &&
            !removedConnections.contains(connectionUuid)

    }

    override fun isEmpty() : Boolean =
        numConcepts == 0

    override fun isNotEmpty() =
        !isEmpty()

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) {

        require(isAvailableForUse)

        if ( containsConceptWithId(conceptId) ) {

            val conceptUuid = conceptId.uuid

            if ( updatedConcepts.remove(conceptUuid) != null ) {
                updatedConnectionsFrom.removeConcept(conceptUuid)
                updatedConnectionsTo.removeConcept(conceptUuid)
            }
            else if ( addedConcepts.remove(conceptUuid) == null ) {
                addedConnectionsFrom.removeConcept(conceptUuid)
                addedConnectionsTo.removeConcept(conceptUuid)
            }

            removedConnectionsFrom.putAll(conceptUuid,data.connectionsFrom.getMap(conceptUuid))
            removedConnectionsTo.putAll(data.connectionsTo)

        }

        check(!containsConceptWithId(conceptId))

    }

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) {

        require(isAvailableForUse)

        val connection = connection(connectionId)

        if ( connection != null) {

            val connectionUuid = connectionId.uuid

            if ( connection is IDirectedConnection<*,*,*> ) {

                val fromConceptUuid = connection.fromConceptId.uuid
                val toConceptUuid = connection.toConceptId.uuid

                if ( updatedConnections.remove(connectionUuid) != null ) {
                    updatedConnectionsFrom.remove(fromConceptUuid,connectionUuid)
                    updatedConnectionsTo.remove(toConceptUuid,connectionUuid)
                }
                else if ( addedConnections.remove(connectionUuid) != null ) {
                    addedConnectionsFrom.remove(fromConceptUuid,connectionUuid)
                    addedConnectionsTo.remove(toConceptUuid,connectionUuid)
                }
                else {
                    removedConnections.add(connectionUuid)
                    removedConnectionsFrom.put(fromConceptUuid, connection)
                    removedConnectionsTo.put(toConceptUuid, connection)
                }

            }
            else if ( connection is IUndirectedConnection<*,*> ) {

                val conceptAUuid = connection.conceptIdA.uuid
                val conceptBUuid = connection.conceptIdB.uuid

                if ( updatedConnections.remove(connectionUuid) != null ) {
                    updatedConnectionsFrom.remove(conceptAUuid,connectionUuid)
                    updatedConnectionsTo.remove(conceptAUuid,connectionUuid)
                    updatedConnectionsFrom.remove(conceptBUuid,connectionUuid)
                    updatedConnectionsTo.remove(conceptBUuid,connectionUuid)
                }
                else if ( addedConnections.remove(connectionUuid) != null ) {
                    addedConnectionsFrom.remove(conceptAUuid,connectionUuid)
                    addedConnectionsTo.remove(conceptAUuid,connectionUuid)
                    addedConnectionsFrom.remove(conceptBUuid,connectionUuid)
                    addedConnectionsTo.remove(conceptBUuid,connectionUuid)
                }
                else {
                    removedConnections.add(connectionUuid)
                    removedConnectionsFrom.put(conceptAUuid, connection)
                    removedConnectionsTo.put(conceptAUuid, connection)
                    removedConnectionsFrom.put(conceptBUuid, connection)
                    removedConnectionsTo.put(conceptBUuid, connection)
                }

            }

        }

        check(!containsConnectionWithId(connectionId))

    }

    override fun rollBack(): IGraph {

        require(isAvailableForUse)

        isAvailableForUse = false

        return Graph(data)

    }

    override fun <V : IConcept<V>> updateConcept(concept: V) {

        require(isAvailableForUse)

        val conceptUuid = concept.id.uuid

        require(!removedConcepts.contains(conceptUuid))

        var existingConcept : IConcept<*>? = addedConcepts[conceptUuid]

        if (existingConcept != null) {
            require(concept.javaClass === existingConcept.javaClass)

            addedConcepts.put(concept)
        }
        else {
            existingConcept = data.concepts[conceptUuid]

            require(existingConcept != null)
            require(concept.javaClass === existingConcept.javaClass)

            updatedConcepts.put(concept)
        }

        check(containsConcept(concept))

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E) {

        require(isAvailableForUse)

        val connectionUuid = connection.id.uuid

        require(!removedConnections.contains(connectionUuid))

        val fromConceptUuid = connection.fromConceptId.uuid
        val toConceptUuid = connection.toConceptId.uuid

        var existingConnection = addedConnections[connectionUuid]

        if ( existingConnection != null ) {
            require(connection.javaClass === existingConnection.javaClass)
            existingConnection as IDirectedConnection<*,*,*>
            require(connection.fromConceptId == existingConnection.fromConceptId)
            require(connection.toConceptId == existingConnection.toConceptId)

            addedConnections.put(connection)
            addedConnectionsFrom.put(fromConceptUuid, connection)
            addedConnectionsTo.put(toConceptUuid, connection)
        }
        else {
            existingConnection = data.connections[connectionUuid]

            require(existingConnection != null)
            require(connection.javaClass === existingConnection.javaClass)
            existingConnection as IDirectedConnection<*,*,*>
            require(connection.fromConceptId == existingConnection.fromConceptId)
            require(connection.toConceptId == existingConnection.toConceptId)

            updatedConnections.put(connection)
            updatedConnectionsFrom.put(fromConceptUuid, connection)
            addedConnectionsTo.put(toConceptUuid, connection)
        }

        check(containsConnection(connection))

    }

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> updateConnection(connection: E) {

        require(isAvailableForUse)

        val connectionUuid = connection.id.uuid

        require(!removedConnections.contains(connectionUuid))

        val conceptAUuid = connection.conceptIdA.uuid
        val conceptBUuid = connection.conceptIdB.uuid

        var existingConnection = addedConnections[connectionUuid]

        if ( existingConnection != null ) {
            require(connection.javaClass === existingConnection.javaClass)

            addedConnections.put(connection)
            addedConnectionsFrom.put(conceptAUuid, connection)
            addedConnectionsTo.put(conceptAUuid, connection)
            addedConnectionsFrom.put(conceptBUuid, connection)
            addedConnectionsTo.put(conceptBUuid, connection)
        }
        else {
            existingConnection = data.connections[connectionUuid]

            require(existingConnection != null)
            require(connection.javaClass === existingConnection.javaClass)

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

