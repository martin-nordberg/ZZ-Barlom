//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.edges2.ConstrainedDataTypeContainment
import org.barlom.domain.metamodel.api.edges2.PackageContainment
import org.barlom.domain.metamodel.api.edges2.PackageDependency
import org.barlom.domain.metamodel.api.edges2.VertexTypeContainment
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
    private val _constrainedDataTypeContainments = VLinkedList<ConstrainedDataTypeContainment>()
    private val _consumerPackageDependencies = VLinkedList<PackageDependency>()
    private val _name = V(if (isRoot) "" else "newpackage")
    private val _parentPackageContainments = VLinkedList<PackageContainment>()
    private val _supplierPackageDependencies = VLinkedList<PackageDependency>()
    private val _vertexTypeContainments = VLinkedList<VertexTypeContainment>()


    /** The child sub-packages within this package. */
    val children: List<Package>
        get() = _childPackageContainments.map { c -> c.child }.sortedBy { pkg -> pkg.name }

    /** Links to packages that are direct children of this package. */
    val childPackageContainments: List<PackageContainment>
        get() = _childPackageContainments.sortedBy { c -> c.child.name }

    /** The constrained data types within this package. */
    val constrainedDataTypes: List<ConstrainedDataType>
        get() = _constrainedDataTypeContainments.map { c -> c.child }.sortedBy { vt -> vt.name }

    /** Links to packages that are direct children of this package. */
    val constrainedDataTypeContainments: List<ConstrainedDataTypeContainment>
        get() = _constrainedDataTypeContainments.sortedBy { c -> c.child.name }

    /** The consumer package links within this package. */
    val consumers: List<Package>
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

    override val parents: List<Package>
        get() = _parentPackageContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    /** The package containment linking this package to its parent. */
    val parentPackageContainments: List<PackageContainment>
        get() = _parentPackageContainments.sortedBy { pkgdep -> pkgdep.child.path }

    override val path: String
        get() {

            if (_parentPackageContainments.isEmpty) {
                return name
            }

            val parentPath = parents[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }

    /** The supplier package links within this package. */
    val suppliers: List<Package>
        get() = _supplierPackageDependencies.map { c -> c.supplier }.sortedBy { pkg -> pkg.path }

    /** Links to packages that are direct suppliers of this package. */
    val supplierPackageDependencies: List<PackageDependency>
        get() = _supplierPackageDependencies.sortedBy { c -> c.supplier.path }

    val transitiveConsumers: List<Package>
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

    val transitiveSuppliers: List<Package>
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

    /** The vertex types within this package. */
    val vertexTypes: List<VertexType>
        get() = _vertexTypeContainments.map { c -> c.child }.sortedBy { vt -> vt.name }

    /** Links to packages that are direct children of this package. */
    val vertexTypeContainments: List<VertexTypeContainment>
        get() = _vertexTypeContainments.sortedBy { c -> c.child.name }


    /** Registers the given package containment in this package. */
    internal fun addChildPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.parent === this) {
            "Child package containment can only be added to its parent."
        }

        _childPackageContainments.add(packageContainment)

    }

    /** Adds a constrained date type to this, its parent package's, list of constrained data type containments. */
    internal fun addConstrainedDataTypeContainment(constrainedDataTypeContainment: ConstrainedDataTypeContainment) {

        require(constrainedDataTypeContainment.parent === this) {
            "Cannot add a constrained data type to a package not its parent."
        }

        _constrainedDataTypeContainments.add(constrainedDataTypeContainment)

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

    /** Adds a vertex type to this, its parent package's, list of vertex type containments. */
    internal fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(vertexTypeContainment.parent === this) {
            "Cannot add a vertex type to a package not its parent."
        }

        _vertexTypeContainments.add(vertexTypeContainment)

    }

    /** Whether the given [pkg] is a direct child of this one. */
    fun hasChild(pkg: Package): Boolean {

        var result = false

        _childPackageContainments.forEachWhile { packageContainment ->

            result = pkg === packageContainment.child
            !result

        }

        return result

    }

    /** Whether the given [vertexType] is a direct child of this one. */
    fun hasChild(vertexType: VertexType): Boolean {

        var result = false

        _vertexTypeContainments.forEachWhile { vertexTypeContainment ->

            result = vertexType === vertexTypeContainment.child
            !result

        }

        return result

    }

    /** Whether the given [pkg] is a direct consumer of this one. */
    fun hasConsumer(pkg: Package): Boolean {

        var result = false

        _consumerPackageDependencies.forEachWhile { packageDependency ->

            result = pkg === packageDependency.consumer
            !result

        }

        return result

    }

    override fun hasParent(pkg: Package): Boolean {

        var result = false

        _parentPackageContainments.forEachWhile { packageContainment ->

            result = pkg === packageContainment.parent
            !result

        }

        return result

    }

    /** Whether the given [pkg] is a direct supplier of this one. */
    fun hasSupplier(pkg: Package): Boolean {

        var result = false

        _supplierPackageDependencies.forEachWhile { packageDependency ->

            result = pkg === packageDependency.supplier
            !result

        }

        return result

    }

    /** Whether the given [pkg] is a direct or indirect child of this one. */
    fun hasTransitiveChild(pkg: Package): Boolean {

        val children: MutableSet<Package> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findChild(parent: Package): Boolean {

            for (packageContainment in parent._childPackageContainments.asList()) {

                val child = packageContainment.child

                if (child === pkg) {
                    return true
                }

                if (!children.contains(child)) {

                    children.add(child)

                    if (findChild(child)) {
                        return true
                    }

                }

            }

            return false

        }

        return findChild(this)

    }

    /** Whether the given [pkg] is a direct or indirect consumer of this one. */
    fun hasTransitiveConsumer(pkg: Package): Boolean {

        val consumers: MutableSet<Package> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findConsumer(supplierPkg: Package): Boolean {

            for (packageDependency in supplierPkg._consumerPackageDependencies.asList()) {

                val consumer = packageDependency.consumer

                if (consumer === pkg) {
                    return true
                }

                if (!consumers.contains(consumer)) {

                    consumers.add(consumer)

                    if (findConsumer(consumer)) {
                        return true
                    }

                }

            }

            return false

        }

        return findConsumer(this)

    }

    /** Whether the given [pkg] is a direct or indirect parent of this one. */
    fun hasTransitiveParent(pkg: Package): Boolean {

        val parents: MutableSet<Package> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findParent(child: Package): Boolean {

            for (packageContainment in child._parentPackageContainments.asList()) {

                val parent = packageContainment.parent

                if (parent === pkg) {
                    return true
                }

                if (!parents.contains(parent)) {

                    parents.add(parent)

                    if (findParent(parent)) {
                        return true
                    }

                }

            }

            return false

        }

        return findParent(this)

    }

    /** Whether the given [pkg] is a direct or indirect supplier of this one. */
    fun hasTransitiveSupplier(pkg: Package): Boolean {

        val suppliers: MutableSet<Package> = mutableSetOf()

        // Helper function recursively searches while accumulating the packages searched so far
        fun findSupplier(consumer: Package): Boolean {

            for (packageDependency in consumer._supplierPackageDependencies.asList()) {

                val supplier = packageDependency.supplier

                if (supplier === pkg) {
                    return true
                }

                if (!suppliers.contains(supplier)) {

                    suppliers.add(supplier)

                    if (findSupplier(supplier)) {
                        return true
                    }

                }

            }

            return false

        }

        return findSupplier(this)

    }

}
