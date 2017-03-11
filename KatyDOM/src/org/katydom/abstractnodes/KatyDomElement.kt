//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.UnusedMap
import org.katydom.infrastructure.UnusedSet
import org.w3c.dom.Element
import org.w3c.dom.Node

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract class representing a KatyDom virtual element. Corresponds to DOM Element.
 * @param key a key for this KatyDOM element that is unique among all the siblings of this element.
 */
abstract class KatyDomElement(
    selector: String?,
    key: String?,
    style: String?
) : KatyDomNode(key) {

    /**
     * Adds a given class to this element.
     * @param className the name of the class to add.
     */
    internal fun addClass(className: String) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        classList.add(className)

    }

    /**
     * Adds multiple classes to this element.
     * @param classes a sequence of class names to add.
     */
    internal fun addClasses(classes: Iterable<String>) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        classList.addAll(classes)

    }

    /**
     * Sets one attribute by name and value. Splits out specific attributes like class and id to their specific
     * handlers (with warnings).
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setAttribute(name: String, value: String?) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        if (value == null) {
            attributes.remove(key)
        }
        else {
            attributes.put(name, value)
        }
    }

    /**
     * Sets multiple attributes provided as a mpa from name to value.
     * @param attributes the attribute name/value pairs to set.
     */
    internal fun setAttributes(attributes: Map<String, String>) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        for ((name, value) in attributes) {
            setAttribute(name, value)
        }

    }

    /**
     * Sets one boolean attribute by name and value.
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal open fun setBooleanAttribute(name: String, value: Boolean?) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        if (value != null && value) {
            attributes.put(name, "")
        }
        else {
            attributes.remove(name)
        }

    }

    /**
     * Sets one data- attribute.
     * @param name the name of the attribute without its "data-" prefix.
     * @param value the value of the attribute.
     */
    internal fun setData(name: String, value: String) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        if (name.startsWith("data-")) {
            // TODO: Warning: "data-" prefix not required for dataset additions.
            dataset.put(name.substring(5), value)
        }
        else {
            dataset.put(name, value)
        }

    }

    /**
     * Sets multiple data attributes at once.
     * @param dataset a collection of name/value pairs of "data-" attributes.
     */
    internal fun setData(dataset: Map<String, String>) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        for ((name, value) in dataset) {
            setData(name, value)
        }

    }

    /**
     * Sets the style attribute for this element. TODO: addStyle( cssKey, cssValue )
     */
    internal fun setStyle(style: String?) {

        if (!isUnderConstruction) throw IllegalStateException("Cannot modify a KatyDOM element after it has been fully constructed.")

        setAttribute("style", style)

    }

////

    override final fun establishAttributes(domElement: Node) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")

        // Establish other attributes.
        for ((key, value) in attributes) {
            domElement.setAttribute(key, value)
        }

    }

    override final fun patchAttributes(domElement: Node, priorElement: KatyDomNode) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")
        if (priorElement !is KatyDomElement) throw IllegalArgumentException("KatyDOM node expected to be element.")

        // Patch other attributes.
        for ((key, newValue) in attributes) {
            if (newValue != priorElement.attributes[key]) {
                domElement.setAttribute(key, newValue)
            }
        }
        for ((key, _) in priorElement.attributes) {
            if (!attributes.contains(key)) {
                domElement.removeAttribute(key)
            }
        }

    }

    override final fun freezeAttributes() {

        if (classList.isNotEmpty()) {
            val attrClasses = attributes["class"]
            if (attrClasses != null) {
                classList.addAll(attrClasses.split(" "))
            }
            attributes.put("class", classList.joinToString(" "))
        }

        for ((name, value) in dataset) {
            setAttribute("data-" + name, value)
        }

        classList = Unused.classList
        dataset = Unused.dataset
    }

////

    /** The attributes of this element, mapped from name to value. */
    private val attributes: MutableMap<String, String> = hashMapOf()

    /** A list of classes for this element. */
    private var classList: MutableSet<String> = hashSetOf()

    /** A list of the data-* properties of this element, keyed without the "data-" prefix. */
    private var dataset: MutableMap<String, String> = hashMapOf()

    init {
        // Parse the id and classes out of the selector as relevant.
        val selectorPieces = selector?.split(".")

        if (selectorPieces != null && selectorPieces.isNotEmpty()) {
            var firstClassIdx = 0

            if (selectorPieces[0].startsWith("#")) {
                setAttribute("id", selectorPieces[0].substring(1))
                firstClassIdx = 1
            }
            else if (selectorPieces[0].isEmpty()) {
                // TODO: Warning: selector should start with "." or "#"; "." assumed.
                firstClassIdx = 1
            }

            classList.addAll(selectorPieces.subList(firstClassIdx, selectorPieces.size))
        }

        setAttribute("style", style)

    }

    private companion object Unused {
        val classList = UnusedSet<String>()
        val dataset: MutableMap<String, String> = UnusedMap<String, String>()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
