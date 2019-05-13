//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * A directed connection (edge or link) from one concept (vertex or node) to another in a property graph.
 */
interface IDirectedConnection<Connection, FromConcept : IConcept<FromConcept>, ToConcept: IConcept<ToConcept>>
    : IConnection<Connection> {

    /** The concept at the tails of the connection. */
    val fromConceptId: Id<FromConcept>

    /** The concept at the head of the connection. */
    val toConceptId: Id<ToConcept>

}

//---------------------------------------------------------------------------------------------------------------------
