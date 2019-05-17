//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * A graph that is undergoing change.
 */
interface IWritableGraph
    : IGraph {

    /** Whether this graph is still available for writing. (True until stopWriting() has been called.) */
    val isWritable: Boolean

    ////

    /**
     * Adds a [concept] to the graph.
     */
    fun <V : IConcept<V>> addConcept(concept: V)

    /**
     * Adds a directed [connection] to the graph. The connected concepts must have already been added to the graph.
     */
    fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E)

    /**
     * Adds an undirected [connection] to the graph. The connected concepts must have already been added to the graph.
     */
    fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E)

    /**
     * Removes the given [concept] and all its connections from this graph.
     */
    fun <V : IConcept<V>> removeConcept(concept: V) =
        removeConcept(concept.id)

    /**
     * Removes the concept with given ID [conceptId] from this graph along with all its connections.
     */
    fun <V : IConcept<V>> removeConcept(conceptId: Id<V>)

    /**
     * Removes the connection with given [connectionId] from this graph.
     */
    fun <E : IConnection<E>> removeConnection(connectionId: Id<E>)

    /**
     * Returns a read-only graph consolidating all the changes made to this writable graph.
     */
    fun stopWriting(): IGraph

    /**
     * Replaces a concept already in the graph with an updated edition, [concept].
     */
    fun <V : IConcept<V>> updateConcept(concept: V)

    /**
     * Replaces a directed connection already in the graph with an updated edition, [connection].
     */
    fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E)

    /**
     * Replaces an undirected connection already in the graph with an updated edition, [connection].
     */
    fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> updateConnection(connection: E)

}

//---------------------------------------------------------------------------------------------------------------------
