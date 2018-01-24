//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.AttributeDataTypeUsage
import org.barlom.domain.metamodel.api.edges.VertexAttributeTypeContainment
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

class VertexAttributeType(

    override val id: Uuid

) : AbstractAttributeType() {

    private val _dataTypeUsages = VLinkedList<AttributeDataTypeUsage>()
    private val _description = V("")
    private val _labelDefaulting = V(ELabelDefaulting.NOT_DEFAULT_LABEL)
    private val _name = V("NewAttributeType")
    private val _optionality = V(EAttributeOptionality.OPTIONAL)
    private val _vertexAttributeTypeContainments = VLinkedList<VertexAttributeTypeContainment>()


    override val dataTypes: List<ConstrainedDataType>
        get() = _dataTypeUsages.map { i -> i.dataType }.sortedBy { dt -> dt.path }

    override var description: String
        get() = _description.get()
        set(value) = _description.set(value)

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    var labelDefaulting: ELabelDefaulting
        get() = _labelDefaulting.get()
        set(value) = _labelDefaulting.set(value)

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override var optionality: EAttributeOptionality
        get() = _optionality.get()
        set(value) = _optionality.set(value)

    override val path: String
        get() {

            if (_vertexAttributeTypeContainments.isEmpty()) {
                return name
            }

            val parentPath = vertexTypes[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "#" + name

        }

    /** The vertex types of this vertex attribute type. */
    val vertexTypes: List<VertexType>
        get() = _vertexAttributeTypeContainments.map { i -> i.vertexType }.sortedBy { vt -> vt.path }

    /** Link to the vertex type containing this vertex attribute type. */
    val vertexAttributeTypeContainments: List<VertexAttributeTypeContainment>
        get() = _vertexAttributeTypeContainments.sortedBy { i -> i.vertexType.path }


    override fun addAttributeDataTypeUsage(usage: AttributeDataTypeUsage) {

        require(usage.attributeType === this) {
            "Attribute data type usage linked to wrong attribute type."
        }

        for ( dtu in _dataTypeUsages ) {
            dtu.dataType.removeAttributeDataTypeUsage( dtu )
        }
        _dataTypeUsages.clear()

        _dataTypeUsages.add(usage)

    }

    /** Adds a vertex type to this, its child vertex attribute type's, list of vertex attribute type containments. */
    internal fun addVertexAttributeTypeContainment(vertexAttributeTypeContainment: VertexAttributeTypeContainment) {

        require(vertexAttributeTypeContainment.attributeType === this) {
            "Cannot add a vertex type to an attribute not its child."
        }

        _vertexAttributeTypeContainments.add(vertexAttributeTypeContainment)

    }

    /** Finds the constrained data types that are eligible to be the data type of this attribute type. */
    fun findPotentialDataTypes() : List<ConstrainedDataType> {

        val result = mutableListOf<ConstrainedDataType>()

        fun findDataTypesInPackage( pkg: Package ) {

            // same package as parent vertex type
            for ( dt in pkg.constrainedDataTypes ) {
                result.add( dt )
            }

            for ( pkg2 in pkg.transitiveSuppliers ) {

                for ( dt in pkg2.constrainedDataTypes ) {
                    result.add(dt)
                }

            }

        }

        for ( vt in vertexTypes ) {

            for ( pkg in vt.parents ) {
                findDataTypesInPackage( pkg )
            }

        }

        return result

    }

    /** Whether this attribute type is a child of the given vertex type. */
    fun hasParent(vt: VertexType) = _vertexAttributeTypeContainments.contains({ c -> c.vertexType == vt })

    override fun hasParentPackage(pkg: Package): Boolean {
        return _vertexAttributeTypeContainments.contains({ c -> c.vertexType.hasParent(pkg) })
    }

}