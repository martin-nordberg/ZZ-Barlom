//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EDataType
import o.barlom.infrastructure.graphs.ConceptTypeId
import x.barlom.infrastructure.platform.DateTime
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Metadata for a data type with constraints.
 */
sealed class ConstrainedDataType<Concept : ConstrainedDataType<Concept>>
    : AbstractPackagedConcept<Concept>() {

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
) : ConstrainedDataType<ConstrainedBoolean>() {

    override val dataType = EDataType.BOOLEAN

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConstrainedBoolean>(
            "o.barlom.domain.graphschema.api.concepts.ConstrainedBoolean"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedDateTime(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    val maxValue: DateTime? = null,
    val minValue: DateTime? = null
) : ConstrainedDataType<ConstrainedDateTime>() {

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

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConstrainedDateTime>(
            "o.barlom.domain.graphschema.api.concepts.ConstrainedDateTime"
        )
    }

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
) : ConstrainedDataType<ConstrainedFloat64>() {

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

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConstrainedFloat64>(
            "o.barlom.domain.graphschema.api.concepts.ConstrainedFloat64"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedInteger32(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = "",
    val defaultValue: Int? = null,
    val maxValue: Int? = null,
    val minValue: Int? = null
) : ConstrainedDataType<ConstrainedInteger32>() {

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

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConstrainedInteger32>(
            "o.barlom.domain.graphschema.api.concepts.ConstrainedInteger32"
        )
    }

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
) : ConstrainedDataType<ConstrainedString>() {

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

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConstrainedString>(
            "o.barlom.domain.graphschema.api.concepts.ConstrainedString"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

data class ConstrainedUuid(
    override val uuid: Uuid,
    override val name: String,
    override val description: String = ""
) : ConstrainedDataType<ConstrainedUuid>() {

    override val dataType = EDataType.UUID

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<ConstrainedUuid>(
            "o.barlom.domain.graphschema.api.concepts.ConstrainedUuid"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

