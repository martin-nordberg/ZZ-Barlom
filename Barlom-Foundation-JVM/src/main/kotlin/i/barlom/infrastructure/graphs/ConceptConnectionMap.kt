//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Defines a map where connections are mapped by concept UUID plus connection UUID. The connection UUID always
 * comes from the connection.
 */
internal class ConceptConnectionMap {

    /** The ordinary inner map doing all the work. */
    private val connectionsByConcept = HashMap<Uuid, ConnectionMap>()

    ////

    /** @return the connections for given concept UUID [conceptUuid]. */
    operator fun get(conceptUuid: Uuid): Set<IConnection<*>> =
        connectionsByConcept[conceptUuid] ?: ConnectionMap()

    /** @return the map of connections for given concept UUID [conceptUuid]. */
    fun getMap(conceptUuid: Uuid): ConnectionMap =
        connectionsByConcept[conceptUuid] ?: ConnectionMap()

    /** Adds a [connection] that is linked to concept with UUID [conceptUuid] to this map. */
    fun put(conceptUuid: Uuid, connection: IConnection<*>) =
        connectionsByConcept.getOrPut(conceptUuid) { ConnectionMap() }.put(connection)

    /** Merges in a map of connections for the concept with given UUID [conceptUuid]. */
    fun putAll(conceptUuid: Uuid, addedConnections: ConnectionMap) =
        addedConnections.forEach { connection ->
            put(conceptUuid, connection)
        }

    /** Merges into this map another entire concept/conception map, [addedConnections]. */
    fun putAll(addedConnections: ConceptConnectionMap) =
        addedConnections.connectionsByConcept.forEach { (conceptUuid, connections) ->
            putAll(conceptUuid, connections)
        }

    /** Removes from this map a connection with UUID [connectionUuid] linked to concept with UUID [conceptUuid]. */
    fun remove(conceptUuid: Uuid, connectionUuid: Uuid) =
        connectionsByConcept[conceptUuid]?.remove(connectionUuid)

    /** Removes from this map another entire concept/conception map, [removedConnections]. */
    fun removeAll(removedConnections: ConceptConnectionMap) =
        removedConnections.connectionsByConcept.forEach { (conceptUuid, connections) ->
            connections.forEach { connection ->
                connectionsByConcept[conceptUuid]?.remove(connection.id.uuid)
            }
        }

    /** Removes from this map the concept with UUID [conceptUuid] along with all its connections. */
    fun removeConcept(conceptUuid: Uuid) =
        connectionsByConcept.remove(conceptUuid)

}

//---------------------------------------------------------------------------------------------------------------------

