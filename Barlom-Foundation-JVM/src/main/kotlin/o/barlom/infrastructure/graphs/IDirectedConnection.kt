//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

interface IDirectedConnection<Connection, FromConcept : IConcept<FromConcept>, ToConcept: IConcept<ToConcept>>
    : IConnection<Connection> {

    val fromConceptId: Id<FromConcept>

    val toConceptId: Id<ToConcept>

}

//---------------------------------------------------------------------------------------------------------------------
