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
 * Tests of classes ReadableGraph and WritableGraph for removing concepts and connections.
 */
@Suppress("RemoveRedundantBackticks")
class GraphRemovalTests
    : GraphTests() {

    @Test
    fun `A graph can have concepts removed immediately`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(1, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(null, concept(concept2.id))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                removeConcept(concept2)
            }
        }

    }

    @Test
    fun `A concept cannot be removed twice`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(1, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(null, concept(concept2.id))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                removeConcept(concept2)

                assertThrows<IllegalArgumentException> {
                    removeConcept(concept2)
                }
            }
        }

    }

    @Test
    fun `A graph can have concepts with connections removed later`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val connection12 = SampleUndirectedConnection(concept1.id, concept2.id)
        val connection13 = SampleUndirectedConnection(concept1.id, concept3.id)
        val connection23 = SampleUndirectedConnection(concept2.id, concept3.id)

        fun checkAdded(g: IGraph) =
            with(g) {
                assertEquals(3, numConcepts)
                assertEquals(3, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertTrue(containsConcept(concept2))
                assertTrue(containsConcept(concept3))
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertTrue(containsConnection(connection23))
                assertEquals(2, connections(concept1.id).size)
                assertEquals(2, connections(concept2.id).size)
                assertEquals(2, connections(concept2.id).size)
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection23))
                assertTrue(connections(concept3.id).contains(connection13))
                assertTrue(connections(concept3.id).contains(connection23))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2, concept(concept2.id))
                assertEquals(concept3, concept(concept3.id))
                assertEquals(connection12, connection(connection12.id))
                assertEquals(connection13, connection(connection13.id))
                assertEquals(connection23, connection(connection23.id))
            }

        val gb = runWriteCheckTest(::checkAdded) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConcept(concept3)
                addConnection(connection12)
                addConnection(connection13)
                addConnection(connection23)
            }
        }

        fun checkRemoved(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(1, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertTrue(containsConcept(concept3))
                assertFalse(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertFalse(containsConnection(connection23))
                assertEquals(1, connections(concept1.id).size)
                assertEquals(0, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertFalse(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertFalse(connections(concept2.id).contains(connection12))
                assertFalse(connections(concept2.id).contains(connection23))
                assertTrue(connections(concept3.id).contains(connection13))
                assertFalse(connections(concept3.id).contains(connection23))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(null, concept(concept2.id))
                assertEquals(concept3, concept(concept3.id))
                assertEquals(null, connection(connection12.id))
                assertEquals(connection13, connection(connection13.id))
                assertEquals(null, connection(connection23.id))
            }

        runWriteCheckTest(gb, ::checkRemoved) { g ->
            with(g) {
                removeConcept(concept2)
            }
        }

    }

    @Test
    fun `A graph can have concepts removed then re-added`() {

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
                removeConcept(concept2)
                addConcept(concept2)
            }
        }

        runWriteCheckTest(gb, ::check) { g ->
            with(g) {
                removeConcept(concept2)
                addConcept(concept2)
            }
        }

    }

    @Test
    fun `A graph can have concepts updated and removed`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept2a = concept2.copy()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(1, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(null, concept(concept2.id))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                updateConcept(concept2a)
                removeConcept(concept2a)
            }
        }

    }

    @Test
    fun `A graph can have directed connections added and removed once`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val connection12 = SampleDirectedConnection(concept1.id, concept2.id)
        val connection13 = SampleDirectedConnection(concept1.id, concept3.id)

        fun checkAdded(g: IGraph) =
            with(g) {
                assertEquals(3, numConcepts)
                assertEquals(2, numConnections)
                assertEquals(2, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertTrue(connections(concept3.id).contains(connection13))
            }

        val gb = runWriteCheckTest(::checkAdded) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConcept(concept3)
                addConnection(connection12)
                addConnection(connection13)
            }
        }

        fun checkRemoved(g: IGraph) =
            with(g) {
                assertEquals(3, numConcepts)
                assertEquals(1, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(0, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertFalse(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertFalse(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertFalse(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept3.id).contains(connection13))
            }

        runWriteCheckTest(gb, ::checkRemoved) { g ->
            with(g) {
                removeConnection(connection12.id)

                assertThrows<IllegalArgumentException> {
                    removeConnection(connection12.id)
                }
            }
        }

    }

    @Test
    fun `A graph can have undirected connections added and removed once`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept3 = SampleConcept()
        val connection12 = SampleUndirectedConnection(concept1.id, concept2.id)
        val connection13 = SampleUndirectedConnection(concept1.id, concept3.id)

        fun checkAdded(g: IGraph) =
            with(g) {
                assertEquals(3, numConcepts)
                assertEquals(2, numConnections)
                assertEquals(2, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertTrue(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertTrue(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertTrue(connections(concept3.id).contains(connection13))
            }

        val gb = runWriteCheckTest(::checkAdded) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConcept(concept3)
                addConnection(connection12)
                addConnection(connection13)
            }
        }

        fun checkRemoved(g: IGraph) =
            with(g) {
                assertEquals(3, numConcepts)
                assertEquals(1, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(0, connections(concept2.id).size)
                assertEquals(1, connections(concept3.id).size)
                assertFalse(containsConnection(connection12))
                assertTrue(containsConnection(connection13))
                assertFalse(connections(concept1.id).contains(connection12))
                assertTrue(connections(concept1.id).contains(connection13))
                assertFalse(connections(concept2.id).contains(connection12))
                assertTrue(connections(concept3.id).contains(connection13))
            }

        runWriteCheckTest(gb, ::checkRemoved) { g ->
            with(g) {
                removeConnection(connection12.id)

                assertThrows<IllegalArgumentException> {
                    removeConnection(connection12.id)
                }
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

