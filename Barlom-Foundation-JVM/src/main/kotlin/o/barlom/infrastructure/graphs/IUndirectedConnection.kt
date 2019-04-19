//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

interface IUndirectedConnection<Connection, Concept>
    : IConnection<Connection> {

    val conceptA: Concept

    val conceptB: Concept

}

//---------------------------------------------------------------------------------------------------------------------
