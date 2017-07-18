//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.types

import org.barlom.domain.metamodel.api.types.Uuid
import org.barlom.infrastructure.utilities.uuids.makeUuid
import org.barlom.infrastructure.utilities.uuids.makeUuidWithReservedBlock
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of class Uuid and associated builder functions.
 */
class UuidTests {

    @Test
    fun `A Uuid can be converted from and back to a string`() {

        val uuid = "123e4567-e89b-12d3-a456-426655440000"

        assertEquals(uuid, Uuid.fromString(uuid).toString())

    }

    @Test
    fun `Makes a single UUID in correct format`() {

        val uuid = makeUuid().toString()

        assertTrue(uuid.matches(UuidPattern))

    }

    @Test
    fun `Makes a block-reserving UUID in correct format`() {

        val uuid = makeUuidWithReservedBlock().toString()

        assertTrue(uuid.matches(ReservedBlockUuidPattern))

    }

    @Test
    fun `Ensures that successive UUIDs are unique`() {

        val uuid1 = makeUuid().toString()
        val uuid2 = makeUuid().toString()

        assertTrue(uuid1 != uuid2)

    }

    @Test
    fun `Ensures that successive block-reserving UUIDs are unique`() {

        val uuid1 = makeUuidWithReservedBlock().toString()
        val uuid2 = makeUuidWithReservedBlock().toString()

        assertTrue(uuid1.matches(ReservedBlockUuidPattern))
        assertTrue(uuid2.matches(ReservedBlockUuidPattern))

        assertTrue(uuid1 != uuid2)

    }

    companion object {

        val UuidPattern = Regex("^[a-f0-9]{8}-[a-f0-9]{4}-1[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$")

        val ReservedBlockUuidPattern = Regex("^[a-f0-9]{6}00-[a-f0-9]{4}-1[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$")

    }
}
