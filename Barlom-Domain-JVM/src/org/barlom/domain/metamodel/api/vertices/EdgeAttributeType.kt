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
    // TODO: edgeWeightDefaulting - is this attribute the default weight for a weighted edge?


    override val dataTypes: List<ConstrainedDataType>
        get() = _dataTypeUsages.map { i -> i.dataType }.sortedBy { dt -> dt.path }

    override val dataTypeUsages: List<AttributeDataTypeUsage>
        get() = _dataTypeUsages.sortedBy { dt -> dt.dataType.path }

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

    /** Finds the constrained data types that are eligible to be the data type of this attribute type. */
    fun findPotentialDataTypes(): List<ConstrainedDataType> {

        val result = mutableListOf<ConstrainedDataType>()

        fun findDataTypesInPackage(pkg: Package) {

            // same package as parent vertex type
            for (dt in pkg.constrainedDataTypes) {
                result.add(dt)
            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (dt in pkg2.constrainedDataTypes) {
                    result.add(dt)
                }

            }

        }

        for (et in edgeTypes) {

            for (pkg in et.parents) {
                findDataTypesInPackage(pkg)
            }

        }

        return result

    }

    override fun hasParentPackage(pkg: Package): Boolean {
        return _edgeAttributeTypeContainments.contains({ c -> c.edgeType.hasParent(pkg) })
    }

    override fun remove() {
        _dataTypeUsages.forEach { dtu -> dtu.remove() }
        _edgeAttributeTypeContainments.forEach { c -> c.remove() }
    }

    override fun removeAttributeDataTypeUsage(usage: AttributeDataTypeUsage) {

        require(_dataTypeUsages.remove(usage)) {
            "Attribute data type usage not linked to this attribute type."
        }

    }

    /** Removes an edge type from this, its child edge attribute type's, list of edge attribute type containments. */
    internal fun removeEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment) {

        require(_edgeAttributeTypeContainments.remove(edgeAttributeTypeContainment)) {
            "Cannot remove an edge type from an attribute not its child."
        }

    }

}

