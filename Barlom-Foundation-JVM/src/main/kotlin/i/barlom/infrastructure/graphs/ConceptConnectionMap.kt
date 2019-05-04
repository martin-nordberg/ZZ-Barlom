//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Defines a map where connections are mapped by concept UUID plus their own UUID.
 */
class ConceptConnectionMap {

    /** The ordinary inner map doing all the work. */
    private val connectionsByConcept = HashMap<Uuid, ConnectionMap>()

    ////

    operator fun get(conceptUuid: Uuid) : Set<IConnection<*>> =
        connectionsByConcept[conceptUuid] ?: ConnectionMap()

    fun getMap(conceptUuid: Uuid) : ConnectionMap =
        connectionsByConcept[conceptUuid] ?: ConnectionMap()

    fun put(conceptUuid: Uuid, connection: IConnection<*>) {
        connectionsByConcept.getOrPut(conceptUuid) { ConnectionMap() }.put(connection)
    }

    fun putAll(conceptUuid: Uuid, addedConnections: ConnectionMap) {

        addedConnections.forEach { connection ->
            put( conceptUuid, connection)
        }

    }

    fun putAll(addedConnections: ConceptConnectionMap) {

        addedConnections.connectionsByConcept.forEach { (conceptUuid, connections) ->
            putAll(conceptUuid,connections)
        }

    }

    fun remove(conceptUuid: Uuid, connectionUuid: Uuid) =
        connectionsByConcept[conceptUuid]?.remove(connectionUuid)

    fun removeAll(removedConnections: ConceptConnectionMap) {

        removedConnections.connectionsByConcept.forEach { (uuid, connections) ->
            connections.forEach { connection ->
                connectionsByConcept[uuid]?.remove(connection.id.uuid)
            }
        }

    }

    fun removeConcept(conceptUuid: Uuid) {
        connectionsByConcept.remove(conceptUuid)
    }

}

//---------------------------------------------------------------------------------------------------------------------

