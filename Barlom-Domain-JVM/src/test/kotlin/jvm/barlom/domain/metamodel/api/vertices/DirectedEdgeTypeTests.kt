//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.metamodel.api.vertices

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.types.EAbstractness
import o.barlom.domain.metamodel.api.types.ECyclicity
import o.barlom.domain.metamodel.api.types.EMultiEdgedness
import o.barlom.domain.metamodel.api.types.ESelfLooping
import o.barlom.infrastructure.revisions.RevisionHistory
import org.junit.jupiter.api.Test
import x.barlom.infrastructure.uuids.makeUuid
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of DirectedConnectionType.
 */
@Suppress("RemoveRedundantBackticks")
class DirectedEdgeTypeTests {

    @Test
    fun `Directed edge types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage() {
                    name = "pkg"
                }
                makePackageContainment(root, pkg)
                val vt1 = makeVertexType()
                makeVertexTypeContainment(pkg, vt1)
                val vt2 = makeVertexType()
                makeVertexTypeContainment(pkg, vt2)
                val etId = makeUuid()
                val et = makeDirectedEdgeType(etId) {
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
                makeDirectedEdgeTypeContainment(pkg, et)
                makeDirectedEdgeTypeHeadConnectivity(et, vt1)
                makeDirectedEdgeTypeTailConnectivity(et, vt2)
                makeDirectedEdgeTypeInheritance(rootDirectedEdgeType, et)


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
                assertEquals(rootDirectedEdgeType, et.superTypes[0])
                assertEquals(et, rootDirectedEdgeType.subTypes[0])
                assertEquals("tail", et.tailRoleName)

                assertEquals("pkg.et", et.path)

            }

            "test"

        }

    }

    @Test
    fun `Directed edge types track their supertype and subtypes`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage() {
                    name = "pkg"
                }
                makePackageContainment(root, pkg)
                val vt0 = rootVertexType
                val vt1 = makeVertexType {
                    name = "vt1"
                    abstractness = EAbstractness.ABSTRACT
                }
                makeVertexTypeContainment(pkg, vt1)
                makeVertexTypeInheritance(vt0, vt1)
                val vt2 = makeVertexType {
                    name = "vt2"
                    abstractness = EAbstractness.ABSTRACT
                }
                makeVertexTypeContainment(pkg, vt2)
                makeVertexTypeInheritance(vt2, vt1)

                val et0 = rootDirectedEdgeType
                val et1 = makeDirectedEdgeType { name = "et1" }
                makeDirectedEdgeTypeContainment(pkg, et1)
                makeDirectedEdgeTypeInheritance(et0, et1)
                val et2 = makeDirectedEdgeType { name = "et2" }
                makeDirectedEdgeTypeContainment(pkg, et2)
                makeDirectedEdgeTypeInheritance(et1, et2)
                val et3 = makeDirectedEdgeType { name = "et3" }
                makeDirectedEdgeTypeContainment(pkg, et3)
                makeDirectedEdgeTypeInheritance(et2, et3)
                val et4 = makeDirectedEdgeType { name = "et4" }
                makeDirectedEdgeTypeContainment(pkg, et4)
                makeDirectedEdgeTypeInheritance(et3, et4)

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

            }

            "test"

        }

    }


}
