//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.model

import org.barlom.domain.metamodel.api.edges2.PackageContainment
import org.barlom.domain.metamodel.api.edges2.PackageDependency
import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

class Model(

    rootPackageId: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283")

) {

    private val _edges: VLinkedList<AbstractDocumentedElement>

    private val _vertices: VLinkedList<AbstractPackagedElement>


    val edges: List<AbstractDocumentedElement>
        get() = _edges.asList()

    val revHistory = RevisionHistory("Initial empty model.")

    val rootPackage: Package

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

        revHistory.update("Root elements added.", 0) {
            rootPkg = Package(rootPackageId, "", true) {}
            _vertices.add(rootPkg!!)
        }

        rootPackage = rootPkg!!

    }


    fun makePackage(
        id: Uuid = makeUuid(),
        initialize: Package.() -> Unit = {}
    ): Package {
        val result = Package(id, "newpackage", false, initialize)
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

}
