//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.*
import org.barlom.domain.metamodel.api.types.EDependencyDepth

/**
 * Implementation class for Barlom root packages.
 */
class RootPackage(

    override val id : String,

    rootVertexTypeId: String,
    rootDirectedEdgeTypeId : String,
    rootUndirectedEdgeTypeId : String

) : IPackageImpl {

    override val name: String
        get() = "$"

    override val childPackages: List<IPackage>
        get() = _childPackages

    override val directedEdgeTypes: List<IDirectedEdgeType>
        get() = listOf(_rootDirectedEdgeType)

    override val undirectedEdgeTypes: List<IUndirectedEdgeType>
        get() = listOf(_rootUndirectedEdgeType)

    override fun addChildPackage(pkg: Package) {

        require(pkg.parentPackage === this) {
            "Cannot add a child package element to a package not its parent."
        }

        _childPackages.add(pkg)

    }

    /** The child packages within this package. */
    private val _childPackages: MutableList<Package> = mutableListOf()

    private val _rootVertexType = RootVertexType(rootVertexTypeId,this)

    private val _rootDirectedEdgeType = RootDirectedEdgeType(rootDirectedEdgeTypeId,this,_rootVertexType)

    private val _rootUndirectedEdgeType = RootUndirectedEdgeType(rootUndirectedEdgeTypeId,this,_rootVertexType)

    override val attributeTypes: List<AttributeType>
        get() = listOf()

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

    override val path: String
        get() = ""

    override val parentPackage: RootPackage
        get() = this

    override val vertexTypes: List<IVertexType>
        get() = listOf(_rootVertexType)

}
