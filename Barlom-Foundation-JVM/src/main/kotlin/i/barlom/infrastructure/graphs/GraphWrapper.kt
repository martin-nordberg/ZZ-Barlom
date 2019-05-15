//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Wrapper for a graph that provides the means to initiate new editions then coalesce them once old editions are
 * no longer in use.
 */
internal class GraphWrapper(

    /** A predecessor graph that serves as the read-only starting point for changes written to this graph. */
    initiatingGraph: GraphWrapper? = null

) : IWritableGraph {

    /** The graph wrapped by this wrapper. */
    private var innerGraph: IGraphImpl =
        if (initiatingGraph == null) ReadableGraph() else WritableGraph(initiatingGraph.innerGraph)

    /** Whether the wrapped graph remains readable through this wrapper. */
    private var isStillReading: Boolean = true

    /** The prior edition of the graph. */
    private var predecessorGraph: GraphWrapper? = initiatingGraph

    /** The succeeding edition of the graph. */
    private var successorGraph: GraphWrapper? = null

    ////

    override val hasPredecessor
        get() = predecessorGraph != null

    override val isReadable
        get() = isStillReading

    override val isWritable
        get() = innerGraph.isWritable

    override val numConcepts
        get() = innerGraph.numConcepts

    override val numConnections
        get() = innerGraph.numConnections

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) =
        innerGraph.addConcept(concept)

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) =
        innerGraph.addConnection(connection)

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E) =
        innerGraph.addConnection(connection)

    /**
     * Combines this written but no longer writable graph with its no longer readable predecessor so that the
     * wrapped graph no longer has a predecessor.
     */
    private fun coalesceWithPredecessor() {

        val p = predecessorGraph!!

        require(!isWritable && p.predecessorGraph == null)

        (innerGraph as WritableGraph).coalesceIntoPredecessor(
            p.innerGraph as ReadableGraph
        )

        innerGraph = p.innerGraph
        predecessorGraph = null

        if (!isReadable && successorGraph != null) {
            successorGraph!!.coalesceWithPredecessor()
        }

    }

    override fun clone(): IGraph {
        require(isReadable)
        require(!hasPredecessor)

        val result = GraphWrapper(null)
        result.innerGraph = innerGraph.clone() as IGraphImpl
        return result
    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {
        require(isReadable)
        return innerGraph.concept(conceptId)
    }

    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? {
        require(isReadable)
        return innerGraph.connection(connectionId)
    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> {
        require(isReadable)
        return innerGraph.connections(conceptId)
    }

    override fun <V : IConcept<V>> containsConceptWithId(conceptId: Id<V>): Boolean {
        require(isReadable)
        return innerGraph.containsConceptWithId(conceptId)
    }

    override fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean {
        require(isReadable)
        return innerGraph.containsConnectionWithId(connectionId)
    }

    override fun isEmpty(): Boolean {
        require(isReadable)
        return innerGraph.isEmpty()
    }

    override fun isNotEmpty(): Boolean {
        require(isReadable)
        return innerGraph.isNotEmpty()
    }

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) =
        innerGraph.removeConcept(conceptId)

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) =
        innerGraph.removeConnection(connectionId)

    override fun startWriting(): IWritableGraph {

        require(isReadable)
        require(!isWritable)
        require(successorGraph == null)

        val result = GraphWrapper(this)

        successorGraph = result

        return result

    }

    override fun stopReading() {

        require(isReadable)

        isStillReading = false

        if (successorGraph?.isWritable == false && predecessorGraph == null) {
            successorGraph?.coalesceWithPredecessor()
        }

    }

    override fun stopWriting(): IGraph {

        innerGraph.stopWriting()

        if (predecessorGraph?.isReadable == false && predecessorGraph?.predecessorGraph == null) {
            coalesceWithPredecessor()
        }

        return this

    }

    override fun <V : IConcept<V>> updateConcept(concept: V) =
        innerGraph.updateConcept(concept)

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> updateConnection(connection: E) =
        innerGraph.updateConnection(connection)

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> updateConnection(connection: E) =
        innerGraph.updateConnection(connection)

}

//---------------------------------------------------------------------------------------------------------------------

