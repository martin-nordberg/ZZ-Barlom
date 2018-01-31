//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.AttributeDataTypeUsage
import org.barlom.domain.metamodel.api.types.EAttributeOptionality

/**
 * Abstract attribute type (for a vertex or edge).
 */
abstract class AbstractAttributeType internal constructor() : AbstractNamedElement() {

    /** The constrained data type of this attribute type. */
    abstract val dataTypes: List<ConstrainedDataType>

    /** The constrained data type usages of this attribute type. */
    abstract val dataTypeUsages: List<AttributeDataTypeUsage>

    /** Whether this attribute is required for instances of the parent vertex type. */
    abstract var optionality: EAttributeOptionality


    /** Adds a link to the constrained data type of this attribute type. */
    abstract internal fun addAttributeDataTypeUsage(usage: AttributeDataTypeUsage)

    /** Removes a link from the constrained data type of this attribute type. */
    abstract internal fun removeAttributeDataTypeUsage(usage: AttributeDataTypeUsage)

}