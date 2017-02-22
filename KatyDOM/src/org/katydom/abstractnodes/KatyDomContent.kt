//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Class representing the contents of a KatyDOM virtual node as those contents are built (before they are passed into
 * a newly constructed parent node.)
 *
 * "Content" consists of the following:
 *   - Attributes - element attributes beyond the primary ones built in to the builder DSL.
 *   - Style - CSS style directives for this element.
 *   - Dataset - attributes with the "data-" prefix.
 *   - Aria - attributes with the "aria-" prefix.
 *   - Event Listeners - like "onwhatever" attributes, but wired to event handling callback functions.
 *   - Child Nodes - the contained elements within the element under construction.
 */
class KatyDomContent(
    private val _attributes: MutableList<KatyDomAttribute> = mutableListOf<KatyDomAttribute>(),
    // TODO: style
    private val _dataset: MutableList<KatyDomAttribute> = mutableListOf<KatyDomAttribute>(),
    // TODO: aria
    // TODO: event listeners
    private val _childNodes: MutableList<KatyDomNode> = mutableListOf<KatyDomNode>()
) {

    val attributes: List<KatyDomAttribute>
        get() = _attributes

    val childNodes: List<KatyDomNode>
        get() = _childNodes

    val dataset: List<KatyDomAttribute>
        get() = _dataset

    val soleChildNode: KatyDomNode
        get() {
            if (childNodes.isEmpty()) {
                throw Exception("Attempted to get single child node from empty list.")
            }
            if (childNodes.size > 1) {
                throw Exception("Attempted to get single child node from multi-node list.")
            }
            return childNodes[0]
        }

    fun addAttribute(attribute: KatyDomAttribute) {
        _attributes.add(attribute)
    }

    fun addChildNode(node: KatyDomNode) {
        _childNodes.add(node)
    }

    fun addDataAttribute(attribute: KatyDomAttribute) {
        _dataset.add(attribute)
    }

    fun withFilteredAttributes(predicate: (KatyDomAttribute) -> Boolean): KatyDomContent {
        return KatyDomContent(
            _attributes.filter(predicate).toMutableList(),
            _dataset,
            _childNodes
        )
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

