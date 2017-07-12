//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.server.main

import spark.kotlin.get
import spark.kotlin.stop

/**
 * Main program for Barlom Server.
 */
fun main(args: Array<String>) {

    println("Server starting ...")

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
