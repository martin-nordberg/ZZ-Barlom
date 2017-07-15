//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

import org.barlom.domain.metamodel.api.types.EDependencyDepth

/**
 * Interface for Barlom packages.
 */
interface IPackage : IPackagedElement {

    val attributeTypes: List<IAttributeType>

    val childPackages: List<IPackage>

    val directedEdgeTypes: List<IDirectedEdgeType>

    val path: String

    val undirectedEdgeTypes: List<IUndirectedEdgeType>

    val vertexTypes: List<IVertexType>

    fun getClientPackages(dependencyDepth: EDependencyDepth): List<IPackage>

    fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<IPackage>

    fun hasClientPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean

    fun hasSupplierPackage(pkg: IPackage, dependencyDepth: EDependencyDepth): Boolean

    fun isChildOf(pkg: IPackage): Boolean

}
