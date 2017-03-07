//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell
import org.w3c.dom.Element
import org.w3c.dom.html.HTMLElement

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

abstract class KatyDomHtmlElement(
    selector: String?,
    key: String?,
    style: String?
) : KatyDomElement(selector,key) {

    // TODO: Needs to be String/String map of many styles
    val style: Cell<String>

////

    @Suppress("unused")
    internal fun setStyle(style: String) {
        scaffolding.style.set(style)
    }

////

    override fun establish3(domElement: Element) {

        if (domElement !is HTMLElement) throw IllegalArgumentException( "DOM element expected to be HTML element." )

        style.ifPresent { style ->
            domElement.setAttribute("style", style)
        }

        establish4(domElement)

    }

    open protected fun establish4(domElement: HTMLElement) {}

    override fun patch3(domElement: Element, priorElement: KatyDomElement?) {

        if (domElement !is HTMLElement) throw IllegalArgumentException( "DOM element expected to be HTML element." )
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

    open protected fun patch4(domElement: HTMLElement, priorElement: KatyDomHtmlElement?) {}

    override fun removeScaffolding3() {
        _scaffolding = null

        removeScaffolding4()
    }

    open protected fun removeScaffolding4() {}

////

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

