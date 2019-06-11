//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EDataType
import x.barlom.infrastructure.platform.DateTime
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Metadata for a data type with constraints.
 */
sealed class ConstrainedDataType
    : AbstractPackagedConcept<ConstrainedDataType>() {

    abstract val dataType: EDataType

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "dataType" -> dataType
            else       -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "dataType" -> true
            else       -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("dataType")

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedBoolean(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = ""
) : ConstrainedDataType() {

    override val dataType = EDataType.BOOLEAN

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedDateTime(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    val maxValue: DateTime? = null,
    val minValue: DateTime? = null
) : ConstrainedDataType() {

    override val dataType = EDataType.DATETIME

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "maxValue" -> maxValue
            "minValue" -> minValue
            else       -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "maxValue" -> true
            "minValue" -> true
            else       -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("maxValue").plus("minValue")

}

//---------------------------------------------------------------------------------------------------------------------

// TODO: ConstrainedPastDateTime, ConstrainedFutureDateTime

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedFloat64(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    val defaultValue: Double? = null,
    val maxValue: Double? = null,
    val minValue: Double? = null
) : ConstrainedDataType() {

    override val dataType = EDataType.FLOAT64

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "defaultValue" -> defaultValue
            "maxValue"     -> maxValue
            "minValue"     -> minValue
            else           -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "defaultValue" -> true
            "maxValue"     -> true
            "minValue"     -> true
            else           -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("defaultValue").plus("maxValue").plus("minValue")

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedInteger32(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    val defaultValue: Int? = null,
    val maxValue: Int? = null,
    val minValue: Int? = null
) : ConstrainedDataType() {

    override val dataType = EDataType.INTEGER32

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "defaultValue" -> defaultValue
            "maxValue"     -> maxValue
            "minValue"     -> minValue
            else           -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "defaultValue" -> true
            "maxValue"     -> true
            "minValue"     -> true
            else           -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("defaultValue").plus("maxValue").plus("minValue")

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedString(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    val defaultValue: String? = null,
    val maxLength: Int? = null,
    val minLength: Int = 0,
    val regexPattern: Regex? = null
) : ConstrainedDataType() {

    override val dataType = EDataType.STRING

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "defaultValue" -> defaultValue
            "maxLength"    -> maxLength
            "minLength"    -> minLength
            "regexPattern" -> regexPattern
            else           -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "defaultValue" -> true
            "maxLength"    -> true
            "minLength"    -> true
            "regexPattern" -> true
            else           -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames()
            .plus("defaultValue")
            .plus("maxLength")
            .plus("minLength")
            .plus("regexPattern")

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedUuid(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = ""
) : ConstrainedDataType() {

    override val dataType = EDataType.UUID

}

//---------------------------------------------------------------------------------------------------------------------

