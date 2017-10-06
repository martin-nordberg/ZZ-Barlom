//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of class VLinkedList.
 */
class VLinkedListTests {

    @Test
    fun `A versioned linked list grows and shrinks with old versions preserved`() {

        val revHistory = RevisionHistory("Rev #0")

        var list: VLinkedList<Int>? = null

        revHistory.update(2) {

            list = VLinkedList()
            "Rev #1"

        }

        revHistory.review {

            assertEquals(0, list!!.size)
            assertEquals(0, list!!.asList().size)
            assertTrue(list!!.isEmpty)
            assertFalse(list!!.isNotEmpty)

        }

        revHistory.update(2) {

            list!!.add(10)
            "Rev #2"

        }

        revHistory.review {

            assertEquals(1, list!!.size)
            assertEquals(1, list!!.asList().size)
            assertTrue(list!!.asList().contains(10))
            assertTrue(list!!.contains(10))
            assertFalse(list!!.isEmpty)
            assertTrue(list!!.isNotEmpty)

        }

        revHistory.review(1) {

            assertEquals(0, list!!.size)
            assertEquals(0, list!!.asList().size)
            assertTrue(list!!.isEmpty)
            assertFalse(list!!.isNotEmpty)

        }

        revHistory.update(2) {

            list!!.add(20)
            list!!.add(30)
            "Rev #3"

        }

        revHistory.review {

            assertEquals(3, list!!.size)
            assertEquals(3, list!!.asList().size)
            assertTrue(list!!.asList().contains(10))
            assertTrue(list!!.asList().contains(20))
            assertTrue(list!!.asList().contains(30))
            assertTrue(list!!.contains(10))
            assertTrue(list!!.contains(20))
            assertTrue(list!!.contains(30))
            assertFalse(list!!.isEmpty)
            assertTrue(list!!.isNotEmpty)

        }

        revHistory.review(2) {

            assertEquals(1, list!!.size)
            assertEquals(1, list!!.asList().size)
            assertTrue(list!!.asList().contains(10))
            assertTrue(list!!.contains(10))
            assertFalse(list!!.isEmpty)
            assertTrue(list!!.isNotEmpty)

        }

        revHistory.review(1) {

            assertEquals(0, list!!.size)
            assertEquals(0, list!!.asList().size)
            assertTrue(list!!.isEmpty)
            assertFalse(list!!.isNotEmpty)

        }

        revHistory.update(2) {

            list!!.remove(20)
            list!!.add(40)
            "Rev #4"

        }

        revHistory.review {

            assertEquals(3, list!!.size)
            assertEquals(3, list!!.asList().size)
            assertTrue(list!!.asList().contains(10))
            assertFalse(list!!.asList().contains(20))
            assertTrue(list!!.asList().contains(30))
            assertTrue(list!!.asList().contains(40))
            assertTrue(list!!.contains(10))
            assertFalse(list!!.contains(20))
            assertTrue(list!!.contains(30))
            assertTrue(list!!.contains(40))
            assertFalse(list!!.isEmpty)
            assertTrue(list!!.isNotEmpty)

            val list2 = list!!.asList()

            assertEquals(3, list2.size)
            assertEquals(40, list2[0])
            assertEquals(30, list2[1])
            assertEquals(10, list2[2])

            val slist = list!!.sortedBy { item -> item }

            assertEquals(3, slist.size)
            assertEquals(10, slist[0])
            assertEquals(30, slist[1])
            assertEquals(40, slist[2])

            var count = 0
            list!!.forEach { count += 1 }

            assertEquals(3, count)

            count = 0
            list!!.forEachWhile { item -> count += 1; item < 30 }

            assertEquals(1, count)
        }

        revHistory.review(3) {

            assertEquals(3, list!!.size)
            assertEquals(3, list!!.asList().size)
            assertTrue(list!!.asList().contains(10))
            assertTrue(list!!.asList().contains(20))
            assertTrue(list!!.asList().contains(30))
            assertFalse(list!!.asList().contains(40))
            assertTrue(list!!.contains(10))
            assertTrue(list!!.contains(20))
            assertTrue(list!!.contains(30))
            assertFalse(list!!.isEmpty)
            assertTrue(list!!.isNotEmpty)

        }

        revHistory.review(2) {

            assertEquals(1, list!!.size)
            assertEquals(1, list!!.asList().size)
            assertTrue(list!!.asList().contains(10))
            assertTrue(list!!.contains(10))
            assertFalse(list!!.isEmpty)
            assertTrue(list!!.isNotEmpty)

        }

        revHistory.review(1) {

            assertEquals(0, list!!.size)
            assertEquals(0, list!!.asList().size)
            assertTrue(list!!.isEmpty)
            assertFalse(list!!.isNotEmpty)

        }

    }

}
