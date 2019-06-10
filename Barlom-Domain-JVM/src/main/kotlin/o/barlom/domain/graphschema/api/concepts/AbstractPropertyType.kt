//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EOptionality

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a property.
 */
abstract class AbstractPropertyType<ConnectionType : AbstractPropertyType<ConnectionType>>
    : AbstractPackagedConcept<ConnectionType>() {

    /** Whether this property is required or optional. */
    abstract val optionality: EOptionality

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "optionality" -> optionality
            else          -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "optionality" -> true
            else          -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("optionality")

}

//---------------------------------------------------------------------------------------------------------------------

