//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.types.EDependencyDepth

/**
 * Implementation class for Barlom packages.
 */
abstract class Package(

    id: String,
    name: String

) : PackagedElement(id, name) {

    internal fun addChildPackage(pkg: NamedPackage) {

        require(pkg.parentPackage === this) {
            "Cannot add a child package element to a package not its parent."
        }

        _childPackages.add(pkg)

    }

    abstract val attributeTypes: List<AttributeType>

    val childPackages: List<NamedPackage>
        get() = _childPackages

    abstract fun getClientPackages(dependencyDepth: EDependencyDepth): List<NamedPackage>

    abstract val edgeTypes: List<EdgeType>

    abstract fun getSupplierPackages(dependencyDepth: EDependencyDepth): List<NamedPackage>

    abstract fun hasClientPackage(pkg: NamedPackage, dependencyDepth: EDependencyDepth): Boolean

    abstract fun hasSupplierPackage(pkg: NamedPackage, dependencyDepth: EDependencyDepth): Boolean

    abstract fun isChildOf(pkg: Package): Boolean

    abstract val path: String

    abstract val vertexTypes: List<VertexType>

    /** The sub-packages of this package. */
    private val _childPackages: MutableList<NamedPackage> = mutableListOf()

}
