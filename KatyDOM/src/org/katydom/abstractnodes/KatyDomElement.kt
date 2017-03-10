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
 * TODO: Probably should just move all this into KatyDomHtmlElement.
 * @param key a key for this KatyDOM element that is unique among all the siblings of this element.
 */
abstract class KatyDomElement(key: String?) : KatyDomNode(key) {

    /** Miscellaneous other attributes of this element, mapped from name to value. */
    val otherAttributes: Map<String, String>

////

    /**
     * Sets one attribute by name and value. Splits out specific attributes like class and id to their specific
     * handlers (with warnings).
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal open fun setAttribute(name: String, value: String) {
        scaffolding.otherAttributes.put(name, value)
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
    internal open fun setBooleanAttribute(name: String, value: Boolean) {
        if (value) {
            scaffolding.otherAttributes.put(name, "")
        }
        else {
            scaffolding.otherAttributes.remove(name)
        }
    }

////

    override final fun establish2(domElement: Node) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")

        // Establish predefined attributes.
        establish3(domElement)

        // Establish other attributes.
        for ((key, value) in otherAttributes) {
            domElement.setAttribute(key, value)
        }

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

        // Patch predefined attributes.
        patch3(domElement, priorElement)

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
    private class Scaffolding {
        val otherAttributes: MutableMap<String, String> = hashMapOf()
    }

    private var _scaffolding: Scaffolding?

    init {
        val sc = Scaffolding()
        _scaffolding = sc
        otherAttributes = sc.otherAttributes

    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomElement.")

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
