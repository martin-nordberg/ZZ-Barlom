//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices2.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices2.Package

/**
 * Interface for abstract Barlom package containment.
 */
abstract class AbstractPackagedElementContainment : AbstractDocumentedElement() {

    /** The packaged element that is contained by the parent package. */
    abstract val child: AbstractPackagedElement

    /** The package that contains the child package. */
    abstract val parent: Package

    // TODO: may want other attributes such as whether the child is exported

}