//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IEdgeAttributeType
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.impl.vertices.ConstrainedDataType
import org.barlom.domain.metamodel.impl.vertices.INonRootEdgeTypeImpl
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

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override var optionality: EAttributeOptionality
        get() = _optionality.get()
        set(value) = _optionality.set(value)

    override val parentEdgeType: INonRootEdgeTypeImpl
        get() = _parentEdgeType.get()

    override val type: ConstrainedDataType
        get() = _type.get()

}
