//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.*
import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.infrastructure.platform.DateTime
import org.barlom.infrastructure.uuids.Uuid

/**
 * Constrained data type implementation.
 */
internal sealed class ConstrainedDataType : IConstrainedDataType


/**
 * Implementation of a boolean constrained data type.
 */
internal data class BooleanConstrainedDataType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl,
    override val defaultValue: Boolean?

) : ConstrainedDataType(), IBooleanConstrainedDataType {

    init {
        parentPackage.addConstrainedDataType(this)
    }

    override val dataType: EDataType
        get() = EDataType.BOOLEAN

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Date/time constrained data type implementation.
 */
internal data class DateTimeConstrainedDataType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl,
    override val maxValue: DateTime,
    override val minValue: DateTime

) : ConstrainedDataType(), IDateTimeConstrainedDataType {

    init {
        parentPackage.addConstrainedDataType(this)
    }

    override val dataType: EDataType
        get() = EDataType.DATETIME

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation for 64-bit floating point constrained data types.
 */
internal data class Float64ConstrainedDataType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl,
    override val defaultValue: Double?,
    override val maxValue: Double?,
    override val minValue: Double?

) : ConstrainedDataType(), IFloat64ConstrainedDataType {

    init {
        parentPackage.addConstrainedDataType(this)
    }

    override val dataType: EDataType
        get() = EDataType.FLOAT64

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation for 32-bit integer constrained data types.
 */
internal data class Integer32ConstrainedDataType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl,
    override val defaultValue: Int?,
    override val maxValue: Int?,
    override val minValue: Int?

) : ConstrainedDataType(), IInteger32ConstrainedDataType {

    init {
        parentPackage.addConstrainedDataType(this)
    }

    override val dataType: EDataType
        get() = EDataType.INTEGER32

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation for string constrained data types.
 */
internal data class StringConstrainedDataType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl,
    override val maxLength: Int?,
    override val minLength: Int?,
    override val regex: Regex?

) : ConstrainedDataType(), IStringConstrainedDataType {

    init {
        parentPackage.addConstrainedDataType(this)
    }

    override val dataType: EDataType
        get() = EDataType.STRING

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation of a UUID constrained data type.
 */
internal data class UuidConstrainedDataType(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: INonRootPackageImpl

) : ConstrainedDataType(), IUuidConstrainedDataType {

    init {
        parentPackage.addConstrainedDataType(this)
    }

    override val dataType: EDataType
        get() = EDataType.UUID

    override val path: String
        get() = parentPackage.path + "." + name

}
