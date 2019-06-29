//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.ELabelDefaulting
import o.barlom.domain.graphschema.api.types.EOptionality
import o.barlom.infrastructure.graphs.ConceptTypeId
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a concept property.
 */
data class ConceptPropertyType(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    override val optionality: EOptionality = EOptionality.OPTIONAL,
    val labelDefaulting: ELabelDefaulting = ELabelDefaulting.NOT_DEFAULT_LABEL
) : AbstractPropertyType<ConceptPropertyType>() {

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "labelDefaulting" -> labelDefaulting
            else              -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "labelDefaulting" -> true
            else              -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("labelDefaulting")

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConceptPropertyType>(
            "o.barlom.domain.graphschema.api.concepts.ConceptPropertyType"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

