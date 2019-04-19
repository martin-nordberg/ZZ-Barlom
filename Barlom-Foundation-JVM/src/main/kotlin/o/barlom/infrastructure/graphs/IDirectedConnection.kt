//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

interface IDirectedConnection<Connection, FromConcept, ToConcept>
    : IConnection<Connection> {

    val fromConcept: FromConcept

    val toConcept: ToConcept

}

//---------------------------------------------------------------------------------------------------------------------
