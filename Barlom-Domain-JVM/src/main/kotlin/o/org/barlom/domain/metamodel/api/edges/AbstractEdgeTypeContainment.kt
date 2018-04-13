//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.edges

import o.org.barlom.domain.metamodel.api.vertices.AbstractEdgeType

/**
 * Interface for Barlom edge type containment.
 */
abstract class AbstractEdgeTypeContainment : AbstractPackagedElementContainment() {

    /** The edge type that is contained by the parent package. */
    abstract override val child: AbstractEdgeType


}