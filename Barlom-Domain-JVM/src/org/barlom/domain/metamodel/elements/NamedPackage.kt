//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EDependencyDepth

/**
 * Implementation class for Barlom non-root packages.
 */
class NamedPackage(

    id: String,
    name: String,

    /** The parent package of this attribute type. */
    override val parentPackage: Package

) : Package(id, name) {

    internal fun addAttributeType(attributeType: AttributeType) {

        require(attributeType.parentPackage === this) {
            "Cannot add an attribute type to a package not its parent."
        }

        _attributeTypes.add(attributeType)

    }

    internal fun addClientPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.supplierPackage === this) {
            "Client package dependency can only be added to its supplier."
        }

        _clientPackageDependencies.add(packageDependency)

    }

    internal fun addSupplierPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.clientPackage === this) {
            "Supplier package dependency can only be added to its client."
        }

        _supplierPackageDependencies.add(packageDependency)

    }

    internal fun addEdgeType(edgeType: EdgeType) {

        require(edgeType.parentPackage === this) {
            "Cannot add an edge type to a package not its parent."
        }

        _edgeTypes.add(edgeType)

    }

    internal fun addVertexType(vertexType: VertexType) {

        require(vertexType.parentPackage === this) {
            "Cannot add a vertex type to a package not its parent."
        }

        _vertexTypes.add(vertexType)

    }

    override val attributeTypes: List<AttributeType>
        get() = _attributeTypes

    override fun getClientPackages(dependencyDepth: EDependencyDepth): List<NamedPackage> {

        val result: MutableSet<NamedPackage> = mutableSetOf()

        for (pkg in _clientPackageDependencies) {

            result.add(pkg.clientPackage)

            if (dependencyDepth.isTransitive()) {
                result.addAll(pkg.clientPackage.getClientPackages(dependencyDepth))
            }

        }

        return result.toList().sortedBy { pkg2 -> pkg2.name }

    }

    override val edgeTypes: List<EdgeType>
        get() = _edgeTypes

    override fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<NamedPackage> {

        val result: MutableSet<NamedPackage> = mutableSetOf()

        for (pkg in _supplierPackageDependencies) {

            result.add(pkg.supplierPackage)

            if (dependencyDepth.isTransitive()) {
                result.addAll(pkg.supplierPackage.getSupplierPackages(dependencyDepth))
            }

        }

        return result.toList().sortedBy { pkg2 -> pkg2.name }

    }

    override fun hasClientPackage(pkg: NamedPackage, dependencyDepth: EDependencyDepth): Boolean {

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

    override fun hasSupplierPackage(pkg: NamedPackage, dependencyDepth: EDependencyDepth): Boolean {

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

    override fun isChildOf(pkg: Package): Boolean {
        return pkg === this.parentPackage || this.parentPackage.isChildOf(pkg)
    }

    override val path: String
        get() = parentPackage.path + "." + name

    override val vertexTypes: List<VertexType>
        get() = _vertexTypes

    /** The attribute types within this package. */
    private val _attributeTypes: MutableList<AttributeType> = mutableListOf()

    /** Links to packages that are clients of this package. */
    private val _clientPackageDependencies: MutableList<PackageDependency> = mutableListOf()

    /** The edge types within this package. */
    private val _edgeTypes: MutableList<EdgeType> = mutableListOf()

    /** Links to packages that are suppliers of this package. */
    private val _supplierPackageDependencies: MutableList<PackageDependency> = mutableListOf()

    /** The vertex types within this package. */
    private val _vertexTypes: MutableList<VertexType> = mutableListOf()

}
