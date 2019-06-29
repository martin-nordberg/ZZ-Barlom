//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EOptionality
import o.barlom.infrastructure.graphs.ConceptTypeId
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a concept property.
 */
data class ConnectionPropertyType(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    override val optionality: EOptionality = EOptionality.OPTIONAL
) : AbstractPropertyType<ConnectionPropertyType>() {

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConnectionPropertyType>(
            "o.barlom.domain.graphschema.api.concepts.ConnectionPropertyType"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

