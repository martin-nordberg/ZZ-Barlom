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

    /** The name of the concrete connection type Connection. */
    val connectionTypeName: String

    /** The unique ID of the connection. */
    val id: Id<Connection>

}

//---------------------------------------------------------------------------------------------------------------------
