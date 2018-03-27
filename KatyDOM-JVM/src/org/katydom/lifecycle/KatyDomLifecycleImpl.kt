//
// (C) Copyright 2017-2018 Martin E. Nordberg III
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
internal class KatyDomLifecycleImpl<Message> : KatyDomLifecycle<Message> {

    override fun build(domElement: Element, katyDomElement: KatyDomHtmlElement<Message>) {

        val document = domElement.ownerDocument!!

        // Create the top level element.
        val root: Element = document.createElement(katyDomElement.nodeName)

        // Fill it in from the virtual DOM.
        katyDomElement.establish(root)

        // Replace the placeholder element.
        val parent = domElement.parentNode!!
        parent.insertBefore(root, domElement)
        parent.removeChild(domElement)

    }

    override fun patch(oldKatyDomElement: KatyDomHtmlElement<Message>, newKatyDomElement: KatyDomHtmlElement<Message>) {
        newKatyDomElement.patch(oldKatyDomElement)
    }

}
