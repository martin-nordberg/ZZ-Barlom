//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IGraph
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of classes ReadableGraph and WritableGraph for updating concepts and connections.
 */
@Suppress("RemoveRedundantBackticks")
class GraphUpdateTests
    : GraphTests() {

    @Test
    fun `A graph can have concepts updated`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept2a = concept2.copy()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertTrue(containsConcept(concept2a))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2a, concept(concept2.id))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                updateConcept(concept2a)
            }
        }

    }

    @Test
    fun `A graph can have concepts updated repeatedly`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept2a = concept2.copy()
        val concept2b = concept2a.copy()
        val concept2c = concept2b.copy()

        fun checkB(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertFalse(containsConcept(concept2a))
                assertTrue(containsConcept(concept2b))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2b, concept(concept2.id))
            }

        val gb = runWriteCheckTest(::checkB) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                updateConcept(concept2a)
                updateConcept(concept2b)
            }
        }

        fun checkC(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(concept1))
                assertFalse(containsConcept(concept2))
                assertFalse(containsConcept(concept2a))
                assertFalse(containsConcept(concept2b))
                assertTrue(containsConcept(concept2c))
                assertEquals(concept1, concept(concept1.id))
                assertEquals(concept2c, concept(concept2.id))
            }

        runWriteCheckTest(gb, ::checkC) { g ->
            with(g) {
                updateConcept(concept2c)
            }
        }

        checkB(gb)

    }

    @Test
    fun `A graph can have undirected connections updated`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connectionA = SampleUndirectedConnection(concept1.id, concept2.id)
        val connectionB = connectionA.copy()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(1, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertFalse(containsConnection(connectionA))
                assertTrue(containsConnection(connectionB))
                assertTrue(connections(concept1.id).contains(connectionB))
                assertTrue(connections(concept2.id).contains(connectionB))
                assertFalse(connections(concept1.id).contains(connectionA))
                assertFalse(connections(concept2.id).contains(connectionA))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConnection(connectionA)
                updateConnection(connectionB)
            }
        }

    }

    @Test
    fun `A graph can have directed connections updated`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connectionA = SampleDirectedConnection(concept1.id, concept2.id)
        val connectionB = connectionA.copy()

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(1, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertFalse(containsConnection(connectionA))
                assertTrue(containsConnection(connectionB))
                assertTrue(connections(concept1.id).contains(connectionB))
                assertTrue(connections(concept2.id).contains(connectionB))
                assertFalse(connections(concept1.id).contains(connectionA))
                assertFalse(connections(concept2.id).contains(connectionA))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConnection(connectionA)
                updateConnection(connectionB)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

