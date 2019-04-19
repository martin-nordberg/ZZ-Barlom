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

    private val addedConcepts = mutableMapOf<Uuid, IConcept<*>>()

    private val addedConnectionsFrom = mutableMapOf<Uuid, MutableSet<IConnection<*>>>()

    private val addedConnectionsTo = mutableMapOf<Uuid, MutableSet<IConnection<*>>>()

    private var isAvailableForUse = true

    private val removedConcepts = mutableSetOf<Uuid>()

    private val removedConnectionsFrom = mutableMapOf<Uuid, MutableSet<IConnection<*>>>()

    private val removedConnectionsTo = mutableMapOf<Uuid, MutableSet<IConnection<*>>>()

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) {

        require(isAvailableForUse)

        val conceptUuid = concept.id.uuid

        require(!data.concepts.containsKey(conceptUuid) && !addedConcepts.containsKey(conceptUuid))

        addedConcepts[conceptUuid] = concept
        removedConcepts.remove(conceptUuid)

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) {

        require(isAvailableForUse)

        val fromConceptUuid = connection.fromConcept.id.uuid
        val toConceptUuid = connection.toConcept.id.uuid

        addedConnectionsFrom.getOrPut(fromConceptUuid) { mutableSetOf() }.add(connection)
        addedConnectionsTo.getOrPut(toConceptUuid) { mutableSetOf() }.add(connection)

        removedConnectionsFrom[fromConceptUuid]?.remove(connection)
        removedConnectionsTo[toConceptUuid]?.remove(connection)

    }

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E) {

        require(isAvailableForUse)

        val conceptAUuid = connection.conceptA.id.uuid
        val conceptBUuid = connection.conceptB.id.uuid

        addedConnectionsFrom.getOrPut(conceptAUuid) { mutableSetOf() }.add(connection)
        addedConnectionsTo.getOrPut(conceptAUuid) { mutableSetOf() }.add(connection)
        addedConnectionsFrom.getOrPut(conceptBUuid) { mutableSetOf() }.add(connection)
        addedConnectionsTo.getOrPut(conceptBUuid) { mutableSetOf() }.add(connection)

        removedConnectionsFrom[conceptAUuid]?.remove(connection)
        removedConnectionsTo[conceptAUuid]?.remove(connection)
        removedConnectionsFrom[conceptBUuid]?.remove(connection)
        removedConnectionsTo[conceptBUuid]?.remove(connection)

    }

    override fun beginTransaction(): IWriteableGraph {
        throw IllegalStateException("Already in a transaction.")
    }

    override fun commit(): IGraph {

        require(isAvailableForUse)

        data.concepts.putAll(addedConcepts)
        removedConcepts.forEach { uuid -> data.concepts.remove(uuid) }

        data.connectionsFrom.putAll(addedConnectionsFrom)
        removedConnectionsFrom.forEach { (uuid, connections) -> data.connectionsFrom[uuid]?.removeAll(connections) }

        data.connectionsTo.putAll(addedConnectionsTo)
        removedConnectionsTo.forEach { (uuid, connections) -> data.connectionsFrom[uuid]?.removeAll(connections) }

        addedConcepts.clear()
        removedConcepts.clear()

        isAvailableForUse = false

        return Graph(data)

    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {

        require(isAvailableForUse)

        val conceptUuid = conceptId.uuid

        if (removedConcepts.contains(conceptUuid)) {
            return null
        }

        return addedConcepts[conceptUuid] as V? ?: data.concepts[conceptId.uuid] as V?

    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Iterable<IConnection<*>> {

        require(isAvailableForUse)

        val result = HashSet<IConnection<*>>(data.connectionsTo[conceptId.uuid] ?: listOf())
        result.addAll(data.connectionsFrom[conceptId.uuid] ?: listOf())

        result.addAll(addedConnectionsFrom[conceptId.uuid] ?: listOf())
        result.addAll(addedConnectionsTo[conceptId.uuid] ?: listOf())

        result.removeAll(removedConnectionsFrom[conceptId.uuid] ?: listOf())
        result.removeAll(removedConnectionsTo[conceptId.uuid] ?: listOf())

        return result
    }

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) {

        require(isAvailableForUse)

        val conceptUuid = conceptId.uuid

        addedConcepts.remove(conceptUuid)
        if (data.concepts.containsKey(conceptUuid)) {
            removedConcepts.add(conceptUuid)
        }

        removedConnectionsFrom.remove(conceptUuid)
        removedConnectionsTo.remove(conceptUuid)

    }

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> removeConnection(connection: E) {
        require(isAvailableForUse)

        TODO("TBD")
    }

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> removeConnection(connection: E) {
        require(isAvailableForUse)

        TODO("TBD")
    }

    override fun rollBack(): IGraph {

        require(isAvailableForUse)

        return Graph(data)

    }

    override fun <V : IConcept<V>> updateConcept(concept: V) {

        require(isAvailableForUse)

        val conceptUuid = concept.id.uuid

        require(data.concepts.containsKey(conceptUuid) || addedConcepts.containsKey(conceptUuid))
        require(!removedConcepts.contains(conceptUuid))

        addedConcepts[conceptUuid] = concept
        removedConcepts.remove(conceptUuid)

    }

}

//---------------------------------------------------------------------------------------------------------------------

