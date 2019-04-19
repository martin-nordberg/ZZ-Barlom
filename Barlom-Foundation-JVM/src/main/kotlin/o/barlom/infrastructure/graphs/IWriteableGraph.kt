//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

interface IWriteableGraph
    : IGraph {

    fun <V : IConcept<V>> addConcept(concept: V)

    fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> addConnection(connection: E)

    fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> addConnection(connection: E)

    fun commit(): IGraph

    fun <V : IConcept<V>> removeConcept(conceptId: Id<V>)

    fun <E : IDirectedConnection<E, V1, V2>, V1 : IConcept<V1>, V2 : IConcept<V2>> removeConnection(connection: E)

    fun <E : IUndirectedConnection<E, V>, V : IConcept<V>> removeConnection(connection: E)

    fun rollBack(): IGraph

    fun <V : IConcept<V>> updateConcept(concept: V)

}

//---------------------------------------------------------------------------------------------------------------------
