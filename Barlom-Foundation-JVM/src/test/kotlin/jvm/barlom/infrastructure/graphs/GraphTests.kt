//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.*
import x.barlom.infrastructure.uuids.Uuid
import x.barlom.infrastructure.uuids.makeUuid
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of classes Graph and WritableGraph.
 */
@Suppress("RemoveRedundantBackticks")
internal abstract class GraphTests {

    protected fun runWriteCheckTest(check: (IGraph) -> Unit, write: (IWritableGraph) -> Unit) =
        runWriteCheckTest(graphOf(), check, write)

    protected fun runWriteCheckTest(
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

}

//---------------------------------------------------------------------------------------------------------------------

data class SampleConcept(

    override val uuid: Uuid = makeUuid()

) : IConcept<SampleConcept> {
    override val typeId = ConceptTypeId<SampleConcept>("SampleConcept")
}

//---------------------------------------------------------------------------------------------------------------------

data class SampleUndirectedConnection(
    override val conceptIdA: Id<SampleConcept>,
    override val conceptIdB: Id<SampleConcept>,
    override val uuid: Uuid = makeUuid()
) : IUndirectedConnection<SampleUndirectedConnection, SampleConcept> {
    override val typeId = ConnectionTypeId<SampleUndirectedConnection>("SampleUndirectedConnection")
}

//---------------------------------------------------------------------------------------------------------------------

data class SampleDirectedConnection(
    override val fromConceptId: Id<SampleConcept>,
    override val toConceptId: Id<SampleConcept>,
    override val uuid: Uuid = makeUuid()
) : IDirectedConnection<SampleDirectedConnection, SampleConcept, SampleConcept> {
    override val typeId = ConnectionTypeId<SampleDirectedConnection>("SampleDirectedConnection")
}

//---------------------------------------------------------------------------------------------------------------------

