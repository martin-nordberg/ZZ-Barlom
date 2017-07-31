//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.*
import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.infrastructure.platform.DateTime
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.uuids.Uuid

/**
 * Constrained data type implementation.
 */
internal sealed class ConstrainedDataType : IConstrainedDataType


/**
 * Implementation of a boolean constrained data type.
 */
internal class BooleanConstrainedDataType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl,
    defaultValue: Boolean?

) : ConstrainedDataType(), IBooleanConstrainedDataType {

    private val _defaultValue = V(defaultValue)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)


    init {
        parentPackage.addConstrainedDataType(this)
    }


    override val dataType: EDataType
        get() = EDataType.BOOLEAN

    override val defaultValue: Boolean?
        get() = _defaultValue.get()

    override val name: String
        get() = _name.get()

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Date/time constrained data type implementation.
 */
internal class DateTimeConstrainedDataType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl,
    maxValue: DateTime,
    minValue: DateTime

) : ConstrainedDataType(), IDateTimeConstrainedDataType {

    private val _maxValue = V(maxValue)
    private val _minValue = V(minValue)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)


    init {
        parentPackage.addConstrainedDataType(this)
    }


    override val dataType: EDataType
        get() = EDataType.DATETIME

    override val maxValue: DateTime
        get() = _maxValue.get()

    override val minValue: DateTime
        get() = _minValue.get()

    override val name: String
        get() = _name.get()

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation for 64-bit floating point constrained data types.
 */
internal class Float64ConstrainedDataType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl,
    defaultValue: Double?,
    maxValue: Double?,
    minValue: Double?

) : ConstrainedDataType(), IFloat64ConstrainedDataType {

    private val _defaultValue = V(defaultValue)
    private val _maxValue = V(maxValue)
    private val _minValue = V(minValue)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)


    init {
        parentPackage.addConstrainedDataType(this)
    }


    override val dataType: EDataType
        get() = EDataType.FLOAT64

    override val defaultValue: Double?
        get() = _defaultValue.get()

    override val maxValue: Double?
        get() = _maxValue.get()

    override val minValue: Double?
        get() = _minValue.get()

    override val name: String
        get() = _name.get()

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation for 32-bit integer constrained data types.
 */
internal class Integer32ConstrainedDataType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl,
    defaultValue: Int?,
    maxValue: Int?,
    minValue: Int?

) : ConstrainedDataType(), IInteger32ConstrainedDataType {

    private val _defaultValue = V(defaultValue)
    private val _maxValue = V(maxValue)
    private val _minValue = V(minValue)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)


    init {
        parentPackage.addConstrainedDataType(this)
    }


    override val dataType: EDataType
        get() = EDataType.INTEGER32

    override val defaultValue: Int?
        get() = _defaultValue.get()

    override val maxValue: Int?
        get() = _maxValue.get()

    override val minValue: Int?
        get() = _minValue.get()

    override val name: String
        get() = _name.get()

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

}


/**
 * Implementation for string constrained data types.
 */
internal class StringConstrainedDataType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl,
    maxLength: Int?,
    minLength: Int?,
    regex: Regex?

) : ConstrainedDataType(), IStringConstrainedDataType {

    private val _maxLength = V(maxLength)
    private val _minLength = V(minLength)
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)
    private val _regex = V(regex)


    init {
        parentPackage.addConstrainedDataType(this)
    }


    override val dataType: EDataType
        get() = EDataType.STRING

    override val maxLength: Int?
        get() = _maxLength.get()

    override val minLength: Int?
        get() = _minLength.get()

    override val name: String
        get() = _name.get()

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

    override val regex: Regex?
        get() = _regex.get()

}


/**
 * Implementation of a UUID constrained data type.
 */
internal class UuidConstrainedDataType(

    override val id: Uuid,
    name: String,
    parentPackage: INonRootPackageImpl

) : ConstrainedDataType(), IUuidConstrainedDataType {

    private val _name = V(name)
    private val _parentPackage = V(parentPackage)


    init {
        parentPackage.addConstrainedDataType(this)
    }


    override val dataType: EDataType
        get() = EDataType.UUID

    override val name: String
        get() = _name.get()

    override val parentPackage: INonRootPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() = parentPackage.path + "." + name

}
