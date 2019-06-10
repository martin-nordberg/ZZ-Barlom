//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from directed connection type to the concept type at its tail.
 */
data class DirectedConnectionTypeTailConnectivity(
    override val id: Id<DirectedConnectionTypeTailConnectivity>,
    override val fromConceptId: Id<DirectedConnectionType>,
    override val toConceptId: Id<ConceptType>
) : AbstractConnectionTypeConnectivity<DirectedConnectionTypeTailConnectivity, DirectedConnectionType>() {

    val directedConnectionTypeId
        get() = fromConceptId

}

//---------------------------------------------------------------------------------------------------------------------

