//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractAttributeType
import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.ConstrainedDataType
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for the link from attribute type to constrained data type.
 */
class AttributeDataTypeUsage internal constructor(

    override val id: Uuid,
    val attributeType: AbstractAttributeType,
    val dataType: ConstrainedDataType

) : AbstractDocumentedElement() {

    init {

        // Register both ends.
        attributeType.addAttributeDataTypeUsage(this)
        dataType.addAttributeDataTypeUsage(this)

    }

}