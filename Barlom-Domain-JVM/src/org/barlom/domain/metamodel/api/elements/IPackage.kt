//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

/**
 * Interface for Barlom packages. Packages are the namespacing mechanism for containing vertex types, edge types,
 * constrained data types, and child packages.
 */
interface IPackage : IPackagedElement {

    /** The child sub-packages within this package. */
    val childPackages: List<IPackage>

    /** Links to packages that are direct clients of this package. */
    val clientPackageDependencies: List<IPackageDependency>

    /** Returns the client packages that directly depend upon this package. */
    val clientPackages: List<IPackage>

    /** The constrained data types contained by this package. */
    val constrainedDataTypes: List<IConstrainedDataType>

    /** The directed edge types defined within this package. */
    val directedEdgeTypes: List<IDirectedEdgeType>

    /** Links to packages that are direct suppliers of this package. */
    val supplierPackageDependencies: List<IPackageDependency>

    /** Returns the supplier packages that this package directly depends upon. */
    val supplierPackages: List<IPackage>

    /** Returns the client packages that directly or indirectly depend upon this package. */
    val transitiveClientPackages: List<IPackage>

    /** Returns the supplier packages that this package directly or indirectly depends upon. */
    val transitiveSupplierPackages: List<IPackage>

    /** The undirected edge types defined within this package. */
    val undirectedEdgeTypes: List<IUndirectedEdgeType>

    /** The vertex types contains by this package. */
    val vertexTypes: List<IVertexType>


    /** Whether a given package directly depends upon this one. */
    fun hasClientPackage(pkg: IPackage): Boolean

    /** Whether a given package is directly depended upon by this one. */
    fun hasSupplierPackage(pkg: IPackage): Boolean

    /** Whether a given package directly or indirectly depends upon this one. */
    fun hasTransitiveClientPackage(pkg: IPackage): Boolean

    /** Whether a given package is directly or indirectly depended upon by this one. */
    fun hasTransitiveSupplierPackage(pkg: IPackage): Boolean

    /** Whether this package is a direct or indirect child of the given package. */
    fun isChildOf(pkg: IPackage): Boolean

}
