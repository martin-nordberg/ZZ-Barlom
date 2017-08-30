//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.edges2.PackageContainment
import org.barlom.domain.metamodel.api.edges2.PackageDependency
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom packages. Packages are the namespacing mechanism for containing vertex types, edge types,
 * constrained data types, and child packages.
 */
class Package internal constructor(

    override val id: Uuid,
    val isRoot: Boolean

) : AbstractPackagedElement() {

    private val _childPackageContainments = VLinkedList<PackageContainment>()
    private val _consumerPackageDependencies = VLinkedList<PackageDependency>()
    private val _name = V(if (isRoot) "" else "newpackage")
    private val _parentPackageContainments = VLinkedList<PackageContainment>()
    private val _supplierPackageDependencies = VLinkedList<PackageDependency>()


    /** The child sub-packages within this package. */
    val childPackages: List<Package>
        get() = _childPackageContainments.map { c -> c.child }.sortedBy { pkg -> pkg.name }

    /** Links to packages that are direct children of this package. */
    val childPackageContainments: List<PackageContainment>
        get() = _childPackageContainments.sortedBy { c -> c.child.name }

    /** The consumer package links within this package. */
    val consumerPackages: List<Package>
        get() = _consumerPackageDependencies.map { c -> c.consumer }.sortedBy { pkg -> pkg.path }

    /** Links to packages that are direct consumers of this package. */
    val consumerPackageDependencies: List<PackageDependency>
        get() = _consumerPackageDependencies.sortedBy { c -> c.consumer.path }

    override var name: String
        get() = _name.get()
        set(value) {

            check(!isRoot) {
                "Root package name cannot be changed"
            }

            _name.set(value)

        }

    override val parentPackages: List<Package>
        get() = _parentPackageContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    /** The package containment linking this package to its parent. */
    val parentPackageContainments: List<PackageContainment>
        get() = _parentPackageContainments.sortedBy { pkgdep -> pkgdep.child.path }

    override val path: String
        get() {

            if (_parentPackageContainments.isEmpty) {
                return name
            }

            val parentPath = parentPackages[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }

    /** The supplier package links within this package. */
    val supplierPackages: List<Package>
        get() = _supplierPackageDependencies.map { c -> c.supplier }.sortedBy { pkg -> pkg.path }

    /** Links to packages that are direct suppliers of this package. */
    val supplierPackageDependencies: List<PackageDependency>
        get() = _supplierPackageDependencies.sortedBy { c -> c.supplier.path }

    val transitiveConsumerPackages: List<Package>
        get() {

            val result: MutableSet<Package> = mutableSetOf()

            // Helper function recursively accumulates the result
            fun accumulateConsumerPackages(pkg: Package) {

                pkg._consumerPackageDependencies.forEach { pkgDep ->

                    val consumerPkg = pkgDep.consumer

                    if (!result.contains(consumerPkg)) {
                        result.add(consumerPkg)
                        accumulateConsumerPackages(consumerPkg)
                    }

                }

            }

            accumulateConsumerPackages(this)

            return result.toList().sortedBy { pkg2 -> pkg2.path }

        }

    val transitiveSupplierPackages: List<Package>
        get() {

            val result: MutableSet<Package> = mutableSetOf()

            // Helper function recursively accumulates the result
            fun accumulateSupplierPackages(pkg: Package) {

                pkg._supplierPackageDependencies.forEach { pkgDep ->

                    val supplierPkg = pkgDep.supplier

                    if (!result.contains(supplierPkg)) {
                        result.add(supplierPkg)
                        accumulateSupplierPackages(supplierPkg)
                    }

                }

            }

            accumulateSupplierPackages(this)

            return result.toList().sortedBy { pkg2 -> pkg2.path }

        }


    /** Registers the given package containment in this package. */
    internal fun addChildPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.parent === this) {
            "Child package containment can only be added to its parent."
        }

        _childPackageContainments.add(packageContainment)

    }

    /** Registers the given package dependency in this package. */
    internal fun addConsumerPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.supplier === this) {
            "Consumer package dependency can only be added to its supplier."
        }

        _consumerPackageDependencies.add(packageDependency)

    }

    /** Registers the given package containment in this package. */
    internal fun addParentPackageContainment(packageContainment: PackageContainment) {

        check(!isRoot) {
            "Root package cannot have a parent."
        }

        require(packageContainment.child === this) {
            "Parent package containment can only be added to its child."
        }

        _parentPackageContainments.add(packageContainment)

    }

    /** Registers the given package dependency in this package. */
    internal fun addSupplierPackageDependency(packageDependency: PackageDependency) {

        require(packageDependency.consumer === this) {
            "Supplier package dependency can only be added to its consumer."
        }

        _supplierPackageDependencies.add(packageDependency)

    }

    fun hasConsumerPackage(pkg: Package): Boolean {

        var result = false

        _consumerPackageDependencies.forEachWhile { pkgdep ->

            result = pkg == pkgdep.consumer
            !result

        }

        return result

    }

    fun hasSupplierPackage(pkg: Package): Boolean {

        var result = false

        _supplierPackageDependencies.forEachWhile { pkgdep ->

            result = pkg == pkgdep.supplier
            !result

        }

        return result

    }

    fun hasTransitiveConsumerPackage(pkg: Package): Boolean {

        val consumers: MutableSet<Package> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findConsumerPackage(supplierPkg: Package): Boolean {

            for (pkgDep in supplierPkg._consumerPackageDependencies.asList()) {

                val consumerPkg = pkgDep.consumer

                if (consumerPkg === pkg) {
                    return true
                }

                if (!consumers.contains(consumerPkg)) {

                    consumers.add(consumerPkg)

                    if (findConsumerPackage(consumerPkg)) {
                        return true
                    }

                }

            }

            return false

        }

        return findConsumerPackage(this)

    }

    fun hasTransitiveSupplierPackage(pkg: Package): Boolean {

        val suppliers: MutableSet<Package> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findSupplierPackage(consumerPkg: Package): Boolean {

            for (pkgDep in consumerPkg._supplierPackageDependencies.asList()) {

                val supplierPkg = pkgDep.supplier

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

    /** Whether this package is a direct or indirect child of the given package. */
    fun isChildOf(pkg: Package): Boolean {

        if (_parentPackageContainments.isEmpty || this === pkg) {
            return false
        }

        val parentPackage = this.parentPackageContainments[0].parent
        return pkg === parentPackage || parentPackage.isChildOf(pkg)

    }

}
