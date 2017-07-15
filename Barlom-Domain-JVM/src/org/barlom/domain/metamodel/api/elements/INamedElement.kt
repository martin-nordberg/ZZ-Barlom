//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements

/**
 * Interface to a named element.
 */
interface INamedElement : IDocumentedElement {

    /** The name of this element. */
    val name: String

}
