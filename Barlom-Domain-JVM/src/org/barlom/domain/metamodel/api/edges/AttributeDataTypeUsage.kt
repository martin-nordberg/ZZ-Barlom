//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.AbstractAttributeType
import org.barlom.domain.metamodel.api.vertices.AbstractElement
import org.barlom.domain.metamodel.api.vertices.ConstrainedDataType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for the link from attribute type to constrained data type.
 */
class AttributeDataTypeUsage internal constructor(

    override val id: Uuid,
    val attributeType: AbstractAttributeType,
    val dataType: ConstrainedDataType

) : AbstractElement() {

    init {

        // Register both ends.
        attributeType.addAttributeDataTypeUsage(this)
        dataType.addAttributeDataTypeUsage(this)

    }


    override fun remove() {
        // Unregister both ends.
        attributeType.removeAttributeDataTypeUsage(this)
        dataType.removeAttributeDataTypeUsage(this)
    }

}