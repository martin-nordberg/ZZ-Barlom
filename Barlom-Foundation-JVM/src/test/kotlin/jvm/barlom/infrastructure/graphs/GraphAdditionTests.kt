//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IGraph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of classes ReadableGraph and WritableGraph for adding concepts and connections.
 */
@Suppress("RemoveRedundantBackticks")
internal class GraphAdditionTests
    : GraphTests() {

    @Test
    fun `A graph starts out empty`() {

        fun check(g: IGraph) =
            with(g) {
                assertEquals(0, numConcepts)
                assertEquals(0, numConnections)
                assertTrue(isEmpty())
                assertFalse(isNotEmpty())
            }

        runWriteCheckTest(::check) {}

    }

    @Test
    fun `A graph can have concepts added`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val concept4 = SampleConcept()

        fun check12(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertTrue(containsConcept(concept2))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2, concept(concept2.id))
            }

        val gb = runWriteCheckTest(::check12) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
            }
        }

        fun check1234(g: IGraph) =
            with(g) {
                assertEquals(4, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertTrue(containsConcept(concept2))
                assertTrue(containsConcept(concept3))
                assertTrue(containsConcept(concept4))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2, concept(concept2.id))
                assertEquals(concept3, concept(concept3.id))
                assertEquals(concept4, concept(concept4.id))
            }

        runWriteCheckTest(gb, ::check1234) { g ->
            with(g) {
                addConcept(concept3)
                addConcept(concept4)
            }
        }

    }

    @Test
    fun `A concept cannot be added twice`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertTrue(containsConcept(concept2))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2, concept(concept2.id))
            }

        val gb = runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)

                assertThrows<IllegalArgumentException> {
                    addConcept(concept1)
                }
                assertThrows<IllegalArgumentException> {
                    addConcept(concept2)
                }
            }
        }

        runWriteCheckTest(gb, ::check) { g ->
            with(g) {
                assertThrows<IllegalArgumentException> {
                    addConcept(concept1)
                }
                assertThrows<IllegalArgumentException> {
                    addConcept(concept2)
                }
            }
        }

    }

    @Test
    fun `A graph can have undirected connections added`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val concept4 = SampleConcept()
        val connection12 = SampleUndirectedConnection(concept1.id, concept2.id)
        val connection13 = SampleUndirectedConnection(concept1.id, concept3.id)
        val connection14 = SampleUndirectedConnection(concept1.id, concept4.id)
        val connection23 = SampleUndirectedConnection(concept2.id, concept3.id)
        val connection24 = SampleUndirectedConnection(concept2.id, concept4.id)
        val connection34 = SampleUndirectedConnection(concept3.id, concept4.id)

        fun check2(g: IGraph) =
            with(g) {
                assertEquals(4, numConcepts)
                assertEquals(2, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertEquals(1, connections(concept4.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection34))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept3.id).contains(connection34))
                assertTrue(connections(concept4.id).contains(connection34))
                assertTrue(connectionsFrom(concept1.id).contains(connection12))
                assertTrue(connectionsFrom(concept2.id).contains(connection12))
                assertTrue(connectionsFrom(concept3.id).contains(connection34))
                assertTrue(connectionsFrom(concept4.id).contains(connection34))
                assertTrue(connectionsTo(concept1.id).contains(connection12))
                assertTrue(connectionsTo(concept2.id).contains(connection12))
                assertTrue(connectionsTo(concept3.id).contains(connection34))
                assertTrue(connectionsTo(concept4.id).contains(connection34))
            }

        val gb = runWriteCheckTest(::check2) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConcept(concept3)
                addConcept(concept4)
                addConnection(connection12)
                addConnection(connection34)
            }
        }

        fun check6(g: IGraph) =
            with(g) {
                assertEquals(4, numConcepts)
                assertEquals(6, numConnections)
                assertEquals(3, connections(concept1.id).size)
                assertEquals(3, connections(concept2.id).size)
                assertEquals(3, connections(concept3.id).size)
                assertEquals(3, connections(concept4.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertTrue(connections(concept1.id).contains(connection14))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection23))
                assertTrue(connections(concept2.id).contains(connection24))
                assertTrue(connections(concept3.id).contains(connection13))
                assertTrue(connections(concept3.id).contains(connection23))
                assertTrue(connections(concept3.id).contains(connection34))
                assertTrue(connections(concept4.id).contains(connection14))
                assertTrue(connections(concept4.id).contains(connection24))
                assertTrue(connections(concept4.id).contains(connection34))
                assertTrue(connectionsFrom(concept1.id).contains(connection12))
                assertTrue(connectionsFrom(concept1.id).contains(connection13))
                assertTrue(connectionsFrom(concept1.id).contains(connection14))
                assertTrue(connectionsFrom(concept2.id).contains(connection12))
                assertTrue(connectionsFrom(concept2.id).contains(connection23))
                assertTrue(connectionsFrom(concept2.id).contains(connection24))
                assertTrue(connectionsFrom(concept3.id).contains(connection13))
                assertTrue(connectionsFrom(concept3.id).contains(connection23))
                assertTrue(connectionsFrom(concept3.id).contains(connection34))
                assertTrue(connectionsFrom(concept4.id).contains(connection14))
                assertTrue(connectionsFrom(concept4.id).contains(connection24))
                assertTrue(connectionsFrom(concept4.id).contains(connection34))
                assertTrue(connectionsTo(concept1.id).contains(connection12))
                assertTrue(connectionsTo(concept1.id).contains(connection13))
                assertTrue(connectionsTo(concept1.id).contains(connection14))
                assertTrue(connectionsTo(concept2.id).contains(connection12))
                assertTrue(connectionsTo(concept2.id).contains(connection23))
                assertTrue(connectionsTo(concept2.id).contains(connection24))
                assertTrue(connectionsTo(concept3.id).contains(connection13))
                assertTrue(connectionsTo(concept3.id).contains(connection23))
                assertTrue(connectionsTo(concept3.id).contains(connection34))
                assertTrue(connectionsTo(concept4.id).contains(connection14))
                assertTrue(connectionsTo(concept4.id).contains(connection24))
                assertTrue(connectionsTo(concept4.id).contains(connection34))
            }

        runWriteCheckTest(gb, ::check6) { g ->
            with(g) {
                addConnection(connection13)
                addConnection(connection14)
                addConnection(connection23)
                addConnection(connection24)
            }
        }

    }

    @Test
    fun `Undirected connections cannot be added twice`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val connection12 = SampleUndirectedConnection(concept1.id, concept2.id)
        val connection13 = SampleUndirectedConnection(concept1.id, concept3.id)

        fun check(g: IGraph) =
            with(g) {
                assertEquals(3, numConcepts)
                assertEquals(2, numConnections)
                assertEquals(2, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept3.id).contains(connection13))
            }

        val gb = runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConcept(concept3)
                addConnection(connection12)
                addConnection(connection13)

                assertThrows<IllegalArgumentException> {
                    addConnection(connection12)
                }
                assertThrows<IllegalArgumentException> {
                    addConnection(connection13)
                }
            }
        }

        runWriteCheckTest(gb, ::check) { g ->
            with(g) {
                assertThrows<IllegalArgumentException> {
                    addConnection(connection12)
                }
                assertThrows<IllegalArgumentException> {
                    addConnection(connection13)
                }
            }
        }

    }

    @Test
    fun `A graph can have directed connections added`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val concept4 = SampleConcept()
        val connection12 = SampleDirectedConnection(concept1.id, concept2.id)
        val connection13 = SampleDirectedConnection(concept1.id, concept3.id)
        val connection14 = SampleDirectedConnection(concept1.id, concept4.id)
        val connection23 = SampleDirectedConnection(concept2.id, concept3.id)
        val connection24 = SampleDirectedConnection(concept2.id, concept4.id)
        val connection34 = SampleDirectedConnection(concept3.id, concept4.id)
        val connection21 = SampleDirectedConnection(concept2.id, concept1.id)
        val connection31 = SampleDirectedConnection(concept3.id, concept1.id)
        val connection41 = SampleDirectedConnection(concept4.id, concept1.id)
        val connection32 = SampleDirectedConnection(concept3.id, concept2.id)
        val connection42 = SampleDirectedConnection(concept4.id, concept2.id)
        val connection43 = SampleDirectedConnection(concept4.id, concept3.id)

        fun check2(g: IGraph) =
            with(g) {
                assertEquals(4, numConcepts)
                assertEquals(2, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertEquals(1, connections(concept4.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection34))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept3.id).contains(connection34))
                assertTrue(connections(concept4.id).contains(connection34))
                assertTrue(connectionsFrom(concept1.id).contains(connection12))
                assertFalse(connectionsFrom(concept2.id).contains(connection12))
                assertTrue(connectionsFrom(concept3.id).contains(connection34))
                assertFalse(connectionsFrom(concept4.id).contains(connection34))
                assertFalse(connectionsTo(concept1.id).contains(connection12))
                assertTrue(connectionsTo(concept2.id).contains(connection12))
                assertFalse(connectionsTo(concept3.id).contains(connection34))
                assertTrue(connectionsTo(concept4.id).contains(connection34))
            }

        val gb = runWriteCheckTest(::check2) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConcept(concept3)
                addConcept(concept4)
                addConnection(connection12)
                addConnection(connection34)
            }
        }

        fun check12(g: IGraph) =
            with(g) {
                assertEquals(4, numConcepts)
                assertEquals(12, numConnections)
                assertEquals(6, connections(concept1.id).size)
                assertEquals(6, connections(concept2.id).size)
                assertEquals(6, connections(concept3.id).size)
                assertEquals(6, connections(concept4.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertTrue(connections(concept1.id).contains(connection14))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection23))
                assertTrue(connections(concept2.id).contains(connection24))
                assertTrue(connections(concept3.id).contains(connection13))
                assertTrue(connections(concept3.id).contains(connection23))
                assertTrue(connections(concept3.id).contains(connection34))
                assertTrue(connections(concept4.id).contains(connection14))
                assertTrue(connections(concept4.id).contains(connection24))
                assertTrue(connections(concept4.id).contains(connection34))
                assertTrue(connections(concept1.id).contains(connection21))
                assertTrue(connections(concept1.id).contains(connection31))
                assertTrue(connections(concept1.id).contains(connection41))
                assertTrue(connections(concept2.id).contains(connection21))
                assertTrue(connections(concept2.id).contains(connection32))
                assertTrue(connections(concept2.id).contains(connection42))
                assertTrue(connections(concept3.id).contains(connection31))
                assertTrue(connections(concept3.id).contains(connection32))
                assertTrue(connections(concept3.id).contains(connection43))
                assertTrue(connections(concept4.id).contains(connection41))
                assertTrue(connections(concept4.id).contains(connection42))
                assertTrue(connections(concept4.id).contains(connection43))
                assertTrue(connectionsFrom(concept1.id).contains(connection12))
                assertTrue(connectionsFrom(concept1.id).contains(connection13))
                assertTrue(connectionsFrom(concept1.id).contains(connection14))
                assertFalse(connectionsFrom(concept2.id).contains(connection12))
                assertTrue(connectionsFrom(concept2.id).contains(connection23))
                assertTrue(connectionsFrom(concept2.id).contains(connection24))
                assertFalse(connectionsFrom(concept3.id).contains(connection13))
                assertFalse(connectionsFrom(concept3.id).contains(connection23))
                assertTrue(connectionsFrom(concept3.id).contains(connection34))
                assertFalse(connectionsFrom(concept4.id).contains(connection14))
                assertFalse(connectionsFrom(concept4.id).contains(connection24))
                assertFalse(connectionsFrom(concept4.id).contains(connection34))
                assertFalse(connectionsFrom(concept1.id).contains(connection21))
                assertFalse(connectionsFrom(concept1.id).contains(connection31))
                assertFalse(connectionsFrom(concept1.id).contains(connection41))
                assertTrue(connectionsFrom(concept2.id).contains(connection21))
                assertFalse(connectionsFrom(concept2.id).contains(connection32))
                assertFalse(connectionsFrom(concept2.id).contains(connection42))
                assertTrue(connectionsFrom(concept3.id).contains(connection31))
                assertTrue(connectionsFrom(concept3.id).contains(connection32))
                assertFalse(connectionsFrom(concept3.id).contains(connection43))
                assertTrue(connectionsFrom(concept4.id).contains(connection41))
                assertTrue(connectionsFrom(concept4.id).contains(connection42))
                assertTrue(connectionsFrom(concept4.id).contains(connection43))
                assertFalse(connectionsTo(concept1.id).contains(connection12))
                assertFalse(connectionsTo(concept1.id).contains(connection13))
                assertFalse(connectionsTo(concept1.id).contains(connection14))
                assertTrue(connectionsTo(concept2.id).contains(connection12))
                assertFalse(connectionsTo(concept2.id).contains(connection23))
                assertFalse(connectionsTo(concept2.id).contains(connection24))
                assertTrue(connectionsTo(concept3.id).contains(connection13))
                assertTrue(connectionsTo(concept3.id).contains(connection23))
                assertFalse(connectionsTo(concept3.id).contains(connection34))
                assertTrue(connectionsTo(concept4.id).contains(connection14))
                assertTrue(connectionsTo(concept4.id).contains(connection24))
                assertTrue(connectionsTo(concept4.id).contains(connection34))
                assertTrue(connectionsTo(concept1.id).contains(connection21))
                assertTrue(connectionsTo(concept1.id).contains(connection31))
                assertTrue(connectionsTo(concept1.id).contains(connection41))
                assertFalse(connectionsTo(concept2.id).contains(connection21))
                assertTrue(connectionsTo(concept2.id).contains(connection32))
                assertTrue(connectionsTo(concept2.id).contains(connection42))
                assertFalse(connectionsTo(concept3.id).contains(connection31))
                assertFalse(connectionsTo(concept3.id).contains(connection32))
                assertTrue(connectionsTo(concept3.id).contains(connection43))
                assertFalse(connectionsTo(concept4.id).contains(connection41))
                assertFalse(connectionsTo(concept4.id).contains(connection42))
                assertFalse(connectionsTo(concept4.id).contains(connection43))
            }

        runWriteCheckTest(gb, ::check12) { g ->
            with(g) {
                addConnection(connection13)
                addConnection(connection14)
                addConnection(connection23)
                addConnection(connection24)
                addConnection(connection21)
                addConnection(connection43)
                addConnection(connection31)
                addConnection(connection41)
                addConnection(connection32)
                addConnection(connection42)
            }
        }

    }

    @Test
    fun `Directed connections cannot be added twice`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connection12 = SampleDirectedConnection(concept1.id, concept2.id)
        val connection21 = SampleDirectedConnection(concept2.id, concept1.id)

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(2, numConnections)
                assertEquals(2, connections(concept1.id).size)
                assertEquals(2, connections(concept2.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection21))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection21))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection21))
            }

        val gb = runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConnection(connection12)
                addConnection(connection21)

                assertThrows<IllegalArgumentException> {
                    addConnection(connection12)
                }
                assertThrows<IllegalArgumentException> {
                    addConnection(connection21)
                }
            }
        }

        runWriteCheckTest(gb, ::check) { g ->
            with(g) {
                assertThrows<IllegalArgumentException> {
                    addConnection(connection12)
                }
                assertThrows<IllegalArgumentException> {
                    addConnection(connection21)
                }
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

