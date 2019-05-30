//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

import i.barlom.infrastructure.graphs.GraphWrapper

//---------------------------------------------------------------------------------------------------------------------

/**
 * A graph consisting of concepts (vertices or nodes) linked by connections (edges or links).
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
     * @return an independent deep copy of this graph.
     * Note: concept and connection objects are assumed to be immutable and are not cloned, the clone duplicates
     * their membership in the new graph.
     */
    fun clone(): IGraph

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
    fun isEmpty(): Boolean

    /**
     * @return true if this graph contains at least one concept.
     */
    fun isNotEmpty(): Boolean

    /**
     * Establishes a new graph that is writable, starting from the current state of this read-only graph.
     * Only one such writable graph can ever be created from this starting point.
     * @return the new writable graph
     */
    fun startWriting(): IWritableGraph

    /**
     * Marks this graph as no longer in use. Allows coalescence with the writeable graph that has been
     * constructed with this graph as its starting point.
     */
    fun stopReading()

    /**
     * Takes this graph through one standard update cycle via a single callback.
     */
    fun update(edit: IWritableGraph.() -> Unit): IGraph {
        val g = this.startWriting()
        g.edit()
        this.stopReading()
        return g.stopWriting()
    }

}

//---------------------------------------------------------------------------------------------------------------------

/** Creates an empty graph. */
fun graphOf(): IGraph =
    GraphWrapper()

//---------------------------------------------------------------------------------------------------------------------
