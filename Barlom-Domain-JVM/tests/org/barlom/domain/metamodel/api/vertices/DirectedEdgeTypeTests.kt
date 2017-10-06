//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
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

        val model = Model({ makeUuid() })

        model.revHistory.update {
            val root = model.rootPackage
            val pkg = model.makePackage() {
                name = "pkg"
            }
            model.makePackageContainment(root, pkg)
            val vt1 = model.makeVertexType()
            model.makeVertexTypeContainment(pkg, vt1)
            val vt2 = model.makeVertexType()
            model.makeVertexTypeContainment(pkg, vt2)
            val etId = makeUuid()
            val et = model.makeDirectedEdgeType(etId) {
                name = "et"
                abstractness = EAbstractness.CONCRETE
                cyclicity = ECyclicity.ACYCLIC
                multiEdgedness = EMultiEdgedness.UNCONSTRAINED
                selfLooping = ESelfLooping.SELF_LOOPS_NOT_ALLOWED
                forwardName = "fet"
                headRoleName = "head"
                maxHeadInDegree = 11
                maxTailOutDegree = 9
                minHeadInDegree = 7
                minTailOutDegree = 5
                reverseName = "ret"
                tailRoleName = "tail"
            }
            model.makeDirectedEdgeTypeContainment(pkg, et)
            model.makeDirectedEdgeTypeHeadConnectivity(et, vt1)
            model.makeDirectedEdgeTypeTailConnectivity(et, vt2)
            model.makeDirectedEdgeTypeInheritance(model.rootDirectedEdgeType, et)


            assertEquals(etId, et.id)
            assertEquals("et", et.name)
            assertEquals(pkg, et.parents[0])
            assertEquals(et, pkg.directedEdgeTypes[0])
            assertEquals(EAbstractness.CONCRETE, et.abstractness)
            assertEquals(ECyclicity.ACYCLIC, et.cyclicity)
            assertEquals(EMultiEdgedness.UNCONSTRAINED, et.multiEdgedness)
            assertEquals(ESelfLooping.SELF_LOOPS_NOT_ALLOWED, et.selfLooping)
            assertEquals("head", et.headRoleName)
            assertEquals(vt1, et.connectedHeadVertexTypes[0])
            assertEquals(vt2, et.connectedTailVertexTypes[0])
            assertEquals(et, vt1.connectingHeadEdgeTypes[0])
            assertEquals(et, vt2.connectingTailEdgeTypes[0])
            assertEquals(11, et.maxHeadInDegree)
            assertEquals(9, et.maxTailOutDegree)
            assertEquals(7, et.minHeadInDegree)
            assertEquals(5, et.minTailOutDegree)
            assertEquals("fet", et.forwardName)
            assertEquals("ret", et.reverseName)
            assertEquals(model.rootDirectedEdgeType, et.superTypes[0])
            assertEquals(et, model.rootDirectedEdgeType.subTypes[0])
            assertEquals("tail", et.tailRoleName)

            assertEquals("pkg.et", et.path)
            "test"
        }

    }

    @Test
    fun `Directed edge types track their supertype and subtypes`() {

        val model = Model({ makeUuid() })

        model.revHistory.update {
            val root = model.rootPackage
            val pkg = model.makePackage() {
                name = "pkg"
            }
            model.makePackageContainment(root, pkg)
            val vt0 = model.rootVertexType
            val vt1 = model.makeVertexType {
                name = "vt1"
                abstractness = EAbstractness.ABSTRACT
            }
            model.makeVertexTypeContainment(pkg, vt1)
            model.makeVertexTypeInheritance(vt0, vt1)
            val vt2 = model.makeVertexType {
                name = "vt2"
                abstractness = EAbstractness.ABSTRACT
            }
            model.makeVertexTypeContainment(pkg, vt2)
            model.makeVertexTypeInheritance(vt2, vt1)

            val et0 = model.rootDirectedEdgeType
            val et1 = model.makeDirectedEdgeType { name = "et1" }
            model.makeDirectedEdgeTypeContainment(pkg, et1)
            model.makeDirectedEdgeTypeInheritance(et0, et1)
            val et2 = model.makeDirectedEdgeType { name = "et2" }
            model.makeDirectedEdgeTypeContainment(pkg, et2)
            model.makeDirectedEdgeTypeInheritance(et1, et2)
            val et3 = model.makeDirectedEdgeType { name = "et3" }
            model.makeDirectedEdgeTypeContainment(pkg, et3)
            model.makeDirectedEdgeTypeInheritance(et2, et3)
            val et4 = model.makeDirectedEdgeType { name = "et4" }
            model.makeDirectedEdgeTypeContainment(pkg, et4)
            model.makeDirectedEdgeTypeInheritance(et3, et4)

            assertEquals(et0, et1.superTypes[0])
            assertEquals(et1, et2.superTypes[0])
            assertEquals(et2, et3.superTypes[0])
            assertEquals(et3, et4.superTypes[0])

            assertTrue(et0.subTypes.contains(et1))
            assertFalse(et0.subTypes.contains(et2))
            assertTrue(et1.subTypes.contains(et2))
            assertFalse(et1.subTypes.contains(et3))
            assertTrue(et2.subTypes.contains(et3))
            assertTrue(et3.subTypes.contains(et4))
            assertTrue(et4.subTypes.isEmpty())

            assertFalse(et0.hasSuperType(et0))
            assertFalse(et1.hasSuperType(et1))
            assertFalse(et2.hasSuperType(et2))
            assertFalse(et3.hasSuperType(et3))

            assertTrue(et1.hasSuperType(et0))
            assertFalse(et0.hasSuperType(et1))

            assertFalse(et2.hasSuperType(et0))
            assertTrue(et2.hasTransitiveSuperType(et0))
            assertTrue(et2.hasSuperType(et1))
            assertFalse(et1.hasSuperType(et2))

            assertTrue(et3.hasTransitiveSuperType(et0))
            assertTrue(et3.hasTransitiveSuperType(et1))
            assertTrue(et3.hasSuperType(et2))
            assertTrue(et3.hasTransitiveSuperType(et2))

            assertTrue(et4.hasTransitiveSuperType(et0))
            assertTrue(et4.hasTransitiveSuperType(et1))
            assertTrue(et4.hasTransitiveSuperType(et2))
            assertTrue(et4.hasSuperType(et3))

            assertTrue(et0.hasSubType(et1))
            assertTrue(et0.hasTransitiveSubType(et1))
            assertTrue(et0.hasTransitiveSubType(et2))
            assertTrue(et0.hasTransitiveSubType(et3))
            assertTrue(et0.hasTransitiveSubType(et4))

            assertTrue(et1.hasTransitiveSubType(et2))
            assertTrue(et1.hasTransitiveSubType(et3))
            assertTrue(et1.hasTransitiveSubType(et4))

            assertTrue(et2.hasTransitiveSubType(et3))
            assertTrue(et2.hasTransitiveSubType(et4))

            assertTrue(et3.hasTransitiveSubType(et4))
            "test"
        }
    }


}