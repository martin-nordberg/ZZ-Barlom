//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.edges

import o.barlom.domain.metamodel.api.vertices.AbstractAttributeType
import o.barlom.domain.metamodel.api.vertices.AbstractElement
import o.barlom.domain.metamodel.api.vertices.ConstrainedDataType
import x.barlom.infrastructure.uuids.Uuid

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
