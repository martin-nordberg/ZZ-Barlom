//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.*
import org.barlom.domain.metamodel.api.types.EDependencyDepth
import org.barlom.domain.metamodel.api.types.Uuid

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

) : IPackageImpl {

    /** The child packages within this package. */
    private val _childPackages: MutableList<Package> = mutableListOf()

    /** The root vertex type that will be the super type of vertex types contained in this package. */
    private val _rootVertexType = RootVertexType(rootVertexTypeId, this)

    /** The root directed edge type that will be the super type of directed edge types contained in this package. */
    private val _rootDirectedEdgeType = RootDirectedEdgeType(rootDirectedEdgeTypeId, this, _rootVertexType)

    /** The root undirected edge type that will be the super type of undirected edge types contained in this package. */
    private val _rootUndirectedEdgeType = RootUndirectedEdgeType(rootUndirectedEdgeTypeId, this, _rootVertexType)


    override val childPackages: List<IPackage>
        get() = _childPackages

    override val clientPackageDependencies: List<IPackageDependency>
        get() = listOf()

    override val constrainedDataTypes: List<ConstrainedDataType>
        get() = listOf()

    override val directedEdgeTypes: List<IDirectedEdgeType>
        get() = listOf(_rootDirectedEdgeType)

    override val name: String
        get() {
            throw UnsupportedOperationException("Root package has no name. It is a coding error to ask its name.")
        }

    override val path: String
        get() = ""

    override val parentPackage: RootPackage
        get() = this

    override val supplierPackageDependencies: List<IPackageDependency>
        get() = listOf()

    override val undirectedEdgeTypes: List<IUndirectedEdgeType>
        get() = listOf(_rootUndirectedEdgeType)

    override val vertexTypes: List<IVertexType>
        get() = listOf(_rootVertexType)


    override fun addChildPackage(pkg: Package) {

        require(pkg.parentPackage === this) {
            "Cannot add a child package element to a package not its parent."
        }

        _childPackages.add(pkg)

    }

    override fun getClientPackages(dependencyDepth: EDependencyDepth): List<IPackage> {
        return listOf()
    }

    override fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<IPackage> {
        return listOf()
    }

    override fun hasClientPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean {
        return false
    }

    override fun hasSupplierPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean {
        return false
    }

    override fun isChildOf(pkg: IPackage): Boolean {
        return false
    }

}
