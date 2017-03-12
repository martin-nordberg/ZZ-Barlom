//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.lifecycle

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.KatyDomLifecycle
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
        katyDomElement.establish(root)

        // Replace the placeholder element.
        val parent = domElement.parentNode!!
        parent.insertBefore(root, domElement)
        parent.removeChild(domElement)

        return root

    }

    override fun update(domElement: Element, oldKatyDomElement: KatyDomHtmlElement, newKatyDomElement: KatyDomHtmlElement) {

        newKatyDomElement.patch(oldKatyDomElement)

    }

}
