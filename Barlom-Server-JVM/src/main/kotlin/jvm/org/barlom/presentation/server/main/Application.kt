//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.barlom.presentation.server.main

import org.apache.logging.log4j.LogManager
import jvm.org.barlom.presentation.server.routes.util.uuidRoute
import spark.kotlin.get
import spark.kotlin.staticFiles
import spark.kotlin.stop
import java.io.File

/**
 * Main program for Barlom Server.
 */
fun main(args: Array<String>) {

    val userDir = System.getProperty("user.dir")
    logger.info("User Directory = " + userDir)

    val staticFolder = File(File(userDir).parent, "build/website").absolutePath
    logger.info("Static Files Path = " + staticFolder)

    staticFiles.externalLocation(staticFolder);

    logger.info("Server starting ...")

    get("/hello") {
        "Hello World"
    }

    get( "/bye" ) {
        "Goodbye"
    }

    get( "/stop" ) {
        stop()
        "Server stopped"
    }

    val root = "/Barlom"

    uuidRoute(root)

}

val logger = LogManager.getLogger("main")
