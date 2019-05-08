//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

internal class WriteableGraph(

    private val originalGraph: IGraphImpl

) : IWriteableGraph, IGraphImpl {

    private val addedConcepts = ConceptMap()

    private val addedConnections = ConnectionMap()

    private val addedConnectionsFrom = ConceptConnectionMap()

    private val addedConnectionsTo = ConceptConnectionMap()

    private var _isReadable = true

    private var _isWriteable = true

    private val removedConcepts = HashSet<Uuid>()

    private val removedConnections = HashSet<Uuid>()

    private val removedConnectionsFrom = ConceptConnectionMap()

    private val removedConnectionsTo = ConceptConnectionMap()

    private val updatedConcepts = ConceptMap()

    private val updatedConnections = ConnectionMap()

    private val updatedConnectionsFrom = ConceptConnectionMap()

    private val updatedConnectionsTo = ConceptConnectionMap()

    ////

    override val isReadable: Boolean
        get() = _isReadable

    override val isWriteable: Boolean
        get() = _isWriteable

    ////

    override val numConcepts: Int
        get() =
            originalGraph.numConcepts + addedConcepts.size - removedConcepts.size

    override val numConnections: Int
        get() =
            originalGraph.numConnections + addedConnections.size - removedConnections.size

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) {

        require(_isWriteable)
        require(!containsConcept(concept)) { "Concept $concept already added." }

        val conceptUuid = concept.id.uuid

        addedConcepts.put(concept)
        removedConcepts.remove(conceptUuid)

        check(containsConcept(concept))

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) {

        require(_isWriteable)
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

        require(_isWriteable)
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

    internal fun coalesceIntoPredecessor(precedingGraph:ReadableGraph) {

        require(!_isWriteable)

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
            removedConnectionsTo,
            _isReadable
        )

    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {

        require(_isReadable)

        val conceptUuid = conceptId.uuid

        if (removedConcepts.contains(conceptUuid)) {
            return null
        }

        return updatedConcepts[conceptUuid] as V?
            ?: addedConcepts[conceptUuid] as V?
            ?: originalGraph.concept(conceptId)

    }

    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? {

        require(_isReadable)

        val connectionUuid = connectionId.uuid

        if (removedConnections.contains(connectionUuid)) {
            return null
        }

        return updatedConnections[connectionUuid] as E?
            ?: addedConnections[connectionUuid] as E?
            ?: originalGraph.connection(connectionId)

    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {

        require(_isReadable)

        val result = ConnectionMap()

        result.putAll(originalGraph.mappedConnectionsTo(conceptId.uuid))
        result.putAll(originalGraph.mappedConnectionsFrom(conceptId.uuid))

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

        require(_isReadable)

        val conceptUuid = conceptId.uuid

        return ( originalGraph.containsConceptWithId(conceptId) || addedConcepts.containsUuid(conceptUuid) ) &&
            !removedConcepts.contains(conceptUuid)

    }

    override fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean {

        require(_isReadable)

        val connectionUuid = connectionId.uuid

        return ( originalGraph.containsConnectionWithId(connectionId) || addedConnections.containsUuid(connectionUuid) ) &&
            !removedConnections.contains(connectionUuid)

    }

    override fun isEmpty() : Boolean =
        numConcepts == 0

    override fun isNotEmpty() =
        !isEmpty()

    override fun mappedConnectionsFrom(conceptUuid: Uuid): ConnectionMap {

        val result = ConnectionMap()

        result.putAll( originalGraph.mappedConnectionsFrom(conceptUuid) )

        result.putAll( addedConnectionsFrom.getMap(conceptUuid) )
        result.putAll( updatedConnectionsFrom.getMap(conceptUuid) )

        removedConnectionsFrom.getMap(conceptUuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result

    }

    override fun mappedConnectionsTo(conceptUuid: Uuid): ConnectionMap {

        val result = ConnectionMap()

        result.putAll( originalGraph.mappedConnectionsTo(conceptUuid) )

        result.putAll( addedConnectionsTo.getMap(conceptUuid) )
        result.putAll( updatedConnectionsTo.getMap(conceptUuid) )

        removedConnectionsTo.getMap(conceptUuid).forEach { connection ->
            result.remove(connection.id.uuid)
        }

        return result

    }

    internal fun notifyOriginalStoppedReading(graphData: GraphData) {

    }

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) {

        require(_isWriteable)

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

            removedConnectionsFrom.putAll(conceptUuid,originalGraph.mappedConnectionsFrom(conceptUuid))
            removedConnectionsTo.putAll(conceptUuid,originalGraph.mappedConnectionsTo(conceptUuid))

        }

        check(!containsConceptWithId(conceptId))

    }

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) {

        require(_isWriteable)

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

    override fun startWriting(): IWriteableGraph =
        throw IllegalStateException( "Method should be handled by wrapper." )

    override fun stopReading() {

        require(!_isWriteable)

        _isReadable = false

    }

    override fun stopWriting(): IGraph {

        require(_isWriteable)

        _isWriteable = false

        return this

    }

    override fun <V : IConcept<V>> updateConcept(concept: V) {

        require(_isWriteable)

        val conceptUuid = concept.id.uuid

        require(!removedConcepts.contains(conceptUuid))

        var existingConcept : IConcept<*>? = addedConcepts[conceptUuid]

        if (existingConcept != null) {
            require(concept.javaClass === existingConcept.javaClass)

            addedConcepts.put(concept)
        }
        else {
            existingConcept = originalGraph.concept(concept.id)

            require(existingConcept != null)
            require(concept.javaClass === existingConcept.javaClass)

            updatedConcepts.put(concept)
        }

        check(containsConcept(concept))

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E) {

        require(_isWriteable)

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
            existingConnection = originalGraph.connection(connection.id)

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

        require(_isWriteable)

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
            existingConnection = originalGraph.connection(connection.id)

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

