//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.edges.IPackageContainment
import org.barlom.domain.metamodel.api.edges.IPackageDependency
import org.barlom.domain.metamodel.api.vertices.IPackage
import org.barlom.domain.metamodel.impl.edges.PackageContainment
import org.barlom.domain.metamodel.impl.edges.PackageDependency
import org.barlom.domain.metamodel.impl.edges.VertexTypeContainment
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

/**
 * Implementation class for Barlom non-root packages.
 */
internal class Package(

    override val id: Uuid,
    name: String,
    initialize: Package.() -> Unit

) : INonRootPackageImpl {

    private val _childPackageContainments = VLinkedList<PackageContainment>()
    private val _clientPackageDependencies = VLinkedList<PackageDependency>()
    private val _constrainedDataTypes = VLinkedList<ConstrainedDataType>()
    private val _directedEdgeTypes = VLinkedList<DirectedEdgeType>()
    private val _name = V(name)
    private val _parentPackageContainments = VLinkedList<PackageContainment>()
    private val _supplierPackageDependencies = VLinkedList<PackageDependency>()
    private val _undirectedEdgeTypes = VLinkedList<UndirectedEdgeType>()
    private val _vertexTypeContainments = VLinkedList<VertexTypeContainment>()


    init {
        initialize()
    }

    override val childPackages: List<Package>
        get() = _childPackageContainments.map { c -> c.child }.sortedBy { pkg -> pkg.name }

    override val childPackageContainments: List<IPackageContainment>
        get() = _childPackageContainments.sortedBy { pkgdep -> pkgdep.child.path }

    override val clientPackageDependencies: List<IPackageDependency>
        get() = _clientPackageDependencies.sortedBy { pkgdep -> pkgdep.clientPackage.path }

    override val clientPackages: List<IPackage>
        get() = _clientPackageDependencies.map { it.clientPackage }.sortedBy { pkg2 -> pkg2.path }

    override val constrainedDataTypes: List<ConstrainedDataType>
        get() = _constrainedDataTypes.sortedBy { dt -> dt.name }

    override val directedEdgeTypes: List<DirectedEdgeType>
        get() = _directedEdgeTypes.sortedBy { e -> e.name }

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

    override val parentPackages: List<IPackageImpl>
        get() = _parentPackageContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val parentPackageContainments: List<IPackageContainment>
        get() = _parentPackageContainments.sortedBy { pkgdep -> pkgdep.child.path }

    override val path: String
        get() {

            if (_parentPackageContainments.isEmpty) {
                return name
            }

            val parentPath = parentPackages[0].path

            if ( parentPath.isEmpty() ) {
                return name
            }

            return parentPath + "." + name

        }

    override val supplierPackageDependencies: List<IPackageDependency>
        get() = _supplierPackageDependencies.sortedBy { pkgdep -> pkgdep.supplierPackage.path }

    override val supplierPackages: List<IPackage>
        get() = _supplierPackageDependencies.map { it.supplierPackage }.sortedBy { pkg2 -> pkg2.path }

    override val transitiveClientPackages: List<IPackage>
        get() {

            val result: MutableSet<IPackage> = mutableSetOf()

            // Helper function recursively accumulates the result
            fun accumulateClientPackages(pkg: Package) {

                pkg._clientPackageDependencies.forEach { pkgDep ->

                    val clientPkg = pkgDep.clientPackage

                    if (!result.contains(clientPkg)) {
                        result.add(clientPkg)
                        accumulateClientPackages(clientPkg)
                    }

                }

            }

            accumulateClientPackages(this)

            return result.toList().sortedBy { pkg2 -> pkg2.path }

        }

    override val transitiveSupplierPackages: List<IPackage>
        get() {

            val result: MutableSet<IPackage> = mutableSetOf()

            // Helper function recursively accumulates the result
            fun accumulateSupplierPackages(pkg: Package) {

                pkg._supplierPackageDependencies.forEach { pkgDep ->

                    val supplierPkg = pkgDep.supplierPackage

                    if (!result.contains(supplierPkg)) {
                        result.add(supplierPkg)
                        accumulateSupplierPackages(supplierPkg)
                    }

                }

            }

            accumulateSupplierPackages(this)

            return result.toList().sortedBy { pkg2 -> pkg2.path }

        }

    override val undirectedEdgeTypes: List<UndirectedEdgeType>
        get() = _undirectedEdgeTypes.sortedBy { e -> e.name }

    override val vertexTypes: List<VertexType>
        get() = _vertexTypeContainments.map { c -> c.child }.sortedBy { v -> v.name }


    override fun addConstrainedDataType(constrainedDataType: ConstrainedDataType) {

//        require(constrainedDataType.parentPackage === this) {
//            "Cannot add a constrained data type to a package not its parent."
//        }

        _constrainedDataTypes.add(constrainedDataType)

    }

    override fun addChildPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.parent === this) {
            "Child package containment can only be added to its parent."
        }

        _childPackageContainments.add(packageContainment)

    }

    override fun addClientPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.supplierPackage === this) {
            "Client package dependency can only be added to its supplier."
        }

        _clientPackageDependencies.add(packageDependency)

    }

    override fun addDirectedEdgeType(edgeType: DirectedEdgeType) {

//        require(edgeType.parentPackage === this) {
//            "Cannot add a directed edge type to a package not its parent."
//        }

        _directedEdgeTypes.add(edgeType)

    }

    override fun addParentPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.child === this) {
            "Parent package containment can only be added to its child."
        }

        _parentPackageContainments.add(packageContainment)

    }

    override fun addSupplierPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.clientPackage === this) {
            "Supplier package dependency can only be added to its client."
        }

        _supplierPackageDependencies.add(packageDependency)

    }

    override fun addUndirectedEdgeType(edgeType: UndirectedEdgeType) {

//        require(edgeType.parentPackage === this) {
//            "Cannot add an undirected edge type to a package not its parent."
//        }

        _undirectedEdgeTypes.add(edgeType)

    }

    override fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(vertexTypeContainment.parent === this) {
            "Cannot add a vertex type to a package not its parent."
        }

        _vertexTypeContainments.add(vertexTypeContainment)

    }

    override fun containedBy(pkg: IPackageImpl) {
        PackageContainment(makeUuid(), pkg, this)
    }

    override fun hasClientPackage(pkg: IPackage): Boolean {

        var result = false

        _clientPackageDependencies.forEachWhile { pkgdep ->

            result = pkg == pkgdep.clientPackage
            !result

        }

        return result

    }

    override fun hasSupplierPackage(pkg: IPackage): Boolean {

        var result = false

        _supplierPackageDependencies.forEachWhile { pkgdep ->

            result = pkg == pkgdep.supplierPackage
            !result

        }

        return result

    }

    override fun hasTransitiveClientPackage(pkg: IPackage): Boolean {

        val clients: MutableSet<IPackage> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findClientPackage(supplierPkg: Package): Boolean {

            for (pkgDep in supplierPkg._clientPackageDependencies.asList()) {

                val clientPkg = pkgDep.clientPackage

                if (clientPkg === pkg) {
                    return true
                }

                if (!clients.contains(clientPkg)) {

                    clients.add(clientPkg)

                    if (findClientPackage(clientPkg)) {
                        return true
                    }

                }

            }

            return false

        }

        return findClientPackage(this)

    }

    override fun hasTransitiveSupplierPackage(pkg: IPackage): Boolean {

        val suppliers: MutableSet<IPackage> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findSupplierPackage(clientPkg: Package): Boolean {

            for (pkgDep in clientPkg._supplierPackageDependencies.asList()) {

                val supplierPkg = pkgDep.supplierPackage

                if (supplierPkg === pkg) {
                    return true
                }

                if (!suppliers.contains(supplierPkg)) {

                    suppliers.add(supplierPkg)

                    if (findSupplierPackage(supplierPkg)) {
                        return true
                    }

                }

            }

            return false

        }

        return findSupplierPackage(this)

    }

    override fun isChildOf(pkg: IPackage): Boolean {
        if ( _parentPackageContainments.isEmpty || this === pkg ) {
            return false
        }
        val parentPackage = this.parentPackageContainments[0].parent
        return pkg === parentPackage || parentPackage.isChildOf(pkg)
    }

}
