//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IEdgeAttributeType
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for edge attribute types.
 */
internal class EdgeAttributeType(

    override val id: Uuid,
    name: String,
    parentEdgeType: INonRootEdgeTypeImpl,
    type: ConstrainedDataType,
    optionality: EAttributeOptionality

) : IEdgeAttributeType {

    private val _name = V(name)
    private val _optionality = V(optionality)
    private val _parentEdgeType = V(parentEdgeType)
    private val _type = V(type)

    init {
        parentEdgeType.addAttributeType(this)
    }

    override val name: String
        get() = _name.get()

    override val optionality: EAttributeOptionality
        get() = _optionality.get()

    override val parentEdgeType: INonRootEdgeTypeImpl
        get() = _parentEdgeType.get()

    override val type: ConstrainedDataType
        get() = _type.get()

}
