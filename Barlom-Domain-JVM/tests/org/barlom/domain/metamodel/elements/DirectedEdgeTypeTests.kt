//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.domain.metamodel.impl.vertices.DirectedEdgeType
import org.barlom.domain.metamodel.impl.vertices.Package
import org.barlom.domain.metamodel.impl.vertices.RootPackage
import org.barlom.domain.metamodel.impl.vertices.VertexType
import org.barlom.domain.metamodel.withRevHistory
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of DirectedEdgeType.
 */
class DirectedEdgeTypeTests {

    @Test
    fun `Directed edge types construct as expected`() {

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy(root) }
            val vt1 = VertexType(makeUuid(), "vt1", EAbstractness.CONCRETE, root.rootVertexType) { containedBy(pkg) }
            val vt2 = VertexType(makeUuid(), "vt2", EAbstractness.CONCRETE, vt1) { containedBy(pkg) }
            val etId = makeUuid()
            val et = DirectedEdgeType(etId, "et", EAbstractness.CONCRETE,
                                      ECyclicity.ACYCLIC,
                                      EMultiEdgedness.UNCONSTRAINED,
                                      ESelfLooping.SELF_LOOPS_NOT_ALLOWED,
                                      "et", "head", vt1,
                                      11, 9, 7, 5,
                                      "te", root.rootDirectedEdgeType, "tail",
                                      vt2) { containedBy(pkg) }

            assertEquals(etId, et.id)
            assertEquals("et", et.name)
            assertEquals(pkg, et.parentPackages[0])
            assertEquals(EAbstractness.CONCRETE, et.abstractness)
            assertEquals(ECyclicity.ACYCLIC, et.cyclicity)
            assertEquals(EMultiEdgedness.UNCONSTRAINED, et.multiEdgedness)
            assertEquals(ESelfLooping.SELF_LOOPS_NOT_ALLOWED, et.selfLooping)
            assertEquals("head", et.headRoleName)
            assertEquals(vt1, et.headVertexType)
            assertEquals(11, et.maxHeadInDegree)
            assertEquals(9, et.maxTailOutDegree)
            assertEquals(7, et.minHeadInDegree)
            assertEquals(5, et.minTailOutDegree)
            assertEquals("te", et.reverseName)
            assertEquals(root.rootDirectedEdgeType, et.superType)
            assertEquals("tail", et.tailRoleName)
            assertEquals(vt2, et.tailVertexType)

            assertEquals("pkg.et", et.path)
        }

    }

    @Test
    fun `Directed edge types track their supertype and subtypes`() {

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy(root) }
            val vt0 = root.rootVertexType
            val vt1 = VertexType(makeUuid(), "vt1", EAbstractness.ABSTRACT, vt0) { containedBy(pkg) }
            val vt2 = VertexType(makeUuid(), "vt2", EAbstractness.ABSTRACT, vt1) { containedBy(pkg) }

            val a = EAbstractness.ABSTRACT
            val c = ECyclicity.ACYCLIC
            val m = EMultiEdgedness.UNCONSTRAINED
            val s = ESelfLooping.SELF_LOOPS_NOT_ALLOWED

            val et0 = root.rootDirectedEdgeType
            val et1 = DirectedEdgeType(makeUuid(), "et1", a, c, m, s,
                                       null, null, vt1, null, null, null,
                                       null, "te0", et0, null,
                                       vt2) { containedBy(pkg) }
            val et2 = DirectedEdgeType(makeUuid(), "et2", a, c, m, s,
                                       null, null, vt1, null, null, null,
                                       null, "te1", et1, null,
                                       vt2) { containedBy(pkg) }
            val et3 = DirectedEdgeType(makeUuid(), "et3", a, c, m, s,
                                       null, null, vt1, null, null, null,
                                       null, "te2", et2, null,
                                       vt2) { containedBy(pkg) }
            val et4 = DirectedEdgeType(makeUuid(), "et4", a, c, m, s,
                                       null, null, vt1, null, null, null,
                                       null, "te3", et3, null,
                                       vt2) { containedBy(pkg) }

            assertEquals(et0, et1.superType)
            assertEquals(et1, et2.superType)
            assertEquals(et2, et3.superType)
            assertEquals(et3, et4.superType)

            assertTrue(et0.subTypes.contains(et1))
            assertFalse(et0.subTypes.contains(et2))
            assertTrue(et1.subTypes.contains(et2))
            assertFalse(et1.subTypes.contains(et3))
            assertTrue(et2.subTypes.contains(et3))
            assertTrue(et3.subTypes.contains(et4))
            assertTrue(et4.subTypes.isEmpty())

            assertFalse(et0.isSubTypeOf(et0))
            assertFalse(et1.isSubTypeOf(et1))
            assertFalse(et2.isSubTypeOf(et2))
            assertFalse(et3.isSubTypeOf(et3))

            assertTrue(et1.isSubTypeOf(et0))
            assertFalse(et0.isSubTypeOf(et1))

            assertTrue(et2.isSubTypeOf(et0))
            assertTrue(et2.isSubTypeOf(et1))
            assertFalse(et1.isSubTypeOf(et2))

            assertTrue(et3.isSubTypeOf(et0))
            assertTrue(et3.isSubTypeOf(et1))
            assertTrue(et3.isSubTypeOf(et2))

            assertTrue(et4.isSubTypeOf(et0))
            assertTrue(et4.isSubTypeOf(et1))
            assertTrue(et4.isSubTypeOf(et2))
            assertTrue(et4.isSubTypeOf(et3))

            assertTrue(et0.transitiveSubTypes.contains(et1))
            assertTrue(et0.transitiveSubTypes.contains(et2))
            assertTrue(et0.transitiveSubTypes.contains(et3))
            assertTrue(et0.transitiveSubTypes.contains(et4))

            assertTrue(et1.transitiveSubTypes.contains(et2))
            assertTrue(et1.transitiveSubTypes.contains(et3))
            assertTrue(et1.transitiveSubTypes.contains(et4))

            assertTrue(et2.transitiveSubTypes.contains(et3))
            assertTrue(et2.transitiveSubTypes.contains(et4))

            assertTrue(et3.transitiveSubTypes.contains(et4))
        }
    }


}
