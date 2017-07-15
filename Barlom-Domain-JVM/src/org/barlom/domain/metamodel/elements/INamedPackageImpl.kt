//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

/**
 * Internal interface to a package that is not a root package.
 */
interface INamedPackageImpl : IPackageImpl {

    fun addAttributeType(attributeType: AttributeType)

    fun addClientPackageDependency(packageDependency: PackageDependency)

    fun addDirectedEdgeType(edgeType: DirectedEdgeType)

    fun addSupplierPackageDependency(packageDependency: PackageDependency)

    fun addUndirectedEdgeType(edgeType: UndirectedEdgeType)

    fun addVertexType(vertexType: VertexType)

}