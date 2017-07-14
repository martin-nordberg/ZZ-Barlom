//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.server.main

import org.apache.logging.log4j.LogManager
import spark.kotlin.get
import spark.kotlin.staticFiles
import spark.kotlin.stop

/**
 * Main program for Barlom Server.
 */
fun main(args: Array<String>) {

    val userDir = System.getProperty("user.dir")
    logger.info("User Directory = " + userDir)
    staticFiles.externalLocation(userDir + "/out/production");

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

}

val logger = LogManager.getLogger("main")
