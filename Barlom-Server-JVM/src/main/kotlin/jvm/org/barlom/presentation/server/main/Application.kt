//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.barlom.presentation.server.main

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.default
import io.ktor.content.files
import io.ktor.content.static
import io.ktor.content.staticRootFolder
import io.ktor.features.CallLogging
import io.ktor.features.ConditionalHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.ShutDownUrl
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import jvm.org.barlom.presentation.server.routes.util.getUuid
import org.apache.logging.log4j.LogManager
import org.slf4j.event.Level
import java.io.File

/**
 * Main program for Barlom Server.
 */
fun main(args: Array<String>) {

    val userDir = System.getProperty("user.dir")
    logger.info("User Directory = " + userDir)

    val staticFolder = File(File(userDir).parent, "build").absolutePath
    logger.info("Static Files Path = " + staticFolder)

    val root = "/barlom"

    val server = embeddedServer(Jetty, 8080) {

        routing {

            static(root + "/metamodel") {

                staticRootFolder = File(staticFolder)
                files("website")
                default("website/index.html")

                install(ConditionalHeaders)

            }

            getUuid(root)

            get("/") {
                call.respondText("Hello, world!", ContentType.Text.Html)
            }

        }

        install(ShutDownUrl.ApplicationCallFeature) {
            shutDownUrl = root + "/exit"
            exitCodeSupplier = { 0 }
        }

        install(CallLogging) {
            level = Level.INFO
        }

    }

    logger.info("Server starting ...")
    server.start(wait = true)

}


val logger = LogManager.getLogger("main")
