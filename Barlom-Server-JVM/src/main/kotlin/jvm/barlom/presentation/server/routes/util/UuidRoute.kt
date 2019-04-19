//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.presentation.server.routes.util

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.apache.logging.log4j.LogManager
import jvm.barlom.infrastructure.logging.logInfo
import x.barlom.infrastructure.uuids.makeUuidWithReservedBlock

/**
 * Defines a JSON endpoint below the given [root] path for retrieving UUIDs.
 */
fun Routing.getUuid(root: String) {

    get(root + "/uuid") {

        val uuid = makeUuidWithReservedBlock().toString()
        logger.logInfo { "Created UUID: $uuid" }

        call.respondText(ContentType.parse("application/json")) {
            """{"uuid":"$uuid"}"""
        }

    }

}

val logger = LogManager.getLogger("main")
