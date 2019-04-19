//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.uuids

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import x.barlom.infrastructure.uuids.Uuid
import x.barlom.infrastructure.uuids.makeUuid
import x.barlom.infrastructure.uuids.makeUuidWithReservedBlock
import java.lang.Math.min

/**
 * Tests of class Uuid and associated builder functions.
 */
@Suppress("RemoveRedundantBackticks")
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

    @Test
    fun `Ensures hash codes are fairly uniformly distributed for reserved blocks`() {

        val tableSize = 1021
        val hashCodeCounts = Array<Int>(tableSize) { 0 }

        for (i in 1..100) {

            var uuid = makeUuidWithReservedBlock()
            hashCodeCounts[Math.floorMod(uuid.hashCode(), tableSize)] += 1

            while (uuid.hasNextInReservedBlock()) {
                uuid = uuid.nextInReservedBlock()
                hashCodeCounts[Math.floorMod(uuid.hashCode(), tableSize)] += 1
            }

        }

        var minCount = 100 * 256
        var maxCount = 0
        for (h in 0 until tableSize) {
            minCount = min(minCount, hashCodeCounts[h])
            maxCount = min(maxCount, hashCodeCounts[h])
        }

        assertTrue(maxCount - minCount < 10, "Max=$maxCount; min=$minCount")

    }

    companion object {

        val UuidPattern = Regex("^[a-f0-9]{8}-[a-f0-9]{4}-1[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$")

        val ReservedBlockUuidPattern = Regex("^[a-f0-9]{6}00-[a-f0-9]{4}-1[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$")

    }

}
