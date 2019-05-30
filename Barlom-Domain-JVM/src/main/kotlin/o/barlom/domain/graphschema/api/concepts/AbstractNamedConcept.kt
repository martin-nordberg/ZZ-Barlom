//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a named concept - any concept with a name.
 */
abstract class AbstractNamedConcept<Concept> internal constructor()
    : AbstractDocumentedConcept<Concept>() {

    /** The name of this concept. */
    abstract var name: String

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "name" -> name
            else   -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "name" -> true
            else   -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("name")

    override fun setString(propertyName: String, value: String) =
        when (propertyName) {
            "name" -> name = value
            else   -> super.setString(propertyName, value)
        }

}

//---------------------------------------------------------------------------------------------------------------------

