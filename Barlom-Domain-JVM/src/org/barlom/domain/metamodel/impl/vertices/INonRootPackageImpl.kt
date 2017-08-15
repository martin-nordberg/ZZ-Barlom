//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.impl.edges.PackageContainment
import org.barlom.domain.metamodel.impl.edges.PackageDependency
import org.barlom.domain.metamodel.impl.edges.UndirectedEdgeTypeContainment
import org.barlom.domain.metamodel.impl.edges.VertexTypeContainment

/**
 * Internal interface to a package that is not a root package.
 */
internal interface INonRootPackageImpl : IPackageImpl {

    /** Registers the given package dependency in this package. */
    fun addClientPackageDependency(packageDependency: PackageDependency)

    /** Adds a constrained data type to this, its parent package's, list of constrained data types. */
    fun addConstrainedDataType(constrainedDataType: ConstrainedDataType)

    /** Adds a directed edge type to this, its parent package's, list of directed edge types. */
    fun addDirectedEdgeType(edgeType: DirectedEdgeType)

    /** Registers the given package containment in this package. */
    fun addParentPackageContainment(packageContainment: PackageContainment)

    /** Registers the given package dependency in this package. */
    fun addSupplierPackageDependency(packageDependency: PackageDependency)

    /** Adds an undirected edge type to this, its parent package's, list of undirected edge types. */
    fun addUndirectedEdgeTypeContainment(edgeTypeContainment: UndirectedEdgeTypeContainment)

    /** Adds a vertex type to this, its parent package's, list of vertex type containments. */
    fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment)

    /** Establishes the parent container of this package. */
    fun containedBy(pkg: IPackageImpl): Unit

}