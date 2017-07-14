//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EDependencyDepth

/**
 * Implementation class for Barlom root packages.
 */
class RootPackage(

    id : String

) : Package(id, "$") {

    override val attributeTypes: List<AttributeType>
        get() = listOf()

    override fun getClientPackages(dependencyDepth: EDependencyDepth): List<NamedPackage> {
        return listOf()
    }

    override val edgeTypes: List<EdgeType>
        get() = listOf() // TODO: root edge types

    override fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<NamedPackage> {
        return listOf()
    }

    override fun hasClientPackage(pkg: NamedPackage, dependencyDepth: EDependencyDepth): Boolean {
        return false
    }

    override fun hasSupplierPackage(pkg: NamedPackage, dependencyDepth: EDependencyDepth): Boolean {
        return false
    }

    override fun isChildOf(pkg: Package): Boolean {
        return false
    }

    override val path: String
        get() = ""

    override val parentPackage: Package
        get() = this

    override val vertexTypes: List<VertexType>
        get() = listOf()  // TODO: root vertex type

    // TODO: root vertext type, directed edge type, undirected edge type
}
