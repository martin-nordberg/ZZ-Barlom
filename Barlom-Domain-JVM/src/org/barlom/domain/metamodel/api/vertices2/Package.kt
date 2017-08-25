//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.edges2.PackageContainment
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

/**
 * Interface for Barlom packages. Packages are the namespacing mechanism for containing vertex types, edge types,
 * constrained data types, and child packages.
 */
class Package internal constructor(

    override val id: Uuid,
    name: String,
    initialize: Package.() -> Unit

) : AbstractPackagedElement() {

    private val _childPackageContainments = VLinkedList<PackageContainment>()
    private val _name = V(name)
    private val _parentPackageContainments = VLinkedList<PackageContainment>()


    init {
        initialize()
    }


    /** The child sub-packages within this package. */
    val childPackages: List<Package>
        get() = _childPackageContainments.map { c -> c.child }.sortedBy { pkg -> pkg.name }

    /** Links to packages that are direct children of this package. */
    val childPackageContainments: List<PackageContainment>
        get() = _childPackageContainments.sortedBy { c -> c.child.name }

    override var name: String
        get() = _name.get()
        set(value) = _name.set(value)

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


    /** Registers the given package containment in this package. */
    internal fun addChildPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.parent === this) {
            "Child package containment can only be added to its parent."
        }

        _childPackageContainments.add(packageContainment)

    }

    /** Registers the given package containment in this package. */
    internal fun addParentPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.child === this) {
            "Parent package containment can only be added to its child."
        }

        _parentPackageContainments.add(packageContainment)

    }

    /** Establishes the parent container of this package. */
    fun containedBy(pkg: Package) {
        PackageContainment(makeUuid(), pkg, this)
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
