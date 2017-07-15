//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IPackage

/**
 * Internal interface to a package or root package.
 */
internal interface IPackageImpl : IPackage {

    /** Adds a package to this, its parent package's, list of child packages. */
    fun addChildPackage(pkg: Package)

}