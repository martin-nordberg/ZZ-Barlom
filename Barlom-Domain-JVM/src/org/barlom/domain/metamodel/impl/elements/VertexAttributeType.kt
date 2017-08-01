//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IVertexAttributeType
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for vertex attribute types.
 */
internal class VertexAttributeType(

    override val id: Uuid,
    name: String,
    parentVertexType: VertexType,
    labelDefaulting: ELabelDefaulting,
    optionality: EAttributeOptionality,
    type: ConstrainedDataType

) : IVertexAttributeType {

    private val _name = V(name)
    private val _labelDefaulting = V(labelDefaulting)
    private val _optionality = V(optionality)
    private val _parentVertexType = V(parentVertexType)
    private val _type = V(type)


    init {
        parentVertexType.addAttributeType(this)
    }


    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override var labelDefaulting: ELabelDefaulting
        get() = _labelDefaulting.get()
        set(value) = _labelDefaulting.set(value)

    override var optionality: EAttributeOptionality
        get() = _optionality.get()
        set(value) = _optionality.set(value)

    override val parentVertexType: VertexType
        get() = _parentVertexType.get()

    override val type: ConstrainedDataType
        get() = _type.get()

}
