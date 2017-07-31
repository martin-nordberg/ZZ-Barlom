//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IPackage
import org.barlom.domain.metamodel.api.elements.IPackageDependency
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for Barlom non-root packages.
 */
internal class Package(

    override val id: Uuid,
    name: String,
    parentPackage: IPackageImpl

) : INonRootPackageImpl {

    private val _childPackages = VLinkedList<Package>()
    private val _clientPackageDependencies = VLinkedList<PackageDependency>()
    private val _constrainedDataTypes = VLinkedList<ConstrainedDataType>()
    private val _directedEdgeTypes = VLinkedList<DirectedEdgeType>()
    private val _name = V(name)
    private val _parentPackage = V(parentPackage)
    private val _supplierPackageDependencies = VLinkedList<PackageDependency>()
    private val _undirectedEdgeTypes = VLinkedList<UndirectedEdgeType>()
    private val _vertexTypes = VLinkedList<VertexType>()


    init {
        parentPackage.addChildPackage(this)
    }


    override val childPackages: List<Package>
        get() = _childPackages.asSortedList { pkg -> pkg.name }

    override val clientPackageDependencies: List<IPackageDependency>
        get() = _clientPackageDependencies.asSortedList { pkgdep -> pkgdep.clientPackage.path }

    override val clientPackages: List<IPackage>
        get() {

            val result: MutableSet<IPackage> = mutableSetOf()

            for (pkg in _clientPackageDependencies.asList()) {

                result.add(pkg.clientPackage)

            }

            return result.toList().sortedBy { pkg2 -> pkg2.path }

        }

    override val constrainedDataTypes: List<ConstrainedDataType>
        get() = _constrainedDataTypes.asSortedList { dt -> dt.name }

    override val directedEdgeTypes: List<DirectedEdgeType>
        get() = _directedEdgeTypes.asSortedList { e -> e.name }

    override val name: String
        get() = _name.get()

    override val parentPackage: IPackageImpl
        get() = _parentPackage.get()

    override val path: String
        get() {
            var result: String = parentPackage.path
            if (result.isEmpty()) {
                result = name
            }
            else {
                result += "."
                result += name
            }
            return result
        }

    override val supplierPackageDependencies: List<IPackageDependency>
        get() = _supplierPackageDependencies.asSortedList { pkgdep -> pkgdep.supplierPackage.path }

    override val supplierPackages: List<IPackage>
        get() {

            val result: MutableSet<IPackage> = mutableSetOf()

            for (pkg in _supplierPackageDependencies.asList()) {

                result.add(pkg.supplierPackage)

            }

            return result.toList().sortedBy { pkg2 -> pkg2.path }

        }

    override val transitiveClientPackages: List<IPackage>
        get() {

            val result: MutableSet<IPackage> = mutableSetOf()

            // Helper function recursively accumulates the result
            fun accumulateClientPackages(pkg: Package) {

                for (pkgDep in pkg._clientPackageDependencies.asList()) {

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

                for (pkgDep in pkg._supplierPackageDependencies.asList()) {

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
        get() = _undirectedEdgeTypes.asSortedList { e -> e.name }

    override val vertexTypes: List<VertexType>
        get() = _vertexTypes.asSortedList { v -> v.name }


    override fun addConstrainedDataType(constrainedDataType: ConstrainedDataType) {

        require(constrainedDataType.parentPackage === this) {
            "Cannot add a constrained data type to a package not its parent."
        }

        _constrainedDataTypes.add(constrainedDataType)

    }

    override fun addChildPackage(pkg: Package) {

        require(pkg.parentPackage === this) {
            "Cannot add a child package element to a package not its parent."
        }

        _childPackages.add(pkg)

    }

    override fun addClientPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.supplierPackage === this) {
            "Client package dependency can only be added to its supplier."
        }

        _clientPackageDependencies.add(packageDependency)

    }

    override fun addDirectedEdgeType(edgeType: DirectedEdgeType) {

        require(edgeType.parentPackage === this) {
            "Cannot add a directed edge type to a package not its parent."
        }

        _directedEdgeTypes.add(edgeType)

    }

    override fun addSupplierPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.clientPackage === this) {
            "Supplier package dependency can only be added to its client."
        }

        _supplierPackageDependencies.add(packageDependency)

    }

    override fun addUndirectedEdgeType(edgeType: UndirectedEdgeType) {

        require(edgeType.parentPackage === this) {
            "Cannot add an undirected edge type to a package not its parent."
        }

        _undirectedEdgeTypes.add(edgeType)

    }

    override fun addVertexType(vertexType: VertexType) {

        require(vertexType.parentPackage === this) {
            "Cannot add a vertex type to a package not its parent."
        }

        _vertexTypes.add(vertexType)

    }

    override fun hasClientPackage(pkg: IPackage): Boolean {

        for (pkgdep in _clientPackageDependencies.asList()) {

            val pkg2 = pkgdep.clientPackage

            if (pkg === pkg2) {
                return true
            }

        }

        return false

    }

    override fun hasSupplierPackage(pkg: IPackage): Boolean {

        for (pkgdep in _supplierPackageDependencies.asList()) {

            val pkg2 = pkgdep.supplierPackage

            if (pkg === pkg2) {
                return true
            }

        }

        return false

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
        return pkg === this.parentPackage || this.parentPackage.isChildOf(pkg)
    }

}
