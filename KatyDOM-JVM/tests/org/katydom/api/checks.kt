//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.abstractnodes.KatyDomNode
import org.katydom.kdom.KDomDocument

/**
 * Builds the DOM for a given KatyDOM node and checks the HTML output against what's expected.
 */
internal fun <Message> checkBuild(expectedHtml: String, vdomNode: KatyDomNode<Message>) {

    val body = KDomDocument().createElement("body")
    val div = body.ownerDocument.createElement("div")

    body.appendChild(div)

    assertEquals(div, body.firstChild)

    val lifecycle = makeKatyDomLifecycle<Message>()

    if (vdomNode is KatyDomHtmlElement) {
        lifecycle.build(div, vdomNode)
    }

    assertEquals(expectedHtml, body.firstChild?.toHtmlString())

}

/**
 * Builds the DOM for a given KatyDOM node and checks the HTML output against what's expected before and after a patch.
 */
internal fun <Message> checkPatch(expectedHtml2: String, vdomNode2: KatyDomNode<Message>, expectedHtml1: String,
                                  vdomNode1: KatyDomNode<Message>) {

    val body = KDomDocument().createElement("body")
    val div = body.ownerDocument.createElement("div")

    body.appendChild(div)

    assertEquals(div, body.firstChild)

    val lifecycle = makeKatyDomLifecycle<Message>()

    if (vdomNode1 is KatyDomHtmlElement && vdomNode2 is KatyDomHtmlElement) {
        lifecycle.build(div, vdomNode1)

        assertEquals(expectedHtml1, body.firstChild?.toHtmlString())

        lifecycle.patch(vdomNode1, vdomNode2)

        assertEquals(expectedHtml2, body.firstChild?.toHtmlString())
    }
    else {
        fail("Function checkPatch expects HTML elements.")
    }

}

