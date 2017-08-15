//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.vertices.INamedElement
import org.barlom.domain.metamodel.api.vertices.IPackage

/**
 * Interface for an abstract packaged element.
 */
interface IPackagedElement : INamedElement {

    /** The package containing this model element. */
    val parentPackage: IPackage?

    /** The dot-delimited path to this packaged element. */
    val path: String

}

