//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

class Graph(

    private val data: GraphData = GraphData()

) : IGraph {

    var isAvailableForUse = true

    override fun beginTransaction(): IWriteableGraph {
        isAvailableForUse = false
        return WriteableGraph(data)
    }

    override fun <V : IConcept<V>> concept(conceptId: Id<V>): V? {
        require(isAvailableForUse)
        return data.concepts[conceptId.uuid] as V?
    }

    override fun <V : IConcept<V>> connections(conceptId: Id<V>): Iterable<IConnection<*>> {
        require(isAvailableForUse)

        val result = HashSet<IConnection<*>>(data.connectionsTo[conceptId.uuid] ?: listOf())
        result.addAll(data.connectionsFrom[conceptId.uuid] ?: listOf())

        return result
    }

}

//---------------------------------------------------------------------------------------------------------------------

fun graphOf(): IGraph =
    Graph()

//---------------------------------------------------------------------------------------------------------------------

