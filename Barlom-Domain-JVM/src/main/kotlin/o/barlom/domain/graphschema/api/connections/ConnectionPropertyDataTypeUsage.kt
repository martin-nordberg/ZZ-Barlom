//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConnectionPropertyType
import o.barlom.domain.graphschema.api.concepts.ConstrainedDataType
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from a connection property type to its data type.
 */
data class ConnectionPropertyDataTypeUsage(
    override val uuid: Uuid,
    override val fromConceptId: Id<ConnectionPropertyType>,
    override val toConceptId: Id<ConstrainedDataType>
) : IDirectedPropertyConnection<ConnectionPropertyDataTypeUsage, ConnectionPropertyType, ConstrainedDataType> {

    val connectionPropertyTypeId
        get() = fromConceptId

    val dataTypeId
        get() = toConceptId

    override val typeName = "ConnectionPropertyDateTypeUsage"

}

//---------------------------------------------------------------------------------------------------------------------

