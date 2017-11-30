//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.AttributeDataTypeUsage
import org.barlom.domain.metamodel.api.edges.EdgeAttributeTypeContainment
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface of edge attribute types. An edge attribute type includes the name, optionality, and constrained data
 * type for attributes in a given parent edge type.
 */
class EdgeAttributeType(

    override val id: Uuid

) : AbstractAttributeType() {

    private val _dataTypeUsages = VLinkedList<AttributeDataTypeUsage>()
    private val _description = V("")
    private val _edgeAttributeTypeContainments = VLinkedList<EdgeAttributeTypeContainment>()
    private val _name = V("NewAttributeType")
    private val _optionality = V(EAttributeOptionality.OPTIONAL)


    override val dataTypes: List<ConstrainedDataType>
        get() = _dataTypeUsages.map { i -> i.dataType }.sortedBy { dt -> dt.path }

    override var description: String
        get() = _description.get()
        set(value) = _description.set(value)

    /** The edge types of this edge attribute type. */
    val edgeTypes: List<AbstractEdgeType>
        get() = _edgeAttributeTypeContainments.map { i -> i.edgeType }.sortedBy { et -> et.path }

    /** Link to the edge type containing this edge attribute type. */
    val edgeAttributeTypeContainments: List<EdgeAttributeTypeContainment>
        get() = _edgeAttributeTypeContainments.sortedBy { i -> i.edgeType.path }

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override var optionality: EAttributeOptionality
        get() = _optionality.get()
        set(value) = _optionality.set(value)

    override val path: String
        get() {

            if (_edgeAttributeTypeContainments.isEmpty()) {
                return name
            }

            val parentPath = edgeTypes[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "#" + name

        }


    override fun addAttributeDataTypeUsage(usage: AttributeDataTypeUsage) {

        require(usage.attributeType === this) {
            "Attribute data type usage linked to wrong attribute type."
        }

        _dataTypeUsages.add(usage)

    }

    /** Adds an edge type to this, its child edge attribute type's, list of edge attribute type containments. */
    internal fun addEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment) {

        require(edgeAttributeTypeContainment.attributeType === this) {
            "Cannot add an edge type to an attribute not its child."
        }

        _edgeAttributeTypeContainments.add(edgeAttributeTypeContainment)

    }

}

