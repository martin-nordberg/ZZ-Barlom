//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract class representing a KatyDom virtual element. Corresponds to DOM Element.
 */
abstract class KatyDomElement(
    selector: String?
) : KatyDomNode() {

    val classList: Set<String>

    val dataset: Map<String, String>

    val id: Cell<String>

    val otherAttributes: Map<String, String>

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
            scaffolding.id.set(value);
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
        for (attribute in attributes) {
            setAttribute(attribute.key, attribute.value)
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
        for (data in dataset) {
            setData(data.key, data.value)
        }
    }

    override final fun removeScaffolding2() {
        _scaffolding = null
        removeScaffolding3()
    }

    abstract protected fun removeScaffolding3()

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
