//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

/**
 * Interface to a named element - any model item with a name.
 */
abstract class AbstractNamedElement internal constructor() : AbstractDocumentedElement() {

    /** The name of this element. */
    abstract var name: String

    /** The dot-delimited path to this packaged element. */
    abstract val path: String

}