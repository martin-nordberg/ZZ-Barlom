//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

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

    /** Miscellaneous other attributes of this element, mapped from name to value. */
    val attributes: Map<String, String>

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
    internal fun setAttribute(name: String, value: String?) {
        if ( value == null ) {
            scaffolding.otherAttributes.remove(key)
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
     * Sets one boolean attribute by name and value.
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal open fun setBooleanAttribute(name: String, value: Boolean?) {

        if (value != null && value) {
            scaffolding.otherAttributes.put(name, "")
        }
        else {
            scaffolding.otherAttributes.remove(name)
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

    /**
     * Sets the style attribute for this element. TODO: addStyle( cssKey, cssValue )
     */
    internal fun setStyle(style: String?) {
        setAttribute("style",style)
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

    override final fun removeAttributesScaffolding() {

        val sc = scaffolding

        if ( sc.classList.isNotEmpty() ) {
            val attrClasses = sc.otherAttributes.get("class")
            if ( attrClasses != null ) {
                sc.classList.addAll(attrClasses.split(" "))
            }
            sc.otherAttributes.put("class",sc.classList.joinToString(" "))
        }

        for ( (name,value) in sc.dataset ) {
            setAttribute("data-"+name,value)
        }

        _scaffolding = null
    }

////

    /**
     * Wrapper for the mutable state of this element while it is under construction. Removed when the element has been
     * fully built.
     */
    private class Scaffolding(
        selectorPieces: List<String>,
        firstClassIdx: Int
    ) {
        val classList: MutableSet<String> = hashSetOf()
        val dataset: MutableMap<String, String> = hashMapOf()
        val otherAttributes: MutableMap<String, String> = hashMapOf()

        init {
            classList.addAll(selectorPieces.subList(firstClassIdx, selectorPieces.size))
        }

    }

    private var _scaffolding: Scaffolding?

    init {
        // Parse the id and classes out of the selector as relevant.
        val selectorPieces = selector?.split(".") ?: listOf()

        var firstClassIdx = 0
        var id: String? = null

        if ( selectorPieces.isNotEmpty() ) {
            if (selectorPieces[0].startsWith("#")) {
                id = selectorPieces[0].substring(1)
                firstClassIdx = 1
            }
            else if (selectorPieces[0].isEmpty()) {
                // TODO: Warning: selector should start with "." or "#"; "." assumed.
                firstClassIdx = 1
            }
        }

        val sc = Scaffolding(selectorPieces,firstClassIdx)
        _scaffolding = sc
        attributes = sc.otherAttributes

        setAttribute("id", id)
        setAttribute("style",style)

    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomElement.")

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
