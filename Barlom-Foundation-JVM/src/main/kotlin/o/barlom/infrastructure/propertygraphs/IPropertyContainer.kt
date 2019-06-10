//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.propertygraphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * An entity that has properties retrievable by name.
 */
interface IPropertyContainer {

    /** @return a property by [propertyName]. */
    operator fun get(propertyName: String): Any? =
        null

    /** @return the property with given [propertyName], or [defaultValue] if the named property is null or not present. */
    fun getOrDefault(propertyName: String, defaultValue: Int): Int =
        getInt(propertyName) ?: defaultValue

    /** @return the property with given [propertyName], or [defaultValue] if the named property is null or not present. */
    fun getOrDefault(propertyName: String, defaultValue: String): String =
        getString(propertyName) ?: defaultValue

    /** @return a Boolean property by [propertyName]. */
    fun getBoolean(propertyName: String): Boolean? =
        get(propertyName) as Boolean?

    /** @return an integer property by [propertyName]. */
    fun getInt(propertyName: String): Int? =
        get(propertyName) as Int?

    /** @return a string property by [propertyName]. */
    fun getString(propertyName: String): String? =
        get(propertyName) as String?

    /** @return true if this concept has a property with given [propertyName]. */
    fun hasProperty(propertyName: String): Boolean =
        false

    /** @return the names of all properties relevant for this entity. */
    fun propertyNames(): Set<String> =
        setOf()

}

//---------------------------------------------------------------------------------------------------------------------

