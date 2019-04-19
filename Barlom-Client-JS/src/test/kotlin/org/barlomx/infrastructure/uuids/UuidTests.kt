package org.barlomx.infrastructure.uuids

import x.barlom.infrastructure.uuids.Uuid
import x.barlom.infrastructure.uuids.makeUuid
import x.barlom.infrastructure.uuids.prefetchUuid
import kotlin.browser.window

fun testUuids( qunit: dynamic ) {

    qunit.test( "UUIDs can be retrieved successfully") { assert ->

        val done = assert.async()

        prefetchUuid()

        val uuids: MutableSet<Uuid> = mutableSetOf()

        window.setTimeout(
            {
                for (i in 1..200) {
                    val uuid = makeUuid()
                    assert.ok(uuid.toString().length == 36)
                    assert.ok(!uuids.contains(uuid))
                    uuids.add(uuid)
                }
            },
            1000
        )

        window.setTimeout(
            {
                for (i in 1..200) {
                    val uuid = makeUuid()
                    assert.ok(uuid.toString().length == 36)
                    assert.ok(!uuids.contains(uuid))
                    uuids.add(uuid)
                }

                done()
            },
            2000
        )

    }

}
