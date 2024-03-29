//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.IConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Defines a map where connections are mapped by their own UUID.
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
internal class ConnectionMap
    : Set<IConnection<*>> {

    /** The ordinary inner map doing all the work. */
    private val connectionsByUuid = HashMap<Uuid, IConnection<*>>()

    /** A redundant inner map of maps for faster queries when connection type is fixed. */
    private val connectionsByTypeAndUuid = HashMap<ConnectionTypeId<*>, HashMap<Uuid, IConnection<*>>>()

    ////

    /** The number of connections mapped. */
    override val size: Int
        get() = connectionsByUuid.size

    ////

    /**
     * @return whether this connection map contains the given [connection].
     */
    override fun contains(connection: IConnection<*>) =
        connectionsByTypeAndUuid[connection.typeId]?.get(connection.id.uuid) === connection

    /**
     * @return whether al of the given [connections] are contained in this map.
     */
    override fun containsAll(connections: Collection<IConnection<*>>): Boolean =
        connections.find { connection -> !contains(connection) } == null

    /**
     * @return whether the map has an entry for the given UUID [connectionUuid].
     */
    fun containsUuid(connectionUuid: Uuid): Boolean =
        connectionsByUuid.containsKey(connectionUuid)

    /**
     * @return the connection with given UUID [connectionUuid] or null if there is none.
     */
    operator fun get(connectionUuid: Uuid): IConnection<*>? =
        connectionsByUuid[connectionUuid]

    /**
     * @return all the connections of given type.
     */
    @Suppress("UNCHECKED_CAST")
    fun <E : IConnection<E>> getByType(type: ConnectionTypeId<E>): Collection<E> {

        val connectionsByType = connectionsByTypeAndUuid[type]

        if (connectionsByType != null) {
            return connectionsByType.values as Collection<E>
        }

        return setOf()

    }

    /**
     * @return true if there are no connections in this map.
     */
    override fun isEmpty() =
        connectionsByUuid.isEmpty()

    /**
     * @return true if there are no connections in this map.
     */
    fun isNotEmpty() =
        !isEmpty()

    /**
     * @return an iterator over the connections in this map.
     */
    override fun iterator(): Iterator<IConnection<*>> =
        connectionsByUuid.values.iterator()

    /**
     * Puts the given [connection] into this map, mapped by its own UUID. Replaces any connection with the same UUID.
     */
    fun put(connection: IConnection<*>) {
        connectionsByUuid[connection.id.uuid] = connection
        connectionsByTypeAndUuid.getOrPut(connection.typeId) { HashMap() }[connection.id.uuid] = connection
    }

    /**
     * Copies connections from another map [addedConnections] into this one.
     */
    fun putAll(addedConnections: ConnectionMap) {

        for (connection in addedConnections) {
            put(connection)
        }

    }

    /**
     * Removes the connection with UUID [connectionUuid] from this map.
     * @return the removed connection or null if there is no connection with the given UUID.
     */
    fun remove(connectionUuid: Uuid): IConnection<*>? {

        val result = connectionsByUuid.remove(connectionUuid)

        if (result != null) {
            val connectionsByType = connectionsByTypeAndUuid[result.typeId]!!
            connectionsByType.remove(connectionUuid)
            if (connectionsByType.isEmpty()) {
                connectionsByTypeAndUuid.remove(result.typeId)
            }
        }

        return result

    }

}

//---------------------------------------------------------------------------------------------------------------------

