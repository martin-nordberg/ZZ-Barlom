//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.infrastructure.uuids.Uuid


/**
 * Top-level interface for Barlom model elements. Various forms of documentation can be linked to any such
 * model element.
 */
interface IDocumentedElement {

    /** The unique ID of this element (usually a UUID). */
    val id: Uuid

}