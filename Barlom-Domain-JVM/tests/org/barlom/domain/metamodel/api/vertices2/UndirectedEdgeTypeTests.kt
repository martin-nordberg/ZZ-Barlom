package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of UndirectedEdgeType.
 */
class UndirectedEdgeTypeTests {

    @Test
    fun `Undirected edge types construct as expected`() {

        val model = Model()

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
            assertEquals(EAbstractness.CONCRETE, et.abstractness)
            assertEquals(ECyclicity.ACYCLIC, et.cyclicity)
            assertEquals(EMultiEdgedness.UNCONSTRAINED, et.multiEdgedness)
            assertEquals(ESelfLooping.SELF_LOOPS_NOT_ALLOWED, et.selfLooping)
            assertEquals(11, et.maxDegree)
            assertEquals(5, et.minDegree)
            assertTrue(et.superTypes.contains(model.rootUndirectedEdgeType))
            assertTrue(model.rootUndirectedEdgeType.subTypes.contains(et))
            assertTrue(et.connectedVertexTypes.contains(vt1))

            assertEquals("pkg.et", et.path)
        }

    }

    /**
    @Test
    fun `Undirected edge types track their supertype and subtypes`() {

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

    val et0 = root.rootUndirectedEdgeType
    val et1 = UndirectedEdgeType(makeUuid(), "et1", a, c, m, s,
    null, null, et0, vt2) { containedBy(pkg) }
    val et2 = UndirectedEdgeType(makeUuid(), "et2", a, c, m, s,
    null, null, et1, vt2) { containedBy(pkg) }
    val et3 = UndirectedEdgeType(makeUuid(), "et3", a, c, m, s,
    null, null, et2, vt2) { containedBy(pkg) }
    val et4 = UndirectedEdgeType(makeUuid(), "et4", a, c, m, s,
    null, null, et3, vt2) { containedBy(pkg) }

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
     **/

}