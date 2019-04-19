//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

interface IGraph {

    fun beginTransaction(): IWriteableGraph

    fun <V : IConcept<V>> concept(conceptId: Id<V>): V?

    fun <V : IConcept<V>> connections(conceptId: Id<V>): Iterable<IConnection<*>>

}

//---------------------------------------------------------------------------------------------------------------------
