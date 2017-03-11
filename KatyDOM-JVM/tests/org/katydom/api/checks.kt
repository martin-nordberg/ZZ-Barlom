//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.abstractnodes.KatyDomNode
import org.katydom.kdom.KDomDocument
import org.w3c.dom.Element

/**
 * Builds the DOM for a given KatyDOM node and checks the HTML output against what's expected.
 */
internal fun checkBuild(expectedHtml: String, vdomNode: KatyDomNode) {

    val body = KDomDocument().createElement("body")
    val div = body.ownerDocument.createElement("div")

    body.appendChild(div)

    assertEquals(div, body.firstChild)

    val lifecycle = makeKatyDomLifecycle()

    if (vdomNode is KatyDomHtmlElement) {
        lifecycle.build(div, vdomNode)
    }

    assertEquals(expectedHtml, body.firstChild?.toHtmlString())

}

/**
 * Builds the DOM for a given KatyDOM node and checks the HTML output against what's expected before and after a patch.
 */
internal fun checkPatch(expectedHtml2: String, vdomNode2: KatyDomNode, expectedHtml1: String, vdomNode1: KatyDomNode) {

    val body = KDomDocument().createElement("body")
    val div = body.ownerDocument.createElement("div")

    body.appendChild(div)

    assertEquals(div, body.firstChild)

    val lifecycle = makeKatyDomLifecycle()

    if (vdomNode1 is KatyDomHtmlElement && vdomNode2 is KatyDomHtmlElement) {
        lifecycle.build(div, vdomNode1)

        assertEquals(expectedHtml1, body.firstChild?.toHtmlString())

        lifecycle.update(body.firstChild as Element, vdomNode1, vdomNode2)

        assertEquals(expectedHtml2, body.firstChild?.toHtmlString())
    }
    else {
        fail("Function checkPatch expects HTML elements.")
    }


}

