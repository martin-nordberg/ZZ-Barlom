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
 */
abstract class KatyDomElement(
    selector: String?,
    key: String?
) : KatyDomNode(key) {

    val classList: Set<String>

    val dataset: Map<String, String>

    val id: Cell<String>

    val otherAttributes: Map<String, String>

////

    internal fun addClass(className: String) {
        scaffolding.classList.add(className)
    }

    internal fun addClasses(classes: Iterable<String>) {
        scaffolding.classList.addAll(classes)
    }

    internal fun setAttribute(name: String, value: String) {
        if (name == "class") {
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

    internal fun setAttributes(attributes: Map<String, String>) {
        for ((key, value) in attributes) {
            setAttribute(key, value)
        }
    }

    internal fun setData(name: String, value: String) {
        if (name.startsWith("data-")) {
            // TODO: Warning: "data-" prefix not required for dataset additions.
            scaffolding.dataset.put(name.substring(5), value)
        }
        else {
            scaffolding.dataset.put(name, value)
        }
    }

    internal fun setData(dataset: Map<String, String>) {
        for ((key, value) in dataset) {
            setData(key, value)
        }
    }

////

    override final fun establish2(domElement: Node) {

        if ( domElement !is Element ) throw IllegalArgumentException("DOM node expected to be an element.")

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

    open protected fun establish3(domElement: Element) {}

    override final fun patch2(domElement: Node, priorElement: KatyDomNode) {

        if ( domElement !is Element ) throw IllegalArgumentException("DOM node expected to be an element.")
        if ( priorElement !is KatyDomElement ) throw IllegalArgumentException( "KatyDOM node expected to be element.")

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

        patch3( domElement, priorElement)
    }

    open protected fun patch3( domElement: Element, priorElement: KatyDomElement?) {}

    override final fun removeScaffolding2() {
        _scaffolding = null
        removeScaffolding3()
    }

    open protected fun removeScaffolding3() {}

////

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
