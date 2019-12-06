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
import x.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of UndirectedEdgeType.
 */
@Suppress("RemoveRedundantBackticks")
class UndirectedEdgeTypeTests {

    @Test
    fun `Undirected edge types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg = makePackage {
                    name = "pkg"
                }
                makePackageContainment(root, pkg)
                val vt1 = makeVertexType()
                makeVertexTypeContainment(pkg, vt1)
                val etId = makeUuid()
                val et = makeUndirectedEdgeType(etId) {
                    name = "et"
                    abstractness = EAbstractness.CONCRETE
                    cyclicity = ECyclicity.ACYCLIC
                    multiEdgedness = EMultiEdgedness.UNCONSTRAINED
                    selfLooping = ESelfLooping.SELF_LOOPS_NOT_ALLOWED
                    maxDegree = 11
                    minDegree = 5
                }
                makeUndirectedEdgeTypeContainment(pkg, et)
                makeUndirectedEdgeTypeConnectivity(et, vt1)
                makeUndirectedEdgeTypeInheritance(rootUndirectedEdgeType, et)

                assertEquals(etId, et.id)
                assertEquals("et", et.name)
                assertEquals(pkg, et.parents[0])
                assertEquals(et, pkg.undirectedEdgeTypes[0])
                assertEquals(EAbstractness.CONCRETE, et.abstractness)
                assertEquals(ECyclicity.ACYCLIC, et.cyclicity)
                assertEquals(EMultiEdgedness.UNCONSTRAINED, et.multiEdgedness)
                assertEquals(ESelfLooping.SELF_LOOPS_NOT_ALLOWED, et.selfLooping)
                assertEquals(11, et.maxDegree)
                assertEquals(5, et.minDegree)
                assertTrue(et.superTypes.contains(rootUndirectedEdgeType))
                assertTrue(rootUndirectedEdgeType.subTypes.contains(et))
                assertTrue(et.connectedVertexTypes.contains(vt1))
                assertTrue(vt1.connectingEdgeTypes.contains(et))

                assertEquals("pkg.et", et.path)

            }

            "test"

        }

    }

    @Test
    fun `Undirected edge types track their supertype and subtypes`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg = makePackage {
                    name = "pkg"
                }
                makePackageContainment(root, pkg)
                val vt0 = rootVertexType
                val vt1 = makeVertexType { name = "vt1" }
                makeVertexTypeContainment(pkg, vt1)
                makeVertexTypeInheritance(vt0, vt1)
                val vt2 = makeVertexType { name = "vt2" }
                makeVertexTypeContainment(pkg, vt2)
                makeVertexTypeInheritance(vt1, vt2)

                val et0 = rootUndirectedEdgeType
                val et1 = makeUndirectedEdgeType { name = "et1" }
                makeUndirectedEdgeTypeContainment(pkg, et1)
                makeUndirectedEdgeTypeInheritance(et0, et1)
                val et2 = makeUndirectedEdgeType { name = "et3" }
                makeUndirectedEdgeTypeContainment(pkg, et2)
                makeUndirectedEdgeTypeInheritance(et1, et2)
                val et3 = makeUndirectedEdgeType { name = "et3" }
                makeUndirectedEdgeTypeContainment(pkg, et3)
                makeUndirectedEdgeTypeInheritance(et2, et3)
                val et4 = makeUndirectedEdgeType { name = "et4" }
                makeUndirectedEdgeTypeContainment(pkg, et4)
                makeUndirectedEdgeTypeInheritance(et3, et4)

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

                assertFalse(et0.hasTransitiveSuperType(et0))
                assertFalse(et1.hasTransitiveSuperType(et1))
                assertFalse(et2.hasTransitiveSuperType(et2))
                assertFalse(et3.hasTransitiveSuperType(et3))

                assertTrue(et1.hasSuperType(et0))
                assertFalse(et0.hasTransitiveSuperType(et1))

                assertFalse(et2.hasSuperType(et0))
                assertTrue(et2.hasTransitiveSuperType(et0))
                assertTrue(et2.hasTransitiveSuperType(et1))
                assertFalse(et1.hasTransitiveSuperType(et2))

                assertTrue(et3.hasTransitiveSuperType(et0))
                assertTrue(et3.hasTransitiveSuperType(et1))
                assertTrue(et3.hasTransitiveSuperType(et2))

                assertTrue(et4.hasTransitiveSuperType(et0))
                assertTrue(et4.hasTransitiveSuperType(et1))
                assertTrue(et4.hasTransitiveSuperType(et2))
                assertTrue(et4.hasTransitiveSuperType(et3))

                assertTrue(et0.hasSubType(et1))
                assertTrue(et0.hasTransitiveSubType(et1))
                assertFalse(et0.hasSubType(et2))
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
