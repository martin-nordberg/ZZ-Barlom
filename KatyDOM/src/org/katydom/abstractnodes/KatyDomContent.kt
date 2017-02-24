//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface representing the contents of a KatyDOM virtual node after those contents have been built (when they
 * are passed into a newly constructed parent node.)
 *
 * "Content" consists of the following:
 *   - Attributes - element attributes beyond the primary ones built in to the builder DSL.
 *   - Style - CSS style directives for this element.
 *   - Dataset - attributes with the "data-" prefix.
 *   - Aria - attributes with the "aria-" prefix.
 *   - Event Listeners - like "onwhatever" attributes, but wired to event handling callback functions.
 *   - Child Nodes - the contained elements within the element under construction.
 */
interface KatyDomContent {

    val attributes: Iterable<KatyDomAttribute>

    val childNodes: Iterable<KatyDomNode>

    val classList: Set<String>

    val dataset: Iterable<KatyDomAttribute>

    val soleChildNode: KatyDomNode

    fun withFilteredAttributes(predicate: (KatyDomAttribute) -> Boolean): KatyDomContent

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
