//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.model

import org.barlom.domain.metamodel.api.edges.*
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

class Model(

    /** Function for creating new UUIDs */
    private val makeUuid: () -> Uuid,

    /** The unique ID for the root package within this model. */
    rootPackageId: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root vertex type within this model. */
    rootVertexTypeId: Uuid = Uuid.fromString("66522301-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root directed edge type within this model. */
    rootUndirectedEdgeTypeId: Uuid = Uuid.fromString("66522302-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root undirected edge type within this model. */
    rootDirectedEdgeTypeId: Uuid = Uuid.fromString("66522303-6c7d-11e7-81b7-080027b6d283"),

    /** First revision of the model. */
    initialize: Model.() -> Unit = {}

) {

    private val _edges: VLinkedList<AbstractElement> = VLinkedList()

    private val _vertices: VLinkedList<AbstractNamedElement> = VLinkedList()


    val edges: List<AbstractElement>
        get() = _edges.asList()

    val revHistory = RevisionHistory.currentlyInUse

    val rootDirectedEdgeType = DirectedEdgeType(rootDirectedEdgeTypeId, true)

    val rootPackage = Package(rootPackageId, true)

    val rootUndirectedEdgeType = UndirectedEdgeType(rootUndirectedEdgeTypeId, true)

    val rootVertexType = VertexType(rootVertexTypeId, true)

    val vertices: List<AbstractNamedElement>
        get() = _vertices.sortedBy { e -> e.path }


    init {

        _vertices.add(rootPackage)
        _vertices.add(rootVertexType)
        _vertices.add(rootDirectedEdgeType)
        _vertices.add(rootUndirectedEdgeType)

        _edges.add(VertexTypeContainment(makeUuid(), rootPackage, rootVertexType))
        _edges.add(DirectedEdgeTypeContainment(makeUuid(), rootPackage, rootDirectedEdgeType))
        _edges.add(UndirectedEdgeTypeContainment(makeUuid(), rootPackage, rootUndirectedEdgeType))

        initialize()

    }


    fun findVertexById( id:Uuid ): AbstractNamedElement? {
        // TODO: need a versioned map class for better performance
        return _vertices.find { v -> v.id == id }
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

    fun makeDirectedEdgeType(
        id: Uuid = makeUuid(),
        initialize: DirectedEdgeType.() -> Unit = {}
    ): DirectedEdgeType {
        val result = DirectedEdgeType(id, false)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeDirectedEdgeTypeHeadConnectivity(
        connectingEdgeType: DirectedEdgeType,
        connectedVertexType: VertexType,
        id: Uuid = makeUuid()
    ): DirectedEdgeTypeHeadConnectivity {
        val result = DirectedEdgeTypeHeadConnectivity(id, connectingEdgeType,
                                                      connectedVertexType)
        _edges.add(result)
        return result
    }

    fun makeDirectedEdgeTypeTailConnectivity(
        connectingEdgeType: DirectedEdgeType,
        connectedVertexType: VertexType,
        id: Uuid = makeUuid()
    ): DirectedEdgeTypeTailConnectivity {
        val result = DirectedEdgeTypeTailConnectivity(id, connectingEdgeType,
                                                      connectedVertexType)
        _edges.add(result)
        return result
    }

    fun makeDirectedEdgeTypeContainment(
        parent: Package,
        child: DirectedEdgeType,
        id: Uuid = makeUuid()
    ): DirectedEdgeTypeContainment {
        val result = DirectedEdgeTypeContainment(id, parent, child)
        _edges.add(result)
        return result
    }

    fun makeDirectedEdgeTypeInheritance(
        superType: DirectedEdgeType,
        subType: DirectedEdgeType,
        id: Uuid = makeUuid()
    ): DirectedEdgeTypeInheritance {
        val result = DirectedEdgeTypeInheritance(id, superType, subType)
        _edges.add(result)
        return result
    }

    fun makeEdgeAttributeType(
        id: Uuid = makeUuid(),
        initialize: EdgeAttributeType.() -> Unit = {}
    ): EdgeAttributeType {
        val result = EdgeAttributeType(id)
        result.initialize()
        _vertices.add(result)
        return result
    }

    fun makeEdgeAttributeTypeContainment(
        parent: AbstractEdgeType,
        child: EdgeAttributeType,
        id: Uuid = makeUuid()
    ): EdgeAttributeTypeContainment {
        val result = EdgeAttributeTypeContainment(id, parent, child)
        _edges.add(result)
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
        val result = UndirectedEdgeTypeConnectivity(id, connectingEdgeType,
                                                    connectedVertexType)
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
