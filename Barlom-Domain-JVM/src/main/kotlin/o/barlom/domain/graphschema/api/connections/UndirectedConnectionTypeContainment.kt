//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.ConnectionType
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent package to contained child undirected connection type.
 */
data class UndirectedConnectionTypeContainment(
    override val uuid: Uuid,
    override val fromConceptId: Id<Package>,
    override val toConceptId: Id<UndirectedConnectionType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractConnectionTypeContainment<UndirectedConnectionTypeContainment, UndirectedConnectionType>() {

    val childUndirectedConnectionTypeId
        get() = toConceptId

    override val type = TYPE

    ////

    companion object {
        val TYPE = ConnectionType<UndirectedConnectionTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.UndirectedConnectionTypeContainment"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

