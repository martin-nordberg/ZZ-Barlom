//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConnectionPropertyType
import o.barlom.domain.graphschema.api.concepts.ConstrainedBoolean
import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from a connection property type to its boolean data type.
 */
data class ConnectionPropertyBooleanUsage(
    override val uuid: Uuid,
    override val fromConceptId: Id<ConnectionPropertyType>,
    override val toConceptId: Id<ConstrainedBoolean>
) : IDirectedPropertyConnection<ConnectionPropertyBooleanUsage, ConnectionPropertyType, ConstrainedBoolean> {

    val connectionPropertyTypeId
        get() = fromConceptId

    val dataTypeId
        get() = toConceptId

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConnectionTypeId<ConnectionPropertyBooleanUsage>(
            "o.barlom.domain.graphschema.api.connections.ConnectionPropertyBooleanUsage"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

