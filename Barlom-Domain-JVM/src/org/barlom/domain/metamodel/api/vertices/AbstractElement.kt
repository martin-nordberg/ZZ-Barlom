//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.infrastructure.uuids.Uuid

/**
 * Top-level interface for Barlom model elements. Elements are uniquely identified.
 */
abstract class AbstractElement internal constructor() {

    /** The unique ID of this element. */
    abstract val id: Uuid

}