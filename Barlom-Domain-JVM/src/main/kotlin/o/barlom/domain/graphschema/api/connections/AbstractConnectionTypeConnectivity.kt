//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractConnectionType
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a connection representing connectivity between connection type and concept type.
 */
abstract class AbstractConnectionTypeConnectivity<
    Connection,
    ConnectionType : AbstractConnectionType<ConnectionType>
    > : IDirectedPropertyConnection<Connection, ConnectionType, ConceptType> {

    val conceptTypeId
        get() = toConceptId

    val connectionTypeId
        get() = fromConceptId

}

//---------------------------------------------------------------------------------------------------------------------

