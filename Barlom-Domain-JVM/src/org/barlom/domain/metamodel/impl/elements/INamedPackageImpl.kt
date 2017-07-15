//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

/**
 * Internal interface to a package that is not a root package.
 */
internal interface INamedPackageImpl : IPackageImpl {

    /** Adds an attribute type to this, its parent package's, list of attribute types. */
    fun addAttributeType(attributeType: AttributeType)

    /** Registers the given package dependency in this package. */
    fun addClientPackageDependency(packageDependency: PackageDependency)

    /** Adds a directed edge type to this, its parent package's, list of directed edge types. */
    fun addDirectedEdgeType(edgeType: DirectedEdgeType)

    /** Registers the given package dependency in this package. */
    fun addSupplierPackageDependency(packageDependency: PackageDependency)

    /** Adds an undirected edge type to this, its parent package's, list of undirected edge types. */
    fun addUndirectedEdgeType(edgeType: UndirectedEdgeType)

    /** Adds a vertex type to this, its parent package's, list of vertex types. */
    fun addVertexType(vertexType: VertexType)

}