//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.infrastructure.uuids.Uuid

/**
 * Top-level interface for Barlom model elements. Various forms of documentation can be linked to any such
 * model element.
 */
abstract class AbstractDocumentedElement internal constructor() {

    /** The unique ID of this element. */
    abstract val id: Uuid

}