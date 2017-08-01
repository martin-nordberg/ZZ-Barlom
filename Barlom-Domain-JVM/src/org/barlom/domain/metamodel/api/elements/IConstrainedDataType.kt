//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.infrastructure.platform.DateTime

/**
 * Constrained data type interface. An constrained data type defines the data type together with constraints like
 * maximum length or minimum value of a vertex or edge attribute.
 */
interface IConstrainedDataType : IPackagedElement {

    /** The core data type that is being constrained. */
    val dataType: EDataType

}


/**
 * Implementation of a boolean constrained data type.
 */
interface IBooleanConstrainedDataType : IConstrainedDataType {

    /** The default value for attributes of this type. */
    var defaultValue: Boolean?

}


/**
 * Date/time constrained data type implementation.
 */
interface IDateTimeConstrainedDataType : IConstrainedDataType {

    /** The maximum allowed value for attributes with this type. */
    var maxValue: DateTime

    /** The minimum allowed value for attributes with this type. */
    var minValue: DateTime

}


/**
 * Implementation for 64-bit floating point constrained data types.
 */
interface IFloat64ConstrainedDataType : IConstrainedDataType {

    /** The default value for attributes of this type. */
    var defaultValue: Double?

    /** The maximum allowed value for attributes with this type. */
    var maxValue: Double?

    /** The minimum allowed value for attributes with this type. */
    var minValue: Double?

}


/**
 * Implementation for 32-bit integer constrained data types.
 */
interface IInteger32ConstrainedDataType : IConstrainedDataType {

    /** The default value for attributes of this type. */
    var defaultValue: Int?

    /** The maximum allowed value for attributes with this type. */
    var maxValue: Int?

    /** The minimum allowed value for attributes with this type. */
    var minValue: Int?

}


/**
 * Implementation for string constrained data types.
 */
interface IStringConstrainedDataType : IConstrainedDataType {

    /** The maximum length for values with this attribute type. */
    var maxLength: Int?

    /** The minimum length for attributes of this type. */
    var minLength: Int?

    /** The regular expression that must be matched by values with this attribute type. */
    var regex: Regex?

}


/**
 * Implementation of a UUID constrained data type.
 */
interface IUuidConstrainedDataType : IConstrainedDataType

