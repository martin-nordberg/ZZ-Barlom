//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

import org.barlom.infrastructure.uuids.prefetchUuid
import org.barlom.presentation.client.runApplication
import org.barlom.presentation.client.state.initializeAppState
import org.barlom.presentation.client.views.view
import org.barlom.presentation.tests.runTests


/**
 * Main entry point for the Barlom Metamodeling client application.
 */
fun main(args: Array<String>) {

    // Run unit tests instead of the application if they are included via <script>.
    if (runTests()) {
        return
    }


    // Start up the UUID machinery.
    prefetchUuid()

    // Run the application.
    runApplication("app", ::initializeAppState, ::view)


    console.log("Lifecycle started.")

}
