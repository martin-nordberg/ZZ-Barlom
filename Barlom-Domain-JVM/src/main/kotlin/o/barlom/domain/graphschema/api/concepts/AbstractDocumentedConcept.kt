//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.infrastructure.propertygraphs.IPropertyConcept

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a documented concept - any concept with a description.
 */
abstract class AbstractDocumentedConcept<Concept> internal constructor()
    : IPropertyConcept<Concept> {

    /** A description of this concept. */
    abstract var description: String

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "description" -> description
            else          -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "description" -> true
            else          -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("description")

    override fun setString(propertyName: String, value: String) =
        when (propertyName) {
            "description" -> description = value
            else          -> super.setString(propertyName, value)
        }

}

//---------------------------------------------------------------------------------------------------------------------

