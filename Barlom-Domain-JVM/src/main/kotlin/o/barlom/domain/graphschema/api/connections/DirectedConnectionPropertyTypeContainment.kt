//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConnectionPropertyType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.ConnectionType
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent directed connection type to contained child connection property type.
 */
data class DirectedConnectionPropertyTypeContainment(
    override val uuid: Uuid,
    override val fromConceptId: Id<DirectedConnectionType>,
    override val toConceptId: Id<ConnectionPropertyType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractConnectionPropertyTypeContainment<DirectedConnectionPropertyTypeContainment, DirectedConnectionType>() {

    val parentDirectedConnectionTypeId
        get() = fromConceptId

    override val type = TYPE

    ////

    companion object {
        val TYPE = ConnectionType<DirectedConnectionPropertyTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.DirectedConnectionPropertyTypeContainment"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

