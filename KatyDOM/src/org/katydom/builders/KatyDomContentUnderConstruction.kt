//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomAttribute
import org.katydom.abstractnodes.KatyDomContent
import org.katydom.abstractnodes.KatyDomNode

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
internal class KatyDomContentUnderConstruction(
    private val _attributes: MutableMap<String,KatyDomAttribute> = mutableMapOf(),
    // TODO: style
    private val _dataset: MutableMap<String,KatyDomAttribute> = mutableMapOf(),
    // TODO: aria
    // TODO: event listeners
    private val _childNodes: MutableList<KatyDomNode> = mutableListOf()
) : KatyDomContent {

    override val attributes: Iterable<KatyDomAttribute>
        get() = _attributes.values

    override val childNodes: Iterable<KatyDomNode>
        get() = _childNodes

    override val dataset: Iterable<KatyDomAttribute>
        get() = _dataset.values

    override val soleChildNode: KatyDomNode
        get() {
            if (_childNodes.isEmpty()) {
                throw Exception("Attempted to get single child node from empty list.")
            }
            if (_childNodes.size > 1) {
                throw Exception("Attempted to get single child node from multi-node list.")
            }
            return _childNodes[0]
        }

    /**
     * Adds an attribute to this content under construction.
     * @param attribute the attribute to add.
     */
    fun addAttribute(attribute: KatyDomAttribute) {
        _attributes.put(attribute.name,attribute)
    }

    /**
     * Adds a child node to this content under construction.
     * @param node the child node to add as the new last child.
     */
    fun addChildNode(node: KatyDomNode) {
        _childNodes.add(node)
    }

    /**
     * Adds a data attribute to this content under construction.
     * @param attribute a data attribute to be added.
     */
    fun addDataAttribute(attribute: KatyDomAttribute) {
        _dataset.put(attribute.name,attribute)
    }

    override fun withFilteredAttributes(predicate: (KatyDomAttribute) -> Boolean): KatyDomContent {
        return KatyDomContentUnderConstruction(
            _attributes.filter{ entry -> predicate(entry.value) }.toMutableMap(),
            _dataset,
            _childNodes
        )
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
