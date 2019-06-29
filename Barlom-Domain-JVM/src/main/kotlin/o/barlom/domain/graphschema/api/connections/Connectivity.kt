//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractConnectionType
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from connection type to a concept type it connects.
 */
data class Connectivity<
    ConnectionType : AbstractConnectionType<ConnectionType>
    >(
    override val typeId: ConnectionTypeId<Connectivity<ConnectionType>>,
    override val uuid: Uuid,
    override val fromConceptId: Id<ConnectionType>,
    override val toConceptId: Id<ConceptType>
) : IDirectedPropertyConnection<Connectivity<ConnectionType>, ConnectionType, ConceptType> {

    val conceptTypeId
        get() = toConceptId

    val connectionTypeId
        get() = fromConceptId

    ////

    init {
        require(TYPE_NAMES.contains(typeId.typeName))
    }

    ////

    companion object {

        val DIRECTED_CONNECTION_TYPE_HEAD_CONNECTIVITY_TYPE = ConnectionTypeId<DirectedConnectionTypeHeadConnectivity>(
            "o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeHeadConnectivity"
        )

        val DIRECTED_CONNECTION_TYPE_TAIL_CONNECTIVITY_TYPE = ConnectionTypeId<DirectedConnectionTypeTailConnectivity>(
            "o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeTailConnectivity"
        )

        val UNDIRECTED_CONNECTION_TYPE_CONNECTIVITY_TYPE = ConnectionTypeId<UndirectedConnectionTypeConnectivity>(
            "o.barlom.domain.graphschema.api.connections.UndirectedConnectionTypeConnectivity"
        )

        private val TYPE_NAMES = setOf(
            DIRECTED_CONNECTION_TYPE_HEAD_CONNECTIVITY_TYPE.typeName,
            DIRECTED_CONNECTION_TYPE_TAIL_CONNECTIVITY_TYPE.typeName,
            UNDIRECTED_CONNECTION_TYPE_CONNECTIVITY_TYPE.typeName
        )

    }

}

//---------------------------------------------------------------------------------------------------------------------

typealias DirectedConnectionTypeHeadConnectivity = Connectivity<DirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

typealias DirectedConnectionTypeTailConnectivity = Connectivity<DirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

typealias UndirectedConnectionTypeConnectivity = Connectivity<UndirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

