//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

import js.katydid.vdom.api.runApplication
import x.barlom.infrastructure.uuids.prefetchUuid
import js.barlom.presentation.client.application.Application


/**
 * Main entry point for the Barlom Metamodeling client application.
 */
fun main(args: Array<String>) {

    // TODO:
//    // Run unit tests instead of the application if they are included via <script>.
//    if (runTests()) {
//        return
//    }


    // Start up the UUID machinery.
    prefetchUuid()

    // Run the application.
    val application = Application()

    runApplication("app", application)

    console.log("Lifecycle started.")

}
