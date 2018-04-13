//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.AbstractElement
import o.org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import o.org.barlom.domain.metamodel.api.vertices.Package

/**
 * Interface for abstract Barlom package containment.
 */
abstract class AbstractPackagedElementContainment : AbstractElement() {

    /** The packaged element that is contained by the parent package. */
    abstract val child: AbstractPackagedElement

    /** The package that contains the child package. */
    abstract val parent: Package

    // TODO: may want other attributes such as whether the child is exported

}