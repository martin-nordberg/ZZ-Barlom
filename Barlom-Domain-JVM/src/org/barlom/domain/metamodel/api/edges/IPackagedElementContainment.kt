//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges

import org.barlom.domain.metamodel.api.vertices.IDocumentedElement
import org.barlom.domain.metamodel.api.vertices.IPackage
import org.barlom.domain.metamodel.api.vertices.IPackagedElement

/**
 * Interface for abstract Barlom package containment.
 */
interface IPackagedElementContainment : IDocumentedElement {

    /** The packaged element that is contained by the parent package. */
    val child: IPackagedElement

    /** The package that contains the child package. */
    val parent: IPackage

    // TODO: may want other attributes such as whether the child is exported

}
