//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.edges.IPackageContainment
import org.barlom.domain.metamodel.api.edges.IPackageDependency
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.domain.metamodel.impl.edges.PackageContainment
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for Barlom root packages.
 */
internal data class RootPackage(

    override val id: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root vertex type within this root package. */
    private val rootVertexTypeId: Uuid = Uuid.fromString("66522301-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root directed edge type within this root package. */
    private val rootDirectedEdgeTypeId: Uuid = Uuid.fromString("66522302-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root undirected edge type within this root package. */
    private val rootUndirectedEdgeTypeId: Uuid = Uuid.fromString("66522303-6c7d-11e7-81b7-080027b6d283")

) : IPackageImpl, IRootPackage {

    /** The child packages within this package. */
    private val _childPackageContainments = VLinkedList<PackageContainment>()


    override val childPackages: List<Package>
        get() = _childPackageContainments.map{ c->c.child }.sortedBy { pkg -> pkg.name }

    override val childPackageContainments: List<IPackageContainment>
        get() = _childPackageContainments.sortedBy { pkgdep -> pkgdep.child.path }

    override val clientPackageDependencies: List<IPackageDependency>
        get() = listOf()

    override val clientPackages: List<IPackage>
        get() = listOf()

    override val constrainedDataTypes: List<ConstrainedDataType>
        get() = listOf()

    override val directedEdgeTypes: List<IDirectedEdgeType>
        get() = listOf(rootDirectedEdgeType)

    override var name: String
        get() =
            throw UnsupportedOperationException("Root package has no name. It is a coding error to ask its name.")
        set(value) =
            throw UnsupportedOperationException("Root package has no name. It is a coding error to ask its name.")

    override val path: String
        get() = ""

    override val parentPackage: RootPackage
        get() = this

    override val parentPackageContainment: IPackageContainment?
        get() =
        throw UnsupportedOperationException("Root package has no parent package. It is a coding error to ask its parent package.")

    override val rootVertexType = RootVertexType(rootVertexTypeId, this)

    override val rootDirectedEdgeType = RootDirectedEdgeType(
        rootDirectedEdgeTypeId, this, rootVertexType)

    override val rootUndirectedEdgeType = RootUndirectedEdgeType(
        rootUndirectedEdgeTypeId, this, rootVertexType)

    override val supplierPackageDependencies: List<IPackageDependency>
        get() = listOf()

    override val supplierPackages: List<IPackage>
        get() = listOf()

    override val transitiveClientPackages: List<IPackage>
        get() = listOf()

    override val transitiveSupplierPackages: List<IPackage>
        get() = listOf()

    override val undirectedEdgeTypes: List<IUndirectedEdgeType>
        get() = listOf(rootUndirectedEdgeType)

    override val vertexTypes: List<IVertexType>
        get() = listOf(rootVertexType)


    override fun addChildPackageContainment(packageContainment: PackageContainment) {

        require(packageContainment.parent === this) {
            "Child package containment can only be added to its parent."
        }

        _childPackageContainments.add(packageContainment)

    }

    override fun hasClientPackage(pkg: IPackage): Boolean {
        return false
    }

    override fun hasSupplierPackage(pkg: IPackage): Boolean {
        return false
    }

    override fun hasTransitiveClientPackage(pkg: IPackage): Boolean {
        return false
    }

    override fun hasTransitiveSupplierPackage(pkg: IPackage): Boolean {
        return false
    }

    override fun isChildOf(pkg: IPackage): Boolean {
        return false
    }

}
