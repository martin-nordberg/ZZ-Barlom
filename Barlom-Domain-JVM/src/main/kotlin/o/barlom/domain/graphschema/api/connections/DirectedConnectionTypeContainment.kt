//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent package to contained child directed connection type.
 */
data class DirectedConnectionTypeContainment(
    override val uuid: Uuid,
    override val fromConceptId: Id<Package>,
    override val toConceptId: Id<DirectedConnectionType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractConnectionTypeContainment<DirectedConnectionTypeContainment, DirectedConnectionType>() {

    val childDirectedConnectionTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

