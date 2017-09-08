//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.model

import org.barlom.domain.metamodel.api.edges2.*
import org.barlom.domain.metamodel.api.vertices2.*
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

class Model(

    /** The unique ID for the root package within this model. */
    rootPackageId: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root vertex type within this model. */
    rootVertexTypeId: Uuid = Uuid.fromString("66522301-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root undirected edge type within this model. */
    rootUndirectedEdgeTypeId: Uuid = Uuid.fromString("66522302-6c7d-11e7-81b7-080027b6d283")

) {

    private val _edges: VLinkedList<AbstractDocumentedElement>

    private val _vertices: VLinkedList<AbstractNamedElement>


    val edges: List<AbstractDocumentedElement>
        get() = _edges.asList()

    val revHistory = RevisionHistory("Initial empty model.")

    val rootPackage: Package

    val rootUndirectedEdgeType: UndirectedEdgeType

    val rootVertexType: VertexType

    val vertices: List<AbstractNamedElement>
        get() = _vertices.sortedBy { e -> e.path }


    init {

        var edges: VLinkedList<AbstractDocumentedElement>? = null
        var vertices: VLinkedList<AbstractNamedElement>? = null

        revHistory.update("Graph initialized.", 0) {
            edges = VLinkedList()
            vertices = VLinkedList()
        }

        _edges = edges!!
        _vertices = vertices!!


        var rootPkg: Package? = null
        var rootUndEdge: UndirectedEdgeType? = null
        var rootVT: VertexType? = null

        revHistory.update("Root elements added.", 0) {
            rootPkg = Package(rootPackageId, true)
            _vertices.add(rootPkg!!)

            rootVT = VertexType(rootVertexTypeId, true)
            _vertices.add(rootVT!!)

            rootUndEdge = UndirectedEdgeType(rootUndirectedEdgeTypeId, true)
            _vertices.add(rootUndEdge!!)

            _edges.add(VertexTypeContainment(makeUuid(), rootPkg!!, rootVT!!))
            _edges.add(UndirectedEdgeTypeContainment(makeUuid(), rootPkg!!, rootUndEdge!!))
        }

        rootPackage = rootPkg!!
        rootVertexType = rootVT!!
        rootUndirectedEdgeType = rootUndEdge!!

    }


    fun makeAttributeDataTypeUsage(
        attributeType: AbstractAttributeType,
        dataType: ConstrainedDataType,
        id: Uuid = makeUuid()
    ): AttributeDataTypeUsage {
        val result = AttributeDataTypeUsage(id, attributeType, dataType)
        _edges.add(result)
        return result
    }

    fun makeConstrainedBoolean(
        id: Uuid = makeUuid(),
        initialize: ConstrainedBoolean.() -> Unit = {}
    ): ConstrainedBoolean {
        val result = ConstrainedBoolean(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeConstrainedDataTypeContainment(
        parent: Package,
        child: ConstrainedDataType,
        id: Uuid = makeUuid()
    ): ConstrainedDataTypeContainment {
        val result = ConstrainedDataTypeContainment(id, parent, child)
        _edges.add(result)
        return result
    }

    fun makeConstrainedDateTime(
        id: Uuid = makeUuid(),
        initialize: ConstrainedDateTime.() -> Unit = {}
    ): ConstrainedDateTime {
        val result = ConstrainedDateTime(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeConstrainedFloat64(
        id: Uuid = makeUuid(),
        initialize: ConstrainedFloat64.() -> Unit = {}
    ): ConstrainedFloat64 {
        val result = ConstrainedFloat64(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeConstrainedInteger32(
        id: Uuid = makeUuid(),
        initialize: ConstrainedInteger32.() -> Unit = {}
    ): ConstrainedInteger32 {
        val result = ConstrainedInteger32(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeConstrainedString(
        id: Uuid = makeUuid(),
        initialize: ConstrainedString.() -> Unit = {}
    ): ConstrainedString {
        val result = ConstrainedString(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeConstrainedUuid(
        id: Uuid = makeUuid(),
        initialize: ConstrainedUuid.() -> Unit = {}
    ): ConstrainedUuid {
        val result = ConstrainedUuid(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makePackage(
        id: Uuid = makeUuid(),
        initialize: Package.() -> Unit = {}
    ): Package {
        val result = Package(id, false)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makePackageContainment(
        parent: Package,
        child: Package,
        id: Uuid = makeUuid()
    ): PackageContainment {
        val result = PackageContainment(id, parent, child)
        _edges.add(result)
        return result
    }

    fun makePackageDependency(
        consumer: Package,
        supplier: Package,
        id: Uuid = makeUuid()
    ): PackageDependency {
        val result = PackageDependency(id, consumer, supplier)
        _edges.add(result)
        return result
    }

    fun makeUndirectedEdgeType(
        id: Uuid = makeUuid(),
        initialize: UndirectedEdgeType.() -> Unit = {}
    ): UndirectedEdgeType {
        val result = UndirectedEdgeType(id, false)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeUndirectedEdgeTypeConnectivity(
        connectingEdgeType: UndirectedEdgeType,
        connectedVertexType: VertexType,
        id: Uuid = makeUuid()
    ): UndirectedEdgeTypeConnectivity {
        val result = UndirectedEdgeTypeConnectivity(id, connectingEdgeType, connectedVertexType)
        _edges.add(result)
        return result
    }

    fun makeUndirectedEdgeTypeContainment(
        parent: Package,
        child: UndirectedEdgeType,
        id: Uuid = makeUuid()
    ): UndirectedEdgeTypeContainment {
        val result = UndirectedEdgeTypeContainment(id, parent, child)
        _edges.add(result)
        return result
    }

    fun makeUndirectedEdgeTypeInheritance(
        superType: UndirectedEdgeType,
        subType: UndirectedEdgeType,
        id: Uuid = makeUuid()
    ): UndirectedEdgeTypeInheritance {
        val result = UndirectedEdgeTypeInheritance(id, superType, subType)
        _edges.add(result)
        return result
    }

    fun makeVertexAttributeType(
        id: Uuid = makeUuid(),
        initialize: VertexAttributeType.() -> Unit = {}
    ): VertexAttributeType {
        val result = VertexAttributeType(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeVertexAttributeTypeContainment(
        parent: VertexType,
        child: VertexAttributeType,
        id: Uuid = makeUuid()
    ): VertexAttributeTypeContainment {
        val result = VertexAttributeTypeContainment(id, parent, child)
        _edges.add(result)
        return result
    }

    fun makeVertexType(
        id: Uuid = makeUuid(),
        initialize: VertexType.() -> Unit = {}
    ): VertexType {
        val result = VertexType(id, false)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeVertexTypeContainment(
        parent: Package,
        child: VertexType,
        id: Uuid = makeUuid()
    ): VertexTypeContainment {
        val result = VertexTypeContainment(id, parent, child)
        _edges.add(result)
        return result
    }

    fun makeVertexTypeInheritance(
        superType: VertexType,
        subType: VertexType,
        id: Uuid = makeUuid()
    ): VertexTypeInheritance {
        val result = VertexTypeInheritance(id, superType, subType)
        _edges.add(result)
        return result
    }

}
