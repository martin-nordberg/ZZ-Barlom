//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests of class RevisionHistory.
 */
class RevisionHistoryTests {

    @Test
    fun `A revision history supports transactions`() {

        val revHistory = RevisionHistory("Rev #0")

        var s: Sample? = null

        revHistory.update("Rev #1", 2) {

            s = Sample()

        }

        assertEquals("Rev #1", revHistory.lastRevision.description)
        assertEquals(1, revHistory.lastRevision.revisionNumber)
        assertEquals("Rev #0", revHistory.lastRevision.priorRevision?.description)
        assertEquals(0, revHistory.lastRevision.priorRevision?.revisionNumber)
        assertNull(revHistory.lastRevision.priorRevision?.priorRevision)

        revHistory.review {

            assertEquals(1, s!!.value.get())
            assertEquals("one", s!!.name.get())
            assertFalse( s!!.isEven.get())

        }

        revHistory.update("Rev #2", 2) {

            s!!.value.set(2)
            s!!.name.set("two")
            s!!.isEven.set(true)

        }

        assertEquals("Rev #2", revHistory.lastRevision.description)
        assertEquals(2, revHistory.lastRevision.revisionNumber)
        assertEquals("Rev #1", revHistory.lastRevision.priorRevision?.description)

        revHistory.review {

            assertEquals(2, s!!.value.get())
            assertEquals("two", s!!.name.get())
            assertTrue( s!!.isEven.get())

        }

        revHistory.review(1) {

            assertEquals(1, s!!.value.get())
            assertEquals("one", s!!.name.get())
            assertFalse( s!!.isEven.get())

        }

        revHistory.update("Rev #3", 2) {

            s!!.value.increment()
            s!!.name.set("three")
            s!!.isEven.set(false)

        }

        assertEquals("Rev #3", revHistory.lastRevision.description)
        assertEquals(3, revHistory.lastRevision.revisionNumber)
        assertEquals("Rev #2", revHistory.lastRevision.priorRevision?.description)

        revHistory.review {

            assertEquals(3, s!!.value.get())
            assertEquals("three", s!!.name.get())
            assertFalse( s!!.isEven.get())

        }

        revHistory.review(3) {

            assertEquals(3, s!!.value.get())
            assertEquals("three", s!!.name.get())
            assertFalse( s!!.isEven.get())

        }

        revHistory.review(2) {

            assertEquals(2, s!!.value.get())
            assertEquals("two", s!!.name.get())
            assertTrue( s!!.isEven.get())

        }

        revHistory.review(1) {

            assertEquals(1, s!!.value.get())
            assertEquals("one", s!!.name.get())
            assertFalse( s!!.isEven.get())

        }

    }

    class Sample {

        val value = VInt(1)
        val name = V<String>("one")
        val isEven = V<Boolean>( false )

    }

}
