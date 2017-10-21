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

        var list0: VLinkedList<Int>? = null

        // empty starting point
        revHistory.update(2) {

            list0 = VLinkedList()
            "Rev #1"

        }

        val list: VLinkedList<Int> = list0!!

        assertEquals(1, revHistory.lastRevision.revisionNumber)

        fun checkRev1() = revHistory.review( 1 ) {

            assertEquals(0, list.size)
            assertEquals(0, list.asList().size)
            assertTrue(list.isEmpty)
            assertFalse(list.isNotEmpty)

        }

        checkRev1()

        // add one item
        revHistory.update(2) {

            list.add(10)
            "Rev #2"

        }

        assertEquals(2, revHistory.lastRevision.revisionNumber)

        fun checkRev2() = revHistory.review( 2 ) {

            assertEquals(1, list.size)
            assertEquals(1, list.asList().size)
            assertTrue(list.asList().contains(10))
            assertTrue(list.contains(10))
            assertFalse(list.isEmpty)
            assertTrue(list.isNotEmpty)

        }

        checkRev2()
        checkRev1()

        // add two more items
        revHistory.update(2) {

            list.add(20)
            list.add(30)
            "Rev #3"

        }

        assertEquals(3, revHistory.lastRevision.revisionNumber)

        fun checkRev3() = revHistory.review(3) {

            assertEquals(3, list.size)
            assertEquals(3, list.asList().size)
            assertTrue(list.asList().contains(10))
            assertTrue(list.asList().contains(20))
            assertTrue(list.asList().contains(30))
            assertTrue(list.contains(10))
            assertTrue(list.contains(20))
            assertTrue(list.contains(30))
            assertFalse(list.isEmpty)
            assertTrue(list.isNotEmpty)

        }

        checkRev3()
        checkRev2()
        checkRev1()

        // remove one; add one
        revHistory.update(2) {

            list.remove(20)
            list.add(40)
            "Rev #4"

        }

        assertEquals(4, revHistory.lastRevision.revisionNumber)

        fun checkRev4() = revHistory.review(4) {

            assertEquals(3, list.size)
            assertEquals(3, list.asList().size)
            assertTrue(list.asList().contains(10))
            assertFalse(list.asList().contains(20))
            assertTrue(list.asList().contains(30))
            assertTrue(list.asList().contains(40))
            assertTrue(list.contains(10))
            assertFalse(list.contains(20))
            assertTrue(list.contains(30))
            assertTrue(list.contains(40))
            assertFalse(list.isEmpty)
            assertTrue(list.isNotEmpty)

            val list2 = list.asList()

            assertEquals(3, list2.size)
            assertEquals(40, list2[0])
            assertEquals(30, list2[1])
            assertEquals(10, list2[2])

            val slist = list.sortedBy { item -> item }

            assertEquals(3, slist.size)
            assertEquals(10, slist[0])
            assertEquals(30, slist[1])
            assertEquals(40, slist[2])

            var count = 0
            list.forEach { count += 1 }

            assertEquals(3, count)

            count = 0
            list.forEachWhile { item -> if ( item != 30 ) { count += 1; true } else false }

            assertEquals(1, count)

            val list3 = list.map { item -> 10*item }

            assertEquals(3, list3.size)
            assertEquals(400, list3[0])
            assertEquals(300, list3[1])
            assertEquals(100, list3[2])

        }

        checkRev4()
        checkRev3()
        checkRev2()
        checkRev1()

        // remove the first item
        revHistory.update(2) {

            list.remove(40)
            "Rev #5"

        }

        assertEquals(5, revHistory.lastRevision.revisionNumber)

        revHistory.review {

            assertEquals(2, list.size)
            assertEquals(2, list.asList().size)
            assertTrue(list.asList().contains(10))
            assertFalse(list.asList().contains(20))
            assertTrue(list.asList().contains(30))
            assertFalse(list.asList().contains(40))
            assertTrue(list.contains(10))
            assertFalse(list.contains(20))
            assertTrue(list.contains(30))
            assertFalse(list.contains(40))
            assertFalse(list.isEmpty)
            assertTrue(list.isNotEmpty)

            val list2 = list.asList()

            assertEquals(2, list2.size)
            assertEquals(30, list2[0])
            assertEquals(10, list2[1])

            var count = 0
            list.forEach { count += 1 }

            assertEquals(2, count)

            count = 0
            list.forEachWhile { item -> if ( item != 30 ) { count += 1; true } else false }

            assertEquals(0, count)

            val list3 = list.map { item -> 10*item }

            assertEquals(2, list3.size)
            assertEquals(300, list3[0])
            assertEquals(100, list3[1])

        }

        checkRev4()
        checkRev3()
        checkRev2()
        checkRev1()

    }

}
