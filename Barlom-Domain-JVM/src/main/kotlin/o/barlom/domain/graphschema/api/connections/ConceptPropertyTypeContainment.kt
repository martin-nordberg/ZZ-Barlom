//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptPropertyType
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.ConnectionType
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent concept type to contained child concept property type.
 */
data class ConceptPropertyTypeContainment(
    override val uuid: Uuid,
    override val fromConceptId: Id<ConceptType>,
    override val toConceptId: Id<ConceptPropertyType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractContainment<ConceptPropertyTypeContainment, ConceptType, ConceptPropertyType>() {

    val childConceptPropertyTypeId
        get() = toConceptId

    val parentConceptTypeId
        get() = fromConceptId

    override val type = TYPE

    ////

    companion object {
        val TYPE = ConnectionType<ConceptPropertyTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.ConceptPropertyTypeContainment"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

