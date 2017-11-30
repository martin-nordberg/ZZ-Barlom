//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.infrastructure.uuids.Uuid

/**
 * Abstract interface for Barlom model elements. Various forms of documentation can be linked to any such
 * model element.
 */
abstract class AbstractDocumentedElement internal constructor() : AbstractElement() {

    /** A short description of the element. */
    abstract var description: String;

    val firstLineOfDescription: String
        get() {
            val result = description.split('\n')
            if ( result.size > 1 ) {
                return result[0] + " ..."
            }
            return result[0]
        }

}