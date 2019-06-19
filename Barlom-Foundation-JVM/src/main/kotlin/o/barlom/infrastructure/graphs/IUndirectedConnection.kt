//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * An undirected connection (edge or link) from one concept (vertex or node) to another in a graph.
 */
interface IUndirectedConnection<Connection: IUndirectedConnection<Connection,Concept>, Concept: IConcept<Concept>>
    : IConnection<Connection> {

    /** The first concept connected by this connection. */
    val conceptIdA: Id<Concept>

    /** The second concept connected by this connection. */
    val conceptIdB: Id<Concept>

}

//---------------------------------------------------------------------------------------------------------------------
