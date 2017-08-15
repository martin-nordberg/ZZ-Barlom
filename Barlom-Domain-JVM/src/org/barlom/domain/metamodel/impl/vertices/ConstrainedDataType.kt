//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.domain.metamodel.api.vertices.*
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

    override var defaultValue: Boolean?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf(_parentPackage.get())

    override val path: String
        get() = parentPackages[0].path + "" + name

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

    override var maxValue: DateTime
        get() = _maxValue.get()
        set(value) = _maxValue.set(value)

    override var minValue: DateTime
        get() = _minValue.get()
        set(value) = _minValue.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf(_parentPackage.get())

    override val path: String
        get() = parentPackages[0].path + "" + name

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

    override var defaultValue: Double?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

    override var maxValue: Double?
        get() = _maxValue.get()
        set(value) = _maxValue.set(value)

    override var minValue: Double?
        get() = _minValue.get()
        set(value) = _minValue.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf(_parentPackage.get())

    override val path: String
        get() = parentPackages[0].path + "" + name

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

    override var defaultValue: Int?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

    override var maxValue: Int?
        get() = _maxValue.get()
        set(value) = _maxValue.set(value)

    override var minValue: Int?
        get() = _minValue.get()
        set(value) = _minValue.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf(_parentPackage.get())

    override val path: String
        get() = parentPackages[0].path + "" + name

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

    override var maxLength: Int?
        get() = _maxLength.get()
        set(value) = _maxLength.set(value)

    override var minLength: Int?
        get() = _minLength.get()
        set(value) = _minLength.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf(_parentPackage.get())

    override val path: String
        get() = parentPackages[0].path + "" + name

    override var regex: Regex?
        get() = _regex.get()
        set(value) = _regex.set(value)

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

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<INonRootPackageImpl>
        get() = listOf(_parentPackage.get())

    override val path: String
        get() = parentPackages[0].path + "" + name

}
