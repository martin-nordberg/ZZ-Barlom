//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.model

import org.barlom.domain.metamodel.api.edges2.PackageContainment
import org.barlom.domain.metamodel.api.edges2.PackageDependency
import org.barlom.domain.metamodel.api.edges2.VertexTypeContainment
import org.barlom.domain.metamodel.api.edges2.VertexTypeInheritance
import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.domain.metamodel.api.vertices2.VertexType
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

class Model(

    /** The unique ID for the root package within this model. */
    rootPackageId: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root vertex type within this model. */
    rootVertexTypeId: Uuid = Uuid.fromString("66522301-6c7d-11e7-81b7-080027b6d283")

) {

    private val _edges: VLinkedList<AbstractDocumentedElement>

    private val _vertices: VLinkedList<AbstractPackagedElement>


    val edges: List<AbstractDocumentedElement>
        get() = _edges.asList()

    val revHistory = RevisionHistory("Initial empty model.")

    val rootPackage: Package

    val rootVertexType: VertexType

    val vertices: List<AbstractPackagedElement>
        get() = _vertices.sortedBy { e -> e.path }


    init {

        var edges: VLinkedList<AbstractDocumentedElement>? = null
        var vertices: VLinkedList<AbstractPackagedElement>? = null

        revHistory.update("Graph initialized.", 0) {
            edges = VLinkedList()
            vertices = VLinkedList()
        }

        _edges = edges!!
        _vertices = vertices!!


        var rootPkg: Package? = null
        var rootVT: VertexType? = null

        revHistory.update("Root elements added.", 0) {
            rootPkg = Package(rootPackageId, true)
            _vertices.add(rootPkg!!)

            rootVT = VertexType(rootVertexTypeId, true)
            _vertices.add(rootVT!!)

            _edges.add(VertexTypeContainment(makeUuid(), rootPkg!!, rootVT!!))
        }

        rootPackage = rootPkg!!
        rootVertexType = rootVT!!

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
