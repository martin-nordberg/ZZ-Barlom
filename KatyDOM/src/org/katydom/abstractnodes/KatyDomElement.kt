//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell
import org.w3c.dom.Element
import org.w3c.dom.Node

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract class representing a KatyDom virtual element. Corresponds to DOM Element.
 * @param selector the selector for the node consisting of an optional id preceded by '#' plus zero or more class
 *                 names, each preceded by '.'. For example, "#topbanner.squished.full-width" means id="topbanner"
 *                 and class="squished full-width".
 * @param key a key for this KatyDOM element that is unique among all the siblings of this element.
 */
abstract class KatyDomElement(selector: String?, key: String?) : KatyDomNode(key) {

    /** The classes of this element. */
    val classList: Set<String>

    /** The data- attributes of this element mapped by name without the "data-" prefix. */
    val dataset: Map<String, String>

    /** The ID of this element. */
    val id: Cell<String>

    /** Miscellaneous other attributes of this element, mapped from name to value. */
    val otherAttributes: Map<String, String>

////

    /**
     * Adds a given class to this element.
     * @param className the name of the class to add.
     */
    internal fun addClass(className: String) {
        scaffolding.classList.add(className)
    }

    /**
     * Adds multiple classes to this element.
     * @param classes a sequence of class names to add.
     */
    internal fun addClasses(classes: Iterable<String>) {
        scaffolding.classList.addAll(classes)
    }

    /**
     * Sets one attribute by name and value. Splits out specific attributes like class and id to their specific
     * handlers (with warnings).
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setAttribute(name: String, value: String) {

        if (name == "class") {
            // TODO: Warning: use addClass instead of setAttribute("class").
            addClass(value)
        }
        else if (name == "id") {
            // TODO: Warning: use selector instead of setAttribute("id",...).
            scaffolding.id.set(value)
        }
        else if (name.startsWith("data-")) {
            // TODO: Warning: use setData instead.
            setData(name.substring(5), value)
        }
        else {
            scaffolding.otherAttributes.put(name, value)
        }

    }

    /**
     * Sets multiple attributes provided as a mpa from name to value.
     * @param attributes the attribute name/value pairs to set.
     */
    internal fun setAttributes(attributes: Map<String, String>) {

        for ((name, value) in attributes) {
            setAttribute(name, value)
        }

    }

    /**
     * Sets one data- attribute.
     * @param name the name of the attribute without its "data-" prefix.
     * @param value the value of the attribute.
     */
    internal fun setData(name: String, value: String) {

        if (name.startsWith("data-")) {
            // TODO: Warning: "data-" prefix not required for dataset additions.
            scaffolding.dataset.put(name.substring(5), value)
        }
        else {
            scaffolding.dataset.put(name, value)
        }

    }

    /**
     * Sets multiple data attributes at once.
     * @param dataset a collection of name/value pairs of "data-" attributes.
     */
    internal fun setData(dataset: Map<String, String>) {

        for ((name, value) in dataset) {
            setData(name, value)
        }

    }

////

    override final fun establish2(domElement: Node) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")

        id.ifPresent { id ->
            domElement.setAttribute("id", id)
        }

        if (classList.isNotEmpty()) {
            domElement.setAttribute("class", classList.joinToString(" "))
        }

        for ((key, value) in otherAttributes) {
            domElement.setAttribute(key, value)
        }

        for ((key, value) in dataset) {
            domElement.setAttribute("data-" + key, value)
        }

        establish3(domElement)

    }

    /**
     * Further establishes DOM attributes in a class derived from this one. Override as needed. The base class method
     * does nothing.
     * @param domElement the newly created element to have its attributes set.
     */
    open protected fun establish3(domElement: Element) {
    }

    override final fun patch2(domElement: Node, priorElement: KatyDomNode) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")
        if (priorElement !is KatyDomElement) throw IllegalArgumentException("KatyDOM node expected to be element.")

        // Patch the id attribute as needed.
        id.ifPresent { id ->
            if (!priorElement.id.contains(id)) {
                domElement.setAttribute("id", id)
            }
        }
        id.ifNotPresent {
            priorElement.id.ifPresent {
                domElement.removeAttribute("id")
            }
        }

        // Patch the class attribute as needed.
        if (classList.isEmpty() && priorElement.classList.isNotEmpty()) {
            domElement.removeAttribute("class")
        }
        else if (classList.isNotEmpty() &&
            classList != priorElement.classList) {
            domElement.setAttribute("class", classList.joinToString(" "))
        }

        // Patch other attributes.
        for ((key, newValue) in otherAttributes) {
            if (newValue != priorElement.otherAttributes[key]) {
                domElement.setAttribute(key, newValue)
            }
        }
        for ((key, _) in priorElement.otherAttributes) {
            if (!otherAttributes.contains(key)) {
                domElement.removeAttribute(key)
            }
        }

        // Patch data attributes.
        for ((key, newValue) in dataset) {
            if (newValue != priorElement.dataset[key]) {
                domElement.setAttribute("data-" + key, newValue)
            }
        }
        for ((key, _) in priorElement.dataset) {
            if (!dataset.contains(key)) {
                domElement.removeAttribute("data-" + key)
            }
        }

        patch3(domElement, priorElement)

    }

    /**
     * Completes even more patching of the DOM element corresponding to this element. Override in a derived class
     * as needed. The base class method does nothing.
     * @param domElement the real DOM element being patched
     * @param priorElement the prior edition of this KatyDOM element from which to compute the patch.
     */
    open protected fun patch3(domElement: Element, priorElement: KatyDomElement?) {
    }

    override final fun removeScaffolding2() {
        _scaffolding = null
        removeScaffolding3()
    }

    /**
     * Removes any further scaffolding in a derived class. Override as needed. The base class method does nothing.
     */
    open protected fun removeScaffolding3() {
    }

////

    /**
     * Wrapper for the mutable state of this element while it is under construction. Removed when the element has been
     * fully built.
     */
    private class Scaffolding(
        selector: String?
    ) {
        val classList: MutableSet<String> = hashSetOf()
        val dataset: MutableMap<String, String> = hashMapOf()
        var id = MutableCell<String>(null)
        val otherAttributes: MutableMap<String, String> = hashMapOf()

        init {

            // Parse the id and classes out of the selector as relevant.
            val selectorPieces = selector?.split(".")

            if (selectorPieces != null) {

                var firstClassIdx = 0
                if (selectorPieces[0].startsWith("#")) {
                    id.set(selectorPieces[0].substring(1))
                    firstClassIdx = 1
                }
                else if (selectorPieces[0].isEmpty()) {
                    // TODO: Warning: selector should start with "." or "#"; "." assumed.
                    firstClassIdx = 1
                }

                classList.addAll(selectorPieces.subList(firstClassIdx, selectorPieces.size))

            }

        }

    }

    private var _scaffolding: Scaffolding?

    init {
        val sc = Scaffolding(selector)
        _scaffolding = sc
        classList = sc.classList
        dataset = sc.dataset
        id = sc.id
        otherAttributes = sc.otherAttributes

    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomElement.")

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
