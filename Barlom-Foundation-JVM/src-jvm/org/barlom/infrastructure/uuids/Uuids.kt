//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.uuids


/**
 * Makes a version 1 UUID.
 * @return the new UUID.
 */
fun makeUuid(): Uuid {
    return Uuid(UuidsJava.getNextTimeAndVersion(false), UuidsJava.CLOCK_SEQ_AND_NODE)
}

/**
 * Makes a UUID with a block of 256 sequential UUIDs reserved. The next UUID returned by this generator will be
 * different in its first three bytes, so a remote client can safely create up to 256 UUIDs from the given one by
 * incrementing only the fourth byte. The fourth byte will be 0x00.
 * @return the new UUID.
 */
fun makeUuidWithReservedBlock(): Uuid {
    return Uuid(UuidsJava.getNextTimeAndVersion(true), UuidsJava.CLOCK_SEQ_AND_NODE)
}
