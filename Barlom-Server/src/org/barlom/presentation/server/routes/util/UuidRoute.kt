//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.server.routes.util

import org.apache.logging.log4j.LogManager
import org.barlom.infrastructure.uuids.makeUuidWithReservedBlock
import spark.kotlin.get

fun uuidRoute( root: String) {

    get(root + "/uuid") {
        val uuid = makeUuidWithReservedBlock().toString()
        logger.info("Created UUID: ", uuid)
        uuid
    }

}

val logger = LogManager.getLogger("main")
