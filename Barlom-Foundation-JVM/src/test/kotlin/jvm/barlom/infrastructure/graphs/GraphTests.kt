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

    private fun runWriteCheckTest(check: (IGraph) -> Unit, write: (IWritableGraph) -> Unit) =
        runWriteCheckTest(graphOf(), check, write)

    private fun runWriteCheckTest(
        initialGraph: IGraph,
        check: (IGraph) -> Unit,
        write: (IWritableGraph) -> Unit
    ): IGraph {

        val g0 = initialGraph.clone()
        val g1 = g0.startWriting()

        write(g1)

        assertTrue(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertTrue(g1.isWritable)
        assertTrue(g1.hasPredecessor)

        check(g1)

        val g2 = g1.stopWriting()

        assertTrue(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertFalse(g1.isWritable)
        assertTrue(g1.hasPredecessor)
        assertTrue(g2.isReadable)
        assertTrue(g2.hasPredecessor)

        check(g1)
        check(g2)

        g0.stopReading()

        assertFalse(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertFalse(g1.isWritable)
        assertFalse(g1.hasPredecessor)
        assertTrue(g2.isReadable)
        assertFalse(g2.hasPredecessor)

        check(g1)
        check(g2)

        ////

        val gA = initialGraph.clone()
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

        val gC = gB.stopWriting()

        assertFalse(gA.isReadable)
        assertFalse(gA.hasPredecessor)
        assertTrue(gB.isReadable)
        assertFalse(gB.isWritable)
        assertFalse(gB.hasPredecessor)
        assertTrue(gC.isReadable)
        assertFalse(gC.hasPredecessor)

        assertFalse(g0.isReadable)
        assertFalse(g0.hasPredecessor)
        assertTrue(g1.isReadable)
        assertFalse(g1.isWritable)
        assertFalse(g1.hasPredecessor)
        assertTrue(g2.isReadable)
        assertFalse(g2.hasPredecessor)

        check(g1)
        check(g2)

        check(gB)
        check(gC)

        return gC

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
    fun `A graph can have directed connections added and removed`() {

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

data class SampleConcept(

    override val id: Id<SampleConcept> = Id(makeUuid())

) : IConcept<SampleConcept> {

    override val conceptTypeName: String = "SampleConcept"

}

//---------------------------------------------------------------------------------------------------------------------

data class SampleUndirectedConnection(
    override val conceptIdA: Id<SampleConcept>,
    override val conceptIdB: Id<SampleConcept>,
    override val id: Id<SampleUndirectedConnection> = Id(makeUuid())
) : IUndirectedConnection<SampleUndirectedConnection, SampleConcept> {

    override val connectionTypeName: String = "SampleUndirectedConnection"

}

//---------------------------------------------------------------------------------------------------------------------

data class SampleDirectedConnection(
    override val fromConceptId: Id<SampleConcept>,
    override val toConceptId: Id<SampleConcept>,
    override val id: Id<SampleDirectedConnection> = Id(makeUuid())
) : IDirectedConnection<SampleDirectedConnection, SampleConcept, SampleConcept> {

    override val connectionTypeName: String = "SampleDirectedConnection"

}

//---------------------------------------------------------------------------------------------------------------------

