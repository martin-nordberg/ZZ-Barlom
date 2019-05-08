//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import org.junit.jupiter.api.Test
import x.barlom.infrastructure.uuids.makeUuid
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of classes Graph and WritableGraph.
 */
@Suppress("RemoveRedundantBackticks")
class GraphTests {

    private fun runWriteCheckTest(
        check: (IGraph) -> Unit,
        write: (IWritableGraph) -> Unit
    ) {

        val g0 = graphOf()
        val g1 = g0.startWriting()

        write(g1)

        assertTrue(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertTrue(g1.isWritable)
        assertTrue(g1.hasPredecessor)

        check(g1)

        g1.stopWriting()

        assertTrue(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertFalse(g1.isWritable)
        assertTrue(g1.hasPredecessor)

        check(g1)

        g0.stopReading()

        assertFalse(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertFalse(g1.isWritable)
        assertFalse(g1.hasPredecessor)

        check(g1)

        ////

        val gA = graphOf()
        val gB = gA.startWriting()

        write(gB)

        assertTrue(gA.isReadable)
        assertFalse(gA.hasPredecessor)
        assertTrue(gB.isReadable)
        assertTrue(gB.isWritable)
        assertTrue(gB.hasPredecessor)

        check(gB)

        gA.stopReading()

        assertFalse(gA.isReadable)
        assertFalse(gA.hasPredecessor)
        assertTrue(gB.isReadable)
        assertTrue(gB.isWritable)
        assertTrue(gB.hasPredecessor)

        check(gB)

        gB.stopWriting()

        assertFalse(gA.isReadable)
        assertFalse(gA.hasPredecessor)
        assertTrue(gB.isReadable)
        assertFalse(gB.isWritable)
        assertFalse(gB.hasPredecessor)

        check(gB)

    }

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

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
            }
        }

    }

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

        fun check(g: IGraph) =
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

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                updateConcept(concept2a)
                updateConcept(concept2b)
                updateConcept(concept2c)
            }
        }

    }

    @Test
    fun `A graph can have concepts removed`() {

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
    fun `A graph can have undirected connections added`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connection = SampleUndirectedConnection(concept1.id, concept2.id)

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(1, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertTrue(containsConnection(connection))
                assertTrue(connections(concept1.id).contains(connection))
                assertTrue(connections(concept2.id).contains(connection))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConnection(connection)
            }
        }

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
    fun `A graph can have directed connections added`() {

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connection = SampleDirectedConnection(concept1.id, concept2.id)

        fun check(g: IGraph) =
            with(g) {
                assertEquals(2, numConcepts)
                assertEquals(1, numConnections)
                assertEquals(1, connections(concept1.id).size)
                assertEquals(1, connections(concept2.id).size)
                assertTrue(containsConnection(connection))
                assertTrue(connections(concept1.id).contains(connection))
                assertTrue(connections(concept2.id).contains(connection))
            }

        runWriteCheckTest(::check) { g ->
            with(g) {
                addConcept(concept1)
                addConcept(concept2)
                addConnection(connection)
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

data class SampleConcept (

    override val id: Id<SampleConcept> = Id(makeUuid())

) : IConcept<SampleConcept>

//---------------------------------------------------------------------------------------------------------------------

data class SampleUndirectedConnection(
    override val conceptIdA: Id<SampleConcept>,
    override val conceptIdB: Id<SampleConcept>,
    override val id: Id<SampleUndirectedConnection> = Id(makeUuid())
) : IUndirectedConnection<SampleUndirectedConnection, SampleConcept>

//---------------------------------------------------------------------------------------------------------------------

data class SampleDirectedConnection(
    override val fromConceptId: Id<SampleConcept>,
    override val toConceptId: Id<SampleConcept>,
    override val id: Id<SampleDirectedConnection> = Id(makeUuid())
) : IDirectedConnection<SampleDirectedConnection, SampleConcept, SampleConcept>

//---------------------------------------------------------------------------------------------------------------------

