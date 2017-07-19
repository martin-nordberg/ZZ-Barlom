//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IPackage
import org.barlom.domain.metamodel.api.elements.IPackageDependency
import org.barlom.domain.metamodel.api.types.EDependencyDepth
import org.barlom.domain.metamodel.api.types.Uuid

/**
 * Implementation class for Barlom non-root packages.
 */
internal data class Package(

    override val id: Uuid,
    override val name: String,
    override val parentPackage: IPackageImpl

) : INonRootPackageImpl {

    /** The child packages within this package. */
    private val _childPackages: MutableList<Package> = mutableListOf()

    /** Links to packages that are clients of this package. */
    private val _clientPackageDependencies: MutableList<PackageDependency> = mutableListOf()

    /** The constrained data types within this package. */
    private val _constrainedDataTypes: MutableList<ConstrainedDataType> = mutableListOf()

    /** The directed edge types within this package. */
    private val _directedEdgeTypes: MutableList<DirectedEdgeType> = mutableListOf()

    /** Links to packages that are suppliers of this package. */
    private val _supplierPackageDependencies: MutableList<PackageDependency> = mutableListOf()

    /** The edge types within this package. */
    private val _undirectedEdgeTypes: MutableList<UndirectedEdgeType> = mutableListOf()

    /** The vertex types within this package. */
    private val _vertexTypes: MutableList<VertexType> = mutableListOf()


    init {
        parentPackage.addChildPackage(this)
    }


    override val childPackages: List<Package>
        get() = _childPackages

    override val clientPackageDependencies: List<IPackageDependency>
        get() = _clientPackageDependencies

    override val constrainedDataTypes: List<ConstrainedDataType>
        get() = _constrainedDataTypes

    override val directedEdgeTypes: List<DirectedEdgeType>
        get() = _directedEdgeTypes

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
        get() = _supplierPackageDependencies

    override val undirectedEdgeTypes: List<UndirectedEdgeType>
        get() = _undirectedEdgeTypes

    override val vertexTypes: List<VertexType>
        get() = _vertexTypes


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

    override fun getClientPackages(dependencyDepth: EDependencyDepth): List<IPackage> {

        val result: MutableSet<IPackage> = mutableSetOf()

        for (pkg in _clientPackageDependencies) {

            result.add(pkg.clientPackage)

            if (dependencyDepth.isTransitive()) {
                result.addAll(pkg.clientPackage.getClientPackages(dependencyDepth))
            }

        }

        return result.toList().sortedBy { pkg2 -> pkg2.name }

    }

    override fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<IPackage> {

        val result: MutableSet<IPackage> = mutableSetOf()

        for (pkg in _supplierPackageDependencies) {

            result.add(pkg.supplierPackage)

            if (dependencyDepth.isTransitive()) {
                result.addAll(pkg.supplierPackage.getSupplierPackages(dependencyDepth))
            }

        }

        return result.toList().sortedBy { pkg2 -> pkg2.name }

    }

    override fun hasClientPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean {

        for (pkgdep in _clientPackageDependencies) {

            val pkg2 = pkgdep.clientPackage

            if (pkg === pkg2) {
                return true
            }

            if (dependencyDepth.isTransitive()) {

                if (pkg2 !== this && pkg2.hasClientPackage(pkg, dependencyDepth)) {
                    return true
                }

            }

        }

        return false

    }

    override fun hasSupplierPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean {

        for (pkgdep in _supplierPackageDependencies) {

            val pkg2 = pkgdep.supplierPackage

            if (pkg === pkg2) {
                return true
            }

            if (dependencyDepth.isTransitive()) {

                if (pkg2 !== this && pkg2.hasSupplierPackage(pkg, dependencyDepth)) {
                    return true
                }

            }

        }

        return false

    }

    override fun isChildOf(pkg: IPackage): Boolean {
        return pkg === this.parentPackage || this.parentPackage.isChildOf(pkg)
    }

}
