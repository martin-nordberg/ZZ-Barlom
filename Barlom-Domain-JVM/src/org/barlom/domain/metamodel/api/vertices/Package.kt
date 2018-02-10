//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.*
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
    private val _description = if (isRoot) V("Root package.") else V("")
    private val _directedEdgeTypeContainments = VLinkedList<DirectedEdgeTypeContainment>()
    private val _name = V(if (isRoot) "Metamodel" else "newpackage")
    private val _parentPackageContainments = VLinkedList<PackageContainment>()
    private val _supplierPackageDependencies = VLinkedList<PackageDependency>()
    private val _undirectedEdgeTypeContainments = VLinkedList<UndirectedEdgeTypeContainment>()
    private val _vertexTypeContainments = VLinkedList<VertexTypeContainment>()


    /** Links to packages that are direct children of this package. */
    val childPackageContainments: List<PackageContainment>
        get() = _childPackageContainments.sortedBy { c -> c.child.name }

    /** The child sub-packages within this package. */
    val childPackages: List<Package>
        get() = _childPackageContainments.map { c -> c.child }.sortedBy { pkg -> pkg.name }

    /** The constrained data types within this package. */
    val constrainedDataTypeContainments: List<ConstrainedDataTypeContainment>
        get() = _constrainedDataTypeContainments.sortedBy { cdt -> cdt.child.name }

    /** The constrained data types within this package. */
    val constrainedDataTypes: List<ConstrainedDataType>
        get() = _constrainedDataTypeContainments.map { c -> c.child }.sortedBy { cdt -> cdt.name }

    /** The consumer package links within this package. */
    val consumers: List<Package>
        get() = _consumerPackageDependencies.map { c -> c.consumer }.sortedBy { pkg -> pkg.path }

    /** Links to packages that are direct consumers of this package. */
    val consumerPackageDependencies: List<PackageDependency>
        get() = _consumerPackageDependencies.sortedBy { c -> c.consumer.path }

    /** Whether this package contains any sub-packages. */
    val containsChildPackages: Boolean
        get() = _childPackageContainments.isNotEmpty()

    /** Whether this package contains directed edge types. */
    val containsConstrainedDataTypes: Boolean
        get() = _constrainedDataTypeContainments.isNotEmpty()

    /** Whether this package contains directed edge types. */
    val containsDirectedEdgeTypes: Boolean
        get() = _directedEdgeTypeContainments.isNotEmpty()

    /** Whether this package contains child elements of any kind. */
    val containsElements: Boolean
        get() = containsChildPackages || containsVertexTypes || containsUndirectedEdgeTypes ||
            containsDirectedEdgeTypes || containsConstrainedDataTypes

    /** Whether this package contains undirected edge types. */
    val containsUndirectedEdgeTypes: Boolean
        get() = _undirectedEdgeTypeContainments.isNotEmpty()

    /** Whether this package contains vertex types. */
    val containsVertexTypes: Boolean
        get() = _vertexTypeContainments.isNotEmpty()

    override var description: String
        get() = _description.get()
        set(value) {


            check(!isRoot) {
                "Root package description cannot be changed"
            }

            _description.set(value)

        }

    /** The directed edge types within this package. */
    val directedEdgeTypeContainments: List<DirectedEdgeTypeContainment>
        get() = _directedEdgeTypeContainments.sortedBy { et -> et.child.name }

    /** The directed edge types within this package. */
    val directedEdgeTypes: List<DirectedEdgeType>
        get() = _directedEdgeTypeContainments.map { c -> c.child }.sortedBy { et -> et.name }

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

            if (isRoot) {
                return ""
            }

            if (_parentPackageContainments.isEmpty()) {
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

    /** The links to undirected edge types within this package. */
    val undirectedEdgeTypeContainments: List<UndirectedEdgeTypeContainment>
        get() = _undirectedEdgeTypeContainments.sortedBy { et -> et.child.name }

    /** The undirected edge types within this package. */
    val undirectedEdgeTypes: List<UndirectedEdgeType>
        get() = _undirectedEdgeTypeContainments.map { c -> c.child }.sortedBy { et -> et.name }

    /** Links to vertex types that are direct children of this package. */
    val vertexTypeContainments: List<VertexTypeContainment>
        get() = _vertexTypeContainments.sortedBy { c -> c.child.name }

    /** The vertex types within this package. */
    val vertexTypes: List<VertexType>
        get() = _vertexTypeContainments.map { c -> c.child }.sortedBy { vt -> vt.name }


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

    /** Adds a directed edge type to this, its parent package's, list of directed edge type containments. */
    internal fun addDirectedEdgeTypeContainment(directedEdgeTypeContainment: DirectedEdgeTypeContainment) {

        require(directedEdgeTypeContainment.parent === this) {
            "Cannot add a directed edge type to a package not its parent."
        }

        _directedEdgeTypeContainments.add(directedEdgeTypeContainment)

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

    /** Adds an undirected edge type to this, its parent package's, list of undirected edge type containments. */
    internal fun addUndirectedEdgeTypeContainment(undirectedEdgeTypeContainment: UndirectedEdgeTypeContainment) {

        require(undirectedEdgeTypeContainment.parent === this) {
            "Cannot add an undirected edge type to a package not its parent."
        }

        _undirectedEdgeTypeContainments.add(undirectedEdgeTypeContainment)

    }

    /** Adds a vertex type to this, its parent package's, list of vertex type containments. */
    internal fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(vertexTypeContainment.parent === this) {
            "Cannot add a vertex type to a package not its parent."
        }

        _vertexTypeContainments.add(vertexTypeContainment)

    }

    /** Finds the child package of this package that has a given name. */
    fun childPackageByName(name: String): Package? {

        return _childPackageContainments.find { c ->
            c.child.name == name
        }?.child

    }

    /** Finds the constrained data type in this package that has a given name. */
    fun constrainedDataTypeByName(name: String): ConstrainedDataType? {

        return _constrainedDataTypeContainments.find { c ->
            c.child.name == name
        }?.child

    }

    /** Finds the directed edge type in this package that has a given name. */
    fun directedEdgeTypeByName(name: String): DirectedEdgeType? {

        return _directedEdgeTypeContainments.find { c ->
            c.child.name == name
        }?.child

    }

    /** Recursively searches within this package for the constrained data type with given relative path [dataTypePath]. */
    fun findDataTypeByPath(dataTypePath: String): ConstrainedDataType? {

        val pathSegments = dataTypePath.split(".", limit = 2)

        if (pathSegments.size == 2) {
            return this.childPackageByName(pathSegments[0])?.findDataTypeByPath(pathSegments[1])
        }
        else {
            return this.constrainedDataTypeByName(pathSegments[0])
        }

    }

    /** Recursively searches within this package for the directed edge type with given relative path [edgeTypePath]. */
    fun findDirectedEdgeTypeByPath(edgeTypePath: String): DirectedEdgeType? {

        val pathSegments = edgeTypePath.split(".", limit = 2)

        if (pathSegments.size == 2) {
            return this.childPackageByName(pathSegments[0])?.findDirectedEdgeTypeByPath(pathSegments[1])
        }
        else {
            return this.directedEdgeTypeByName(pathSegments[0])
        }

    }

    /** Recursively searches within this package for the package with given relative path [packagePath]. */
    fun findPackageByPath(packagePath: String): Package? {

        val pathSegments = packagePath.split(".", limit = 2)

        if (pathSegments.size == 2) {
            return this.childPackageByName(pathSegments[0])?.findPackageByPath(pathSegments[1])
        }
        else {
            return this.packageByName(pathSegments[0])
        }

    }

    /** Finds the packages in the model that are eligible to be a supplier of this package. */
    fun findPotentialSuppliers(): List<Package> {

        val result = mutableListOf<Package>()

        if (isRoot) {
            return result
        }

        val parentPkg = this.parents[0]

        fun findPotentialSuppliersInPkg(pkg: Package) {

            val ineligible = pkg.isRoot ||
                pkg == this ||
                this.hasSupplier(pkg) ||
                pkg.hasSupplier(this) && pkg.parents[0] != parentPkg

            if (!ineligible) {
                result.add(pkg)
            }

            for (childPkg in pkg.childPackages) {
                findPotentialSuppliersInPkg(childPkg)
            }

        }

        findPotentialSuppliersInPkg(this.findRootPackage())

        return result

    }

    /** Finds the root package by traversing up the child-parent path. */
    fun findRootPackage(): Package {

        var result = this

        while (!result.isRoot) {
            result = result.parents[0]
        }

        return result

    }

    /** Recursively searches within this package for the undirected edge type with given relative path [edgeTypePath]. */
    fun findUndirectedEdgeTypeByPath(edgeTypePath: String): UndirectedEdgeType? {

        val pathSegments = edgeTypePath.split(".", limit = 2)

        if (pathSegments.size == 2) {
            return this.childPackageByName(pathSegments[0])?.findUndirectedEdgeTypeByPath(pathSegments[1])
        }
        else {
            return this.undirectedEdgeTypeByName(pathSegments[0])
        }

    }

    /** Recursively searches within this package for the vertex type with given relative path [vertexTypePath]. */
    fun findVertexTypeByPath(vertexTypePath: String): VertexType? {

        val pathSegments = vertexTypePath.split(".", limit = 2)

        if (pathSegments.size == 2) {
            return this.childPackageByName(pathSegments[0])?.findVertexTypeByPath(pathSegments[1])
        }
        else {
            return this.vertexTypeByName(pathSegments[0])
        }

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

    override fun hasParent(pkg: Package) = _parentPackageContainments.contains({ c -> c.parent == pkg })

    override fun hasParentPackage(pkg: Package) = hasParent(pkg)

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

    /** Finds the child package in this package that has a given [name]. */
    fun packageByName(name: String): Package? {

        return _childPackageContainments.find { c ->
            c.child.name == name
        }?.child

    }

    override fun remove() {
        _childPackageContainments.forEach { c -> c.remove() }
        _constrainedDataTypeContainments.forEach { c -> c.remove() }
        _consumerPackageDependencies.forEach { c -> c.remove() }
        _directedEdgeTypeContainments.forEach { c -> c.remove() }
        _parentPackageContainments.forEach { c -> c.remove() }
        _supplierPackageDependencies.forEach { d -> d.remove() }
        _undirectedEdgeTypeContainments.forEach { c -> c.remove() }
        _vertexTypeContainments.forEach { c -> c.remove() }
    }

    /** Unregisters the given package containment from this package. */
    internal fun removeChildPackageContainment(packageContainment: PackageContainment) {

        require(_childPackageContainments.remove(packageContainment)) {
            "Child package containment can not be removed from a package not its parent."
        }

    }

    /** Removes a constrained data type from this, its parent package's, list of constrained data type containments. */
    internal fun removeConstrainedDataTypeContainment(constrainedDataTypeContainment: ConstrainedDataTypeContainment) {

        require(_constrainedDataTypeContainments.remove(constrainedDataTypeContainment)) {
            "Cannot remove a constrained data type from a package not its parent."
        }

    }

    /** Unregisters the given package dependency in this package. */
    internal fun removeConsumerPackageDependency(packageDependency: PackageDependency) {

        require(_consumerPackageDependencies.remove(packageDependency)) {
            "Consumer package dependency can not be removed from a package not its supplier."
        }

    }

    /** Removes a directed edge type from this, its parent package's, list of directed edge type containments. */
    internal fun removeDirectedEdgeTypeContainment(directedEdgeTypeContainment: DirectedEdgeTypeContainment) {

        require(_directedEdgeTypeContainments.remove(directedEdgeTypeContainment)) {
            "Cannot remove a directed edge type from a package not its parent."
        }

    }

    /** Unregisters the given package containment in this package. */
    internal fun removeParentPackageContainment(packageContainment: PackageContainment) {

        require(_parentPackageContainments.remove(packageContainment)) {
            "Parent package containment can not be removed from a ackage not its child."
        }

    }

    /** Registers the given package dependency in this package. */
    internal fun removeSupplierPackageDependency(packageDependency: PackageDependency) {

        require(_supplierPackageDependencies.remove(packageDependency)) {
            "Supplier package dependency can not be removed from a package not its consumer."
        }

    }

    /** Removes an undirected edge type from this, its parent package's, list of undirected edge type containments. */
    internal fun removeUndirectedEdgeTypeContainment(undirectedEdgeTypeContainment: UndirectedEdgeTypeContainment) {

        require(_undirectedEdgeTypeContainments.remove(undirectedEdgeTypeContainment)) {
            "Cannot add an undirected edge type to a package not its parent."
        }

    }

    /** Removes a vertex type from this, its parent package's, list of vertex type containments. */
    internal fun removeVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(_vertexTypeContainments.remove(vertexTypeContainment)) {
            "Cannot remove a vertex type from a package not its parent."
        }

    }

    /** Finds the undirected edge type in this package that has a given [name]. */
    fun undirectedEdgeTypeByName(name: String): UndirectedEdgeType? {

        return _undirectedEdgeTypeContainments.find { c ->
            c.child.name == name
        }?.child

    }

    /** Finds the vertex type in this package that has a given [name]. */
    fun vertexTypeByName(name: String): VertexType? {

        return _vertexTypeContainments.find { c ->
            c.child.name == name
        }?.child

    }

}
