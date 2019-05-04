//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.graphs

import i.barlom.infrastructure.graphs.graphOf
import o.barlom.infrastructure.graphs.IConcept
import o.barlom.infrastructure.graphs.IDirectedConnection
import o.barlom.infrastructure.graphs.IUndirectedConnection
import o.barlom.infrastructure.graphs.Id
import org.junit.jupiter.api.Test
import x.barlom.infrastructure.uuids.makeUuid
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of classes Graph and WriteableGraph.
 */
@Suppress("RemoveRedundantBackticks")
class GraphTests {

    @Test
    fun `A graph starts out empty`() {

        val g = graphOf()

        with(g) {
            assertEquals(0, numConcepts)
            assertEquals(0, numConnections)
            assertTrue(isEmpty())
            assertFalse(isNotEmpty())
        }

        with( g.beginTransaction()) {
            assertEquals(0, numConcepts)
            assertEquals(0, numConnections)
            assertTrue(isEmpty())
            assertFalse(isNotEmpty())
        }

    }

    @Test
    fun `A graph can have concepts added`() {

        val g = graphOf().beginTransaction()

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()

        with(g) {
            addConcept(concept1)
            addConcept(concept2)

            assertEquals(2, numConcepts)
            assertEquals(0, numConnections)
            assertFalse(isEmpty())
            assertTrue(isNotEmpty())
            assertTrue(containsConcept(concept1))
            assertTrue(containsConcept(concept2))
            assertEquals(concept1, concept(concept1.id))
            assertEquals(concept2, concept(concept2.id))
        }

        with( g.commit()) {
            assertEquals(2, numConcepts)
            assertEquals(0, numConnections)
            assertFalse(isEmpty())
            assertTrue(isNotEmpty())
            assertTrue(containsConcept(concept1))
            assertTrue(containsConcept(concept2))
            assertEquals(concept1, concept(concept1.id))
            assertEquals(concept2, concept(concept2.id))
        }

    }

    @Test
    fun `A graph can have concepts updated`() {

        val g = graphOf().beginTransaction()

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val concept2a = concept2.copy()

        with(g) {
            addConcept(concept1)
            addConcept(concept2)
            updateConcept(concept2a)

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

        with(g.commit()) {
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

    }

    @Test
    fun `A graph can have concepts removed`() {

        val g = graphOf().beginTransaction()

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()

        with(g) {
            addConcept(concept1)
            addConcept(concept2)
            removeConcept(concept2)

            assertEquals(1, numConcepts)
            assertEquals(0, numConnections)
            assertFalse(isEmpty())
            assertTrue(isNotEmpty())
            assertTrue(containsConcept(concept1))
            assertFalse(containsConcept(concept2))
            assertEquals(concept1, concept(concept1.id))
            assertEquals(null, concept(concept2.id))
        }

        with(g.commit()) {
            assertEquals(1, numConcepts)
            assertEquals(0, numConnections)
            assertFalse(isEmpty())
            assertTrue(isNotEmpty())
            assertTrue(containsConcept(concept1))
            assertFalse(containsConcept(concept2))
            assertEquals(concept1, concept(concept1.id))
            assertEquals(null, concept(concept2.id))
        }

    }

    @Test
    fun `A graph can have undirected connections added`() {

        val g = graphOf().beginTransaction()

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connection = SampleUndirectedConnection(concept1.id,concept2.id)

        with(g) {
            addConcept(concept1)
            addConcept(concept2)
            addConnection(connection)

            assertEquals(2, numConcepts)
            assertEquals(1, numConnections)
            assertEquals(1, connections(concept1.id).size)
            assertEquals(1, connections(concept2.id).size)
            assertTrue(containsConnection(connection))
            assertTrue(connections(concept1.id).contains(connection))
            assertTrue(connections(concept2.id).contains(connection))
        }

        with(g.commit()) {
            assertEquals(2, numConcepts)
            assertEquals(1, numConnections)
            assertEquals(1, connections(concept1.id).size)
            assertEquals(1, connections(concept2.id).size)
            assertTrue(containsConnection(connection))
            assertTrue(connections(concept1.id).contains(connection))
            assertTrue(connections(concept2.id).contains(connection))
        }

    }

    @Test
    fun `A graph can have undirected connections updated`() {

        val g = graphOf().beginTransaction()

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connectionA = SampleUndirectedConnection(concept1.id,concept2.id)
        val connectionB = connectionA.copy()

        with(g) {
            addConcept(concept1)
            addConcept(concept2)
            addConnection(connectionA)
            updateConnection(connectionB)

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

        with(g.commit()) {
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

    }

    @Test
    fun `A graph can have directed connections added`() {

        val g = graphOf().beginTransaction()

        val concept1 = SampleConcept()
        val concept2 = SampleConcept()
        val connection = SampleDirectedConnection(concept1.id,concept2.id)

        with(g) {
            addConcept(concept1)
            addConcept(concept2)
            addConnection(connection)

            assertEquals(2, numConcepts)
            assertEquals(1, numConnections)
            assertEquals(1, connections(concept1.id).size)
            assertEquals(1, connections(concept2.id).size)
            assertTrue(containsConnection(connection))
            assertTrue(connections(concept1.id).contains(connection))
            assertTrue(connections(concept2.id).contains(connection))
        }

        with(g.commit()) {
            assertEquals(2, numConcepts)
            assertEquals(1, numConnections)
            assertEquals(1, connections(concept1.id).size)
            assertEquals(1, connections(concept2.id).size)
            assertTrue(containsConnection(connection))
            assertTrue(connections(concept1.id).contains(connection))
            assertTrue(connections(concept2.id).contains(connection))
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

