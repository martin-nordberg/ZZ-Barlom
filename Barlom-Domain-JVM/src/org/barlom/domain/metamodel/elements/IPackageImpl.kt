//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.elements.IPackage

/**
 * Internal interface to a package or root package.
 */
interface IPackageImpl : IPackage {

    fun addChildPackage(pkg: Package)

}