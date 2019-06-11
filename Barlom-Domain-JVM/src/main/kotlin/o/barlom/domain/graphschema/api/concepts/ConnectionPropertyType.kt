//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EOptionality
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
) : AbstractPropertyType<ConnectionPropertyType>()

//---------------------------------------------------------------------------------------------------------------------

