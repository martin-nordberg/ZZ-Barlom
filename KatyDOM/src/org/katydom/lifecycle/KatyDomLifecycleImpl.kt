//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.lifecycle

import org.katydom.abstractnodes.KatyDomElement
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.abstractnodes.KatyDomNode
import org.katydom.api.KatyDomLifecycle
import org.katydom.concretenodes.KatyDomText
import org.w3c.dom.Element

/**
 * Implementation of the KatyDOM lifecycle to build and patch a real DOM tree from an initial and changed virtual DOM
 * tree.
 */
internal class KatyDomLifecycleImpl : KatyDomLifecycle {

    override fun build(domElement: Element, katyDomElement: KatyDomHtmlElement): Element {

        val document = domElement.ownerDocument!!

        // Create the top level element.
        val root: Element = document.createElement(katyDomElement.nodeName)

        // Fill it in from the virtual DOM.
        establishNewHtmlElement(root, katyDomElement)

        // Replace the placeholder element.
        val parent = domElement.parentNode!!
        parent.insertBefore(root, domElement)
        parent.removeChild(domElement)

        return root

    }

    override fun update(domElement: Element, oldKatyDomElement: KatyDomHtmlElement, newKatyDomElement: KatyDomHtmlElement) {

        patchHtmlElementAttributes(domElement, oldKatyDomElement, newKatyDomElement)

        patchChildNodes(domElement, oldKatyDomElement, newKatyDomElement)

    }

    /**
     * Creates a new DOM node corresponding to the given virtual DOM node.
     */
    private fun establishNewNode(domElement: Element, katyDomNode: KatyDomNode) {

        val document = domElement.ownerDocument;

        for (childNode in katyDomNode.childNodes) {

            when (childNode) {
                is KatyDomHtmlElement -> {
                    val childElement = document.createElement(childNode.nodeName)
                    establishNewHtmlElement(childElement, childNode)
                    domElement.appendChild(childElement)
                }
                is KatyDomElement     -> {
                    val childElement = document.createElement(childNode.nodeName)
                    establishNewElement(childElement, childNode)
                    domElement.appendChild(childElement)
                }
                is KatyDomText        -> {
                    val childText = document.createTextNode(childNode.textChars)
                    domElement.appendChild(childText)
                }
            }

        }

    }

    /**
     * Creates a new DOM node corresponding to the given virtual DOM node.
     */
    private fun establishNewElement(domElement: Element, katyDomElement: KatyDomElement) {

        establishNewNode(domElement, katyDomElement)

        katyDomElement.id.ifPresent { id ->
            domElement.setAttribute("id", id)
        }

        if (katyDomElement.classList.isNotEmpty()) {
            domElement.setAttribute("class", katyDomElement.classList.joinToString(" "))
        }

        for (attr in katyDomElement.otherAttributes) {
            domElement.setAttribute(attr.key, attr.value)
        }

        for (attr in katyDomElement.dataset) {
            domElement.setAttribute("data-" + attr.key, attr.value)
        }

    }

    /**
     * Creates a new DOM node corresponding to the given virtual DOM node.
     */
    private fun establishNewHtmlElement(domElement: Element, katyDomHtmlElement: KatyDomHtmlElement) {

        establishNewElement(domElement, katyDomHtmlElement)

        katyDomHtmlElement.style.ifPresent { style ->
            domElement.setAttribute("style", style)
        }

    }

    private fun patchHtmlElementAttributes(domElement: Element, oldKatyDomNode: KatyDomHtmlElement, newKatyDomNode: KatyDomHtmlElement) {

        patchElementAttributes( domElement, oldKatyDomNode, newKatyDomNode)

        // Patch the style attribute.
        newKatyDomNode.style.ifPresent { style ->
            if (!oldKatyDomNode.style.contains(style)) {
                domElement.setAttribute("style", style)
            }
        }
        newKatyDomNode.style.ifNotPresent {
            oldKatyDomNode.style.ifPresent {
                domElement.removeAttribute("style")
            }
        }

    }

    private fun patchElementAttributes(domElement: Element, oldKatyDomNode: KatyDomHtmlElement, newKatyDomNode: KatyDomHtmlElement) {

        // Patch the id attribute as needed.
        newKatyDomNode.id.ifPresent { id ->
            if (!oldKatyDomNode.id.contains(id)) {
                domElement.setAttribute("id", id)
            }
        }
        newKatyDomNode.id.ifNotPresent {
            oldKatyDomNode.id.ifPresent {
                domElement.removeAttribute("id")
            }
        }

        // Patch the class attribute as needed.
        if (newKatyDomNode.classList.isEmpty() && oldKatyDomNode.classList.isNotEmpty()) {
            domElement.removeAttribute("class")
        }
        else if (newKatyDomNode.classList.isNotEmpty() &&
            newKatyDomNode.classList != oldKatyDomNode.classList) {
            domElement.setAttribute("class", newKatyDomNode.classList.joinToString(" "))
        }

        // Patch other attributes.
        for ((key, newValue) in newKatyDomNode.otherAttributes) {
            if (newValue != oldKatyDomNode.otherAttributes.get(key)) {
                domElement.setAttribute(key, newValue)
            }
        }
        for ((key, _) in oldKatyDomNode.otherAttributes) {
            if (!newKatyDomNode.otherAttributes.contains(key)) {
                domElement.removeAttribute(key)
            }
        }

        // Patch data attributes.
        for ((key, newValue) in newKatyDomNode.dataset) {
            if (newValue != oldKatyDomNode.dataset.get(key)) {
                domElement.setAttribute("data-" + key, newValue)
            }
        }
        for ((key, _) in oldKatyDomNode.dataset) {
            if (!newKatyDomNode.dataset.contains(key)) {
                domElement.removeAttribute("data-" + key)
            }
        }

    }

    private fun patchChildNodes(domElement: Element, oldKatyDomNode: KatyDomNode, newKatyDomNode: KatyDomNode) {

        if ( oldKatyDomNode.childNodes.isEmpty() ) {

            establishNewNode( domElement, newKatyDomNode )

        }
        else if ( newKatyDomNode.childNodes.isEmpty() ) {

            while( domElement.hasChildNodes() ) {
                domElement.removeChild( domElement.firstChild )
            }

        }
        else {

            // TODO: The main point of virtual DOM is still TBD here: just patch the differences.

            while( domElement.hasChildNodes() ) {
                domElement.removeChild( domElement.firstChild )
            }

            establishNewNode( domElement, newKatyDomNode )

        }

    }

}
