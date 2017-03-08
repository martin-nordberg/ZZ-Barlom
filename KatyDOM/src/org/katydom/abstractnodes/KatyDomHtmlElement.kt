//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell
import org.w3c.dom.Element

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract KatyDOM class corresponding to a DOM HTMLElement node.
 * @param selector the selector for the node consisting of an optional id preceded by '#' plus zero or more class
 *                 names, each preceded by '.'. For example, "#topbanner.squished.full-width" means id="topbanner"
 *                 and class="squished full-width".
 * @param key a key for this KatyDOM element that is unique among all the siblings of this element.
 * @param style a string containing CSS for this element.
 */
abstract class KatyDomHtmlElement(
    selector: String?,
    key: String?,
    style: String?
) : KatyDomElement(selector,key) {

    // TODO: Needs to be String/String map of many styles
    val style: Cell<String>

////

    /**
     * Sets the style attribute for this element. TODO: addStyle( cssKey, cssValue )
     */
    @Suppress("unused")
    internal fun setStyle(style: String) {
        scaffolding.style.set(style)
    }

////

    override fun establish3(domElement: Element) {

        style.ifPresent { style ->
            domElement.setAttribute("style", style)
        }

        establish4(domElement)

    }

    /**
     * Further establishes the given DOM element with the attributes of a derived KatyDOM element. Override when needed.
     * The base class method does nothing.
     * @param domElement the real DOM element being established.
     */
    open protected fun establish4(domElement: Element) {}

    override fun patch3(domElement: Element, priorElement: KatyDomElement?) {

        if (priorElement !is KatyDomHtmlElement) throw IllegalArgumentException( "KatyDOM element expected to be KatyDOM HTML element." )

        // Patch the style attribute.
        style.ifPresent { style ->
            if (!priorElement.style.contains(style)) {
                domElement.setAttribute("style", style)
            }
        }
        style.ifNotPresent {
            priorElement.style.ifPresent {
                domElement.removeAttribute("style")
            }
        }

        patch4(domElement,priorElement)

    }

    /**
     * Further patches the given DOM element with changes from a prior edition of this element.
     * @param domElement the element being patched.
     * @param priorElement the prior edition of this KatyDOM element from which to compute the patch.
     */
    open protected fun patch4(domElement: Element, priorElement: KatyDomHtmlElement?) {}

    override fun removeScaffolding3() {
        _scaffolding = null

        removeScaffolding4()
    }

    /**
     * Further removes any scaffolding in a derived class. Override when needed. The base class method does nothing.
     */
    open protected fun removeScaffolding4() {}

////

    /**
     * Wrapper for the mutable state of this element while it is under construction. Removed when the element has been
     * fully built.
     */
    private class Scaffolding(
        style: String?
    ) {
        var style = MutableCell(style)
    }

    private var _scaffolding: Scaffolding?

    init {
        val sc = Scaffolding(style)
        _scaffolding = sc
        this.style = sc.style

    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomHtmlElement.")

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

