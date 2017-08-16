//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

/**
 * Interface to a named element - any model item with a name.
 */
interface INamedElement : IDocumentedElement {

    /** The name of this element. */
    var name: String

}
