//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IEdgeAttributeType
import org.barlom.domain.metamodel.api.types.EAttributeOptionality

/**
 * Implementation class for edge attribute types.
 */
internal data class EdgeAttributeType(

    override val id: String,
    override val name: String,
    override val parentEdgeType: IEdgeTypeImpl,
    override val type: ConstrainedDataType,
    override val optionality: EAttributeOptionality

) : IEdgeAttributeType {

    init {
        parentEdgeType.addAttributeType(this)
    }

}
