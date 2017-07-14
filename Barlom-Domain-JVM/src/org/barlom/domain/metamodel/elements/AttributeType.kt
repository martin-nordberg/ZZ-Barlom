//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.types.EDataType
import java.time.Instant

/**
 * Attribute type implementation.
 */
sealed class AttributeType(

    id: String,
    name: String,

    /** The parent package of this attribute type. */
    override final val parentPackage: NamedPackage

) : PackagedElement(id, name) {

    abstract val dataType: EDataType

    init {
        parentPackage.addAttributeType(this)
    }

}


/**
 * Implementation of a boolean attribute type.
 */
class BooleanAttributeType(

    id: String,
    name: String,
    parentPackage: NamedPackage,

    /** The default value for attributes of this type. */
    val defaultValue: Boolean?

) : AttributeType(id, name, parentPackage) {

    override val dataType: EDataType
        get() = EDataType.BOOLEAN

}


/**
 * Date/time attribute type implementation.
 */
class DateTimeAttributeType(
    id: String,
    name: String,
    parentPackage: NamedPackage,

    /** The maximum allowed value for attributes with this type. */
    val maxValue: Instant,

    /** The minimum allowed value for attributes with this type. */
    val minValue: Instant

) : AttributeType(id, name, parentPackage) {

    override val dataType: EDataType
        get() = EDataType.DATETIME


}


/**
 * Implementation for 64-bit floating point attribute types.
 */
class Float64AttributeType(

    id: String,
    name: String,
    parentPackage: NamedPackage,

    /** The default value for attributes of this type. */
    val defaultValue: Double?,

    /** The maximum allowed value for attributes with this type. */
    val maxValue: Double?,

    /** The minimum allowed value for attributes with this type. */
    val minValue: Double?

) : AttributeType(id, name, parentPackage) {

    override val dataType: EDataType
        get() = EDataType.FLOAT64

}


/**
 * Implementation for 32-bit integer attribute types.
 */
class Integer32AttributeType(

    id: String,
    name: String,
    parentPackage: NamedPackage,

    /** The default value for attributes of this type. */
    val defaultValue: Int?,

    /** The maximum allowed value for attributes with this type. */
    val maxValue: Int?,

    /** The minimum allowed value for attributes with this type. */
    val minValue: Int?

) : AttributeType(id, name, parentPackage) {

    override val dataType: EDataType
        get() = EDataType.INTEGER32

}


/**
 * Implementation for string attribute types.
 */
class StringAttributeType(

    id: String,
    name: String,
    parentPackage: NamedPackage,

    /** The maximum length for values with this attribute type. */
    val maxLength: Int?,

    /** The minimum length for attributes of this type. */
    val minLength: Int?,

    /** The regular expression that must be matched by values with this attribute type. */
    regexPatternStr: String,

    /** Options for the regular expression of these attributes. */
    regexOptions: Set<RegexOption>

) : AttributeType(id, name, parentPackage) {

    override val dataType: EDataType
        get() = EDataType.STRING

    /** The regular expression that must be matched by values with this attribute type. */
    val regex: Regex = Regex(regexPatternStr, regexOptions)

}


/**
 * Implementation of a UUID attribute type.
 */
class UuidAttributeType(

    id: String,
    name: String,
    parentPackage: NamedPackage

) : AttributeType(id, name, parentPackage) {

    override val dataType: EDataType
        get() = EDataType.UUID

}
