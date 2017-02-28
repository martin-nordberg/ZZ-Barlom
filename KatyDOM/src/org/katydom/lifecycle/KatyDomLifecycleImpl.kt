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
import org.katydom.spi.DocumentProxy
import org.katydom.spi.ElementProxy

/**
 * Implementation of the KatyDOM lifecycle to build and patch a real DOM tree from an initial and changed virtual DOM
 * tree.
 */
internal class KatyDomLifecycleImpl : KatyDomLifecycle {

    override fun build(domElement: ElementProxy, firstKatyDomElement: KatyDomHtmlElement) {

        val document = domElement.ownerDocument
        val root: ElementProxy = document.createElement(firstKatyDomElement.nodeName)

        establishNewNode(document, root, firstKatyDomElement)

        val parent = domElement.parentNode

        if (parent != null) {
            parent.insertBefore(root, domElement)
            parent.removeChild(domElement)
        }

    }

    override fun patch(oldKatyDomElement: KatyDomHtmlElement, newKatyDomElement: KatyDomHtmlElement) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun establishNewNode(document: DocumentProxy, domElement: ElementProxy, katyDomNode: KatyDomNode) {

        if (katyDomNode is KatyDomElement) {

            katyDomNode.id.ifPresent {
                id ->
                domElement.setAttribute("id", id)
            }

            if (katyDomNode.classList.isNotEmpty()) {
                domElement.setAttribute("class", katyDomNode.classList.joinToString(" "))
            }

            for (attr in katyDomNode.otherAttributes) {
                domElement.setAttribute(attr.key, attr.value)
            }

            for (attr in katyDomNode.dataset) {
                domElement.setAttribute("data-" + attr.key, attr.value)
            }

            if (katyDomNode is KatyDomHtmlElement) {
                katyDomNode.style.ifPresent { style ->
                    domElement.setAttribute("style", style)
                }
            }

        }

        for (childNode in katyDomNode.childNodes) {

            if (childNode is KatyDomElement) {
                val childElement = document.createElement(childNode.nodeName)
                establishNewNode(document, childElement, childNode)
                domElement.appendChild(childElement)
            }
            else if (childNode is KatyDomText) {
                val childText = document.createTextNode(childNode.textChars)
                domElement.appendChild(childText)
            }

        }

    }
}