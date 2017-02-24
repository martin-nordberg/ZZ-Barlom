package org.katydom.lifecycle

import org.katydom.abstractnodes.KatyDomElement
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.abstractnodes.KatyDomNode
import org.katydom.api.KatyDomLifecycle
import org.katydom.concretenodes.KatyDomText
import org.katydom.spi.DomSpi
import org.w3c.dom.Element

/**
 * Implementation of the KatyDOM lifecycle to build and patch a real DOM tree from an initial and changed virtual DOM
 * tree.
 */
internal class KatyDomLifecycleImpl(private val spi: DomSpi) : KatyDomLifecycle {

    override fun build(domElement: Element, firstKatyDomElement: KatyDomHtmlElement) {

        val root = spi.createElement(firstKatyDomElement.nodeName)

        establishNewNode(root, firstKatyDomElement)

        val parent = spi.parentNode(domElement)

        if (parent != null) {
            spi.insertBefore(parent, root, domElement)
            spi.removeChild(parent, domElement)
        }

    }

    override fun patch(oldKatyDomElement: KatyDomHtmlElement, newKatyDomElement: KatyDomHtmlElement) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun establishNewNode(domElement: Element, katyDomNode: KatyDomNode) {

        if (katyDomNode is KatyDomElement) {

            if (katyDomNode.id != null) {
                domElement.setAttribute("id", katyDomNode.id)
            }

            if (katyDomNode.classList.isNotEmpty()) {
                domElement.setAttribute("class", katyDomNode.classList.joinToString(" "))
            }

            for (attr in katyDomNode.otherAttributes) {
                domElement.setAttribute(attr.name, attr.value)
            }

            for (attr in katyDomNode.dataset) {
                domElement.setAttribute("data-"+attr.name, attr.value)
            }

            if (katyDomNode is KatyDomHtmlElement && katyDomNode.style != null) {
                domElement.setAttribute("style", katyDomNode.style)
            }

        }

        for (childNode in katyDomNode.childNodes) {

            if (childNode is KatyDomElement) {
                val childElement = spi.createElement(childNode.nodeName)
                establishNewNode(childElement, childNode)
                spi.appendChild(domElement, childElement)
            }
            else if (childNode is KatyDomText) {
                val childText = spi.createTextNode(childNode.textChars)
                spi.appendChild(domElement, childText)
            }

        }

    }
}