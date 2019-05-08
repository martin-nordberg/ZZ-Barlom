//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

import i.barlom.infrastructure.graphs.GraphWrapper

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a property graph consisting of concepts linked by connections.
 */
interface IGraph {

    /** Whether this graph retains a link to an earlier version that is still being read. */
    val hasPredecessor: Boolean

    /** Whether this graph is still available for reading. (True until stopReading() has been called.) */
    val isReadable: Boolean

    /** The number of concepts in the graph. */
    val numConcepts: Int

    /** The number of connections in the graph. */
    val numConnections: Int

    ////

    /**
     * @return the concept in this graph with given ID [conceptId].
     */
    fun <V : IConcept<V>> concept(conceptId: Id<V>): V?

    /**
     * @return the connection in this graph with given ID [connectionId].
     */
    fun <E : IConnection<E>> connection(connectionId: Id<E>): E?

    /**
     * @return the connections linked to the concept with given ID [conceptId].
     */
    fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>>

    /**
     * @return true if the graph contains the given [concept].
     */
    fun <V : IConcept<V>> containsConcept(concept: V): Boolean =
        concept === concept(concept.id)

    /**
     * @return true if the graph contains a concept with given ID [conceptId]].
     */
    fun <V : IConcept<V>> containsConceptWithId(conceptId: Id<V>): Boolean

    /**
     * @return true if the graph contains the given [connection].
     */
    fun <E : IConnection<E>> containsConnection(connection: E): Boolean =
        connection === connection(connection.id)

    /**
     * @return true if the graph contains a connection with given ID [connectionId]].
     */
    fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean

    /**
     * @return true if this graph contains no concepts (or connections).
     */
    fun isEmpty() : Boolean

    /**
     * @return true if this graph contains at least one concept.
     */
    fun isNotEmpty() : Boolean

    /**
     * Establishes a new graph that is writeable, starting from the current state of this read-only graph.
     * Only one such writeable graph can ever be created from this starting point.
     * @return the new writeable graph
     */
    fun startWriting(): IWritableGraph

    /**
     * Marks this graph as no longer in use. Allows coalescence with the writeable graph that has been
     * constructed with this graph as its starting point.
     */
    fun stopReading()

}

//---------------------------------------------------------------------------------------------------------------------

fun graphOf(): IGraph =
    GraphWrapper()

//---------------------------------------------------------------------------------------------------------------------
