//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.edges2.ConstrainedDataTypeContainment
import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.infrastructure.platform.DateTime
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Constrained data type implementation.
 */
sealed class ConstrainedDataType(

    override val id: Uuid,
    name: String

) : AbstractPackagedElement() {

    private val _constrainedDataTypeContainments = VLinkedList<ConstrainedDataTypeContainment>()
    private val _name = V(name)


    /** The core data type that is being constrained. */
    abstract val dataType: EDataType

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parents: List<Package>
        get() = _constrainedDataTypeContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val path: String
        get() {

            if (_constrainedDataTypeContainments.isEmpty) {
                return name
            }

            val parentPath = parents[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }


    fun addConstrainedDataTypeContainment(constrainedDataTypeContainment: ConstrainedDataTypeContainment) {

        require(constrainedDataTypeContainment.child === this) {
            "Constrained data type containment can only be added to its child."
        }

        _constrainedDataTypeContainments.add(constrainedDataTypeContainment)

    }

    override fun hasParent(pkg: Package): Boolean {

        var result = false

        _constrainedDataTypeContainments.forEachWhile { constrainedDataTypeContainment ->

            result = pkg === constrainedDataTypeContainment.parent
            !result

        }

        return result

    }

}

/**
 * Implementation of a boolean constrained data type.
 */
class ConstrainedBoolean(

    id: Uuid

) : ConstrainedDataType(id, "NewConstrainedBoolean") {

    private val _defaultValue: V<Boolean?> = V(null)


    override val dataType: EDataType
        get() = EDataType.BOOLEAN

    /** The default value for attributes of this type. */
    var defaultValue: Boolean?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

}

/**
 * Date/time constrained data type implementation.
 */
class ConstrainedDateTime(

    id: Uuid

) : ConstrainedDataType(id, "NewConstrainedDateTime") {

    private val _maxValue: V<DateTime?> = V(null)
    private val _minValue: V<DateTime?> = V(null)


    override val dataType: EDataType
        get() = EDataType.DATETIME

    /** The maximum allowed value for attributes with this type. */
    var maxValue: DateTime?
        get() = _maxValue.get()
        set(value) = _maxValue.set(value)

    /** The minimum allowed value for attributes with this type. */
    var minValue: DateTime?
        get() = _minValue.get()
        set(value) = _minValue.set(value)

}

/**
 * Implementation for 64-bit floating point constrained data types.
 */
class ConstrainedFloat64(

    id: Uuid

) : ConstrainedDataType(id, "NewConstrainedFloat64") {

    private val _defaultValue: V<Double?> = V(null)
    private val _maxValue: V<Double?> = V(null)
    private val _minValue: V<Double?> = V(null)


    override val dataType: EDataType
        get() = EDataType.FLOAT64

    /** The default value for attributes of this type. */
    var defaultValue: Double?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

    /** The maximum allowed value for attributes with this type. */
    var maxValue: Double?
        get() = _maxValue.get()
        set(value) = _maxValue.set(value)

    /** The minimum allowed value for attributes with this type. */
    var minValue: Double?
        get() = _minValue.get()
        set(value) = _minValue.set(value)

}

/**
 * Implementation for 32-bit integer constrained data types.
 */
class ConstrainedInteger32(

    id: Uuid

) : ConstrainedDataType(id, "NewConstrainedInteger32") {

    private val _defaultValue: V<Int?> = V(null)
    private val _maxValue: V<Int?> = V(null)
    private val _minValue: V<Int?> = V(null)


    override val dataType: EDataType
        get() = EDataType.INTEGER32

    /** The default value for attributes of this type. */
    var defaultValue: Int?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

    /** The maximum allowed value for attributes with this type. */
    var maxValue: Int?
        get() = _maxValue.get()
        set(value) = _maxValue.set(value)

    var minValue: Int?
        get() = _minValue.get()
        set(value) = _minValue.set(value)

}

/**
 * Implementation for string constrained data types.
 */
class ConstrainedString(

    id: Uuid

) : ConstrainedDataType(id, "NewConstrainedString") {

    private val _defaultValue: V<String?> = V(null)
    private val _maxLength: V<Int?> = V(null)
    private val _minLength: V<Int?> = V(null)
    private val _regex: V<Regex?> = V(null)


    override val dataType: EDataType
        get() = EDataType.STRING

    /** The default value for attributes of this type. */
    var defaultValue: String?
        get() = _defaultValue.get()
        set(value) = _defaultValue.set(value)

    /** The maximum allowed length for attributes with this type. */
    var maxLength: Int?
        get() = _maxLength.get()
        set(value) = _maxLength.set(value)

    /** The minimum allowed length for attributes with this type. */
    var minLength: Int?
        get() = _minLength.get()
        set(value) = _minLength.set(value)

    /** The regular expression that must be matched by values with this attribute type. */
    var regex: Regex?
        get() = _regex.get()
        set(value) = _regex.set(value)


}

/**
 * Implementation of a UUID constrained data type.
 */
class ConstrainedUuid(

    id: Uuid

) : ConstrainedDataType(id, "NewConstrainedUuid") {

    override val dataType: EDataType
        get() = EDataType.UUID

}