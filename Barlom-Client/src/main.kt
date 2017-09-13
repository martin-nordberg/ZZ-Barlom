//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

import org.barlom.presentation.tests.runTests
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.makeKatyDomLifecycle
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.browser.window


/**
 * Main entry point for the Barlom Metamodeling client application.
 */
fun main(args: Array<String> ) {

    if (runTests()) {
        return
    }

  ////

    val lifecycle = makeKatyDomLifecycle()

    val appElement = document.getElementById("app")

    if (appElement != null && vdomNode is KatyDomHtmlElement) {
        lifecycle.build(appElement, vdomNode)
    }

    console.log("DONE")

}