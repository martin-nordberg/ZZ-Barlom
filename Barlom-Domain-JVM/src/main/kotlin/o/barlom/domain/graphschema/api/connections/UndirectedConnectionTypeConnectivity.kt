//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from undirected connection type to concept type.
 */
data class UndirectedConnectionTypeConnectivity(
    override val uuid: Uuid,
    override val fromConceptId: Id<UndirectedConnectionType>,
    override val toConceptId: Id<ConceptType>
) : AbstractConnectionTypeConnectivity<UndirectedConnectionTypeConnectivity, UndirectedConnectionType>() {

    val undirectedConnectionTypeId
        get() = fromConceptId

}

//---------------------------------------------------------------------------------------------------------------------

