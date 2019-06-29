//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a connection.
 */
@Suppress("unused")
data class ConnectionTypeId<Connection : IConnection<Connection>>(

    /** The name of this connection type. */
    val typeName: String

)

//---------------------------------------------------------------------------------------------------------------------
