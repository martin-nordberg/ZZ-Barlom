//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.barlom.presentation.server.routes.util

import org.apache.logging.log4j.LogManager
import jvm.org.barlom.infrastructure.logging.logInfo
import x.org.barlom.infrastructure.uuids.makeUuidWithReservedBlock
import spark.kotlin.get


fun uuidRoute( root: String) {

    get(root + "/uuid" ) {
        response.type("application/json")

        val uuid = makeUuidWithReservedBlock().toString()
        logger.logInfo { "Created UUID: $uuid" }
        """{"uuid":"$uuid"}"""
    }

}

val logger = LogManager.getLogger("main")
