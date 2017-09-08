//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.AbstractEdgeType

/**
 * Interface for Barlom edge type containment.
 */
abstract class AbstractEdgeTypeContainment : AbstractPackagedElementContainment() {

    /** The edge type that is contained by the parent package. */
    abstract override val child: AbstractEdgeType


}