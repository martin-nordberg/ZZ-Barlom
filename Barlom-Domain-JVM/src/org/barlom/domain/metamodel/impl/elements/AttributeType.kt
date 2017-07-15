//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.*
import org.barlom.domain.metamodel.api.types.EDataType
import java.time.Instant

/**
 * Attribute type implementation.
 */
internal sealed class AttributeType : IAttributeType


/**
 * Implementation of a boolean attribute type.
 */
internal data class BooleanAttributeType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val defaultValue: Boolean?

) : AttributeType(), IBooleanAttributeType {

    init {
        parentPackage.addAttributeType(this)
    }

    override val dataType: EDataType
        get() = EDataType.BOOLEAN

}


/**
 * Date/time attribute type implementation.
 */
internal data class DateTimeAttributeType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val maxValue: Instant,
    override val minValue: Instant

) : AttributeType(), IDateTimeAttributeType {

    init {
        parentPackage.addAttributeType(this)
    }

    override val dataType: EDataType
        get() = EDataType.DATETIME

}


/**
 * Implementation for 64-bit floating point attribute types.
 */
internal data class Float64AttributeType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val defaultValue: Double?,
    override val maxValue: Double?,
    override val minValue: Double?

) : AttributeType(), IFloat64AttributeType {

    init {
        parentPackage.addAttributeType(this)
    }

    override val dataType: EDataType
        get() = EDataType.FLOAT64

}


/**
 * Implementation for 32-bit integer attribute types.
 */
internal data class Integer32AttributeType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val defaultValue: Int?,
    override val maxValue: Int?,
    override val minValue: Int?

) : AttributeType(), IInteger32AttributeType {

    init {
        parentPackage.addAttributeType(this)
    }

    override val dataType: EDataType
        get() = EDataType.INTEGER32

}


/**
 * Implementation for string attribute types.
 */
internal data class StringAttributeType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl,
    override val maxLength: Int?,
    override val minLength: Int?,
    override val regex: Regex?

) : AttributeType(), IStringAttributeType {

    init {
        parentPackage.addAttributeType(this)
    }

    override val dataType: EDataType
        get() = EDataType.STRING

}


/**
 * Implementation of a UUID attribute type.
 */
internal data class UuidAttributeType(

    override val id: String,
    override val name: String,
    override val parentPackage: INamedPackageImpl

) : AttributeType(), IUuidAttributeType {

    init {
        parentPackage.addAttributeType(this)
    }

    override val dataType: EDataType
        get() = EDataType.UUID

}
