//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EDependencyDepth

/**
 * Interface for Barlom packages. Packages are the namespacing mechanism for containing vertex types, edge types,
 * constrained data types, and child packages.
 */
interface IPackage : IPackagedElement {

    /** The child sub-packages within this package. */
    val childPackages: List<IPackage>

    /** Links to packages that are clients of this package. */
    val clientPackageDependencies: List<IPackageDependency>

    /** The constrained data types contained by this package. */
    val constrainedDataTypes: List<IConstrainedDataType>

    /** The directed edge types defined within this package. */
    val directedEdgeTypes: List<IDirectedEdgeType>

    /** The dot-delimited path to this package. */
    val path: String

    /** Links to packages that are suppliers of this package. */
    val supplierPackageDependencies: List<IPackageDependency>

    /** The undirected edge types defined within this package. */
    val undirectedEdgeTypes: List<IUndirectedEdgeType>

    /** The vertex types contains by this package. */
    val vertexTypes: List<IVertexType>


    /** Returns the client packages that depend upon this package, either directly or fully transitively. */
    fun getClientPackages(dependencyDepth: EDependencyDepth): List<IPackage>

    /** Returns the supplier packages that this package depends upon, either directly or fully transitively. */
    fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<IPackage>

    /** Whether a given package depends upon this one. */
    fun hasClientPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean

    /** Whether a given package is depended upon by this one. */
    fun hasSupplierPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean

    /** Whether this package is a direct or indirect child of the given package. */
    fun isChildOf(pkg: IPackage): Boolean

}
