//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EDataType
import java.time.Instant

/**
 * Attribute type interface. An attribute type defines the data type together with constraints like maximum length
 * or minimum value of a vertex or edge attribute.
 */
interface IAttributeType : IPackagedElement {

    val dataType: EDataType

}


/**
 * Implementation of a boolean attribute type.
 */
interface IBooleanAttributeType : IAttributeType {

    /** The default value for attributes of this type. */
    val defaultValue: Boolean?

}


/**
 * Date/time attribute type implementation.
 */
interface IDateTimeAttributeType : IAttributeType {

    /** The maximum allowed value for attributes with this type. */
    val maxValue: Instant

    /** The minimum allowed value for attributes with this type. */
    val minValue: Instant

}


/**
 * Implementation for 64-bit floating point attribute types.
 */
interface IFloat64AttributeType : IAttributeType {

    /** The default value for attributes of this type. */
    val defaultValue: Double?

    /** The maximum allowed value for attributes with this type. */
    val maxValue: Double?

    /** The minimum allowed value for attributes with this type. */
    val minValue: Double?

}


/**
 * Implementation for 32-bit integer attribute types.
 */
interface IInteger32AttributeType : IAttributeType {

    /** The default value for attributes of this type. */
    val defaultValue: Int?

    /** The maximum allowed value for attributes with this type. */
    val maxValue: Int?

    /** The minimum allowed value for attributes with this type. */
    val minValue: Int?

}


/**
 * Implementation for string attribute types.
 */
interface IStringAttributeType : IAttributeType {

    /** The maximum length for values with this attribute type. */
    val maxLength: Int?

    /** The minimum length for attributes of this type. */
    val minLength: Int?

    /** The regular expression that must be matched by values with this attribute type. */
    val regex: Regex?

}


/**
 * Implementation of a UUID attribute type.
 */
interface IUuidAttributeType : IAttributeType

