//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection (edge or link) in a property graph. Provides the unique ID of the connection.
 */
interface IConnection<Connection> {

    /** The unique ID of the connection. */
    val id: Id<Connection>

}

//---------------------------------------------------------------------------------------------------------------------
