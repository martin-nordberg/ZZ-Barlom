//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.uuids

import org.w3c.xhr.XMLHttpRequest

/**
 * Makes a version 1 UUID.
 * @return the new UUID.
 */
fun makeUuid(): Uuid {

    if ( nextUuid == null ) {

        nextUuid = waitingUuid
        waitingUuid = null

        prefetchUuid()

    }

    if ( nextUuid == null ) {

        console.log( "Retrieving nextUuid synchronously....")

        val request = XMLHttpRequest()
        request.open("GET", "/Barlom/uuid", false)
        request.send(null)

        if (request.status == 200.toShort()) {
            val json = request.responseText
            nextUuid = Uuid.fromString( JSON.parse<UuidObj>(json).uuid )
            console.log( "Retrieved nextUuid.")
        }

    }

    val result = nextUuid!!

    nextUuid = nextUuid!!.nextInBlock

    return result

}

/** Retrieves a UUID into waitingUuid. */
fun prefetchUuid() {

    if ( prefetchInProgress ) {
        return
    }

    prefetchInProgress = true

    console.log("Retrieving waitingUuid asynchronously....")

    val request = XMLHttpRequest()
    request.open("GET", "/Barlom/uuid")
    request.send(null)

    request.onload = {

        if (request.readyState == 4.toShort() && request.status == 200.toShort()) {
            val json = request.responseText
            waitingUuid = Uuid.fromString(JSON.parse<UuidObj>(json).uuid)
            console.log( "Retrieved waitingUuid.")
        }
        else {
            console.log( "Failed to retrieve waitingUuid.")
        }

        prefetchInProgress = false
        true

    }

}

/** The next available UUID in a block. */
private var nextUuid : Uuid? = null

/** A pre-fetched block starting UUID waiting to be used when the current block is exhausted. */
private var waitingUuid : Uuid? = null

/** True if waitingUuid is currently being fetched. */
private var prefetchInProgress : Boolean = false

/** Type for JSON retrieved from /uuid endpoint. */
data class UuidObj( val uuid: String )
