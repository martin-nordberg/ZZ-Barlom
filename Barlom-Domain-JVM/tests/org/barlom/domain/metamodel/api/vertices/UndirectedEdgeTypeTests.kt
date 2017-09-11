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
 * Tests of UndirectedEdgeType.
 */
class UndirectedEdgeTypeTests {

    @Test
    fun `Undirected edge types construct as expected`() {

        val model = Model({makeUuid()})

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage() {
                name = "pkg"
            }
            model.makePackageContainment(root, pkg)
            val vt1 = model.makeVertexType()
            model.makeVertexTypeContainment(pkg, vt1)
            val etId = makeUuid()
            val et = model.makeUndirectedEdgeType(etId) {
                name = "et"
                abstractness = EAbstractness.CONCRETE
                cyclicity = ECyclicity.ACYCLIC
                multiEdgedness = EMultiEdgedness.UNCONSTRAINED
                selfLooping = ESelfLooping.SELF_LOOPS_NOT_ALLOWED
                maxDegree = 11
                minDegree = 5
            }
            model.makeUndirectedEdgeTypeContainment(pkg, et)
            model.makeUndirectedEdgeTypeConnectivity(et, vt1)
            model.makeUndirectedEdgeTypeInheritance(model.rootUndirectedEdgeType, et)

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
            assertTrue(et.superTypes.contains(model.rootUndirectedEdgeType))
            assertTrue(model.rootUndirectedEdgeType.subTypes.contains(et))
            assertTrue(et.connectedVertexTypes.contains(vt1))
            assertTrue(vt1.connectingEdgeTypes.contains(et))

            assertEquals("pkg.et", et.path)
        }

    }

    @Test
    fun `Undirected edge types track their supertype and subtypes`() {

        val model = Model({makeUuid()})

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage() {
                name = "pkg"
            }
            model.makePackageContainment(root, pkg)
            val vt0 = model.rootVertexType
            val vt1 = model.makeVertexType { name = "vt1" }
            model.makeVertexTypeContainment(pkg, vt1)
            model.makeVertexTypeInheritance(vt0, vt1)
            val vt2 = model.makeVertexType { name = "vt2" }
            model.makeVertexTypeContainment(pkg, vt2)
            model.makeVertexTypeInheritance(vt1, vt2)

            val et0 = model.rootUndirectedEdgeType
            val et1 = model.makeUndirectedEdgeType { name = "et1" }
            model.makeUndirectedEdgeTypeContainment(pkg, et1)
            model.makeUndirectedEdgeTypeInheritance(et0, et1)
            val et2 = model.makeUndirectedEdgeType { name = "et3" }
            model.makeUndirectedEdgeTypeContainment(pkg, et2)
            model.makeUndirectedEdgeTypeInheritance(et1, et2)
            val et3 = model.makeUndirectedEdgeType { name = "et3" }
            model.makeUndirectedEdgeTypeContainment(pkg, et3)
            model.makeUndirectedEdgeTypeInheritance(et2, et3)
            val et4 = model.makeUndirectedEdgeType { name = "et4" }
            model.makeUndirectedEdgeTypeContainment(pkg, et4)
            model.makeUndirectedEdgeTypeInheritance(et3, et4)

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

    }

}