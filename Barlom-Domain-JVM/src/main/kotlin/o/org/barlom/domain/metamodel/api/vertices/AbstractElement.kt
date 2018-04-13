//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.vertices

import x.org.barlom.infrastructure.uuids.Uuid

/**
 * Top-level interface for Barlom model elements. Elements are uniquely identified.
 */
abstract class AbstractElement internal constructor() {

    /** The unique ID of this element. */
    abstract val id: Uuid

    /** Removes this abstract element from the graph it is part of. */
    abstract fun remove()

}