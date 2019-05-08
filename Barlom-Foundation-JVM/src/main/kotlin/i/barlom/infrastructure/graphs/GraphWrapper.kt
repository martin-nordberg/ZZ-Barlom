//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

internal class GraphWrapper(

    initiatingGraph: GraphWrapper? = null

) : IWriteableGraph {

    private var innerGraph: IGraphImpl =
        if ( initiatingGraph == null ) ReadableGraph() else WriteableGraph(initiatingGraph.innerGraph )

    private var predecessorGraph: GraphWrapper? = initiatingGraph

    private var successorGraph: GraphWrapper? = null

    ////

    override val isReadable: Boolean
        get() = innerGraph.isReadable

    override val isWriteable: Boolean
        get() = innerGraph.isWriteable

    override val numConcepts: Int
        get() = innerGraph.numConcepts

    override val numConnections: Int
        get() = innerGraph.numConnections

    ////

    override fun <V : IConcept<V>> addConcept(concept: V) =
        innerGraph.addConcept(concept)

    override fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E) =
        innerGraph.addConnection(connection)

    override fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E) =
        innerGraph.addConnection(connection)

    private fun coalesceWithPredecessor() {

        val p = predecessorGraph!!

        require(!isWriteable && p.predecessorGraph == null)

        (innerGraph as WriteableGraph).coalesceIntoPredecessor(
            p.innerGraph as ReadableGraph
        )

        innerGraph = p.innerGraph
        predecessorGraph = null

    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? =
        innerGraph.concept(conceptId)

    override fun <E : IConnection<E>> connection(connectionId: Id<E>): E? =
        innerGraph.connection(connectionId)

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Set<IConnection<*>> =
        innerGraph.connections(conceptId)

    override fun <V : IConcept<V>> containsConceptWithId(conceptId: Id<V>): Boolean =
        innerGraph.containsConceptWithId(conceptId)

    override fun <E : IConnection<E>> containsConnectionWithId(connectionId: Id<E>): Boolean =
        innerGraph.containsConnectionWithId(connectionId)

    override fun isEmpty() =
        innerGraph.isEmpty()

    override fun isNotEmpty() =
        innerGraph.isNotEmpty()

    override fun <V : IConcept<V>> removeConcept(conceptId: Id<V>) =
        innerGraph.removeConcept(conceptId)

    override fun <E : IConnection<E>> removeConnection(connectionId: Id<E>) =
        innerGraph.removeConnection(connectionId)

    override fun startWriting(): IWriteableGraph {

        require(isReadable)
        require(successorGraph == null)

        val result = GraphWrapper(this)

        successorGraph = result

        return result

    }

    override fun stopReading() {

        innerGraph.stopReading()

        if (successorGraph?.isWriteable == false && predecessorGraph == null) {
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

