//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConnectionPropertyType
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.ConnectionType
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent undirected connection type to contained child connection property type.
 */
data class UndirectedConnectionPropertyTypeContainment(
    override val uuid: Uuid,
    override val fromConceptId: Id<UndirectedConnectionType>,
    override val toConceptId: Id<ConnectionPropertyType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractConnectionPropertyTypeContainment<UndirectedConnectionPropertyTypeContainment, UndirectedConnectionType>() {

    val parentUndirectedConnectionTypeId
        get() = fromConceptId

    override val type = TYPE

    ////

    companion object {
        val TYPE = ConnectionType<UndirectedConnectionPropertyTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.UndirectedConnectionPropertyTypeContainment"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

