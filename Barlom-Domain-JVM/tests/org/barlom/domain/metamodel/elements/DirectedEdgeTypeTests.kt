//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.domain.metamodel.impl.elements.*
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.*

/**
 * Tests of DirectedEdgeType.
 */
class DirectedEdgeTypeTests {

    @Test
    fun `Directed edge types construct as expected`() {

        val root = RootPackage()
        val pkg = Package(makeUuid(),"pkg",root)
        val vt1 = VertexType(makeUuid(), "vt1", pkg, EAbstractness.CONCRETE, root.rootVertexType)
        val vt2 = VertexType(makeUuid(),"vt2",pkg,EAbstractness.CONCRETE,vt1)
        val etId = makeUuid()
        val et = DirectedEdgeType(etId,"et",pkg,EAbstractness.CONCRETE,ECyclicity.ACYCLIC,
                                  EMultiEdgedness.UNCONSTRAINED,ESelfLooping.SELF_LOOPS_NOT_ALLOWED,
                                  "head",vt1,
                                  11,9,7,5,
                                  root.rootDirectedEdgeType,"tail",vt2)

        assertEquals(etId,et.id)
        assertEquals("et", et.name)
        assertEquals(pkg,et.parentPackage)
        assertEquals(EAbstractness.CONCRETE,et.abstractness)
        assertEquals(ECyclicity.ACYCLIC,et.cyclicity)
        assertEquals(EMultiEdgedness.UNCONSTRAINED,et.multiEdgedness)
        assertEquals(ESelfLooping.SELF_LOOPS_NOT_ALLOWED,et.selfLooping)
        assertEquals("head",et.headRoleName)
        assertEquals(vt1,et.headVertexType)
        assertEquals(11,et.maxHeadInDegree)
        assertEquals(9,et.maxTailOutDegree)
        assertEquals(7,et.minHeadInDegree)
        assertEquals(5,et.minTailOutDegree)
        assertEquals(root.rootDirectedEdgeType,et.superType)
        assertEquals("tail",et.tailRoleName)
        assertEquals(vt2, et.tailVertexType)

        assertEquals("pkg.et", et.path)

    }

    @Test
    fun `Directed edge types track their supertype and subtypes`() {

        val root = RootPackage()
        val pkg = Package(makeUuid(),"pkg",root)
        val vt0 = root.rootVertexType
        val vt1 = VertexType(makeUuid(), "vt1", pkg, EAbstractness.ABSTRACT, vt0)
        val vt2 = VertexType(makeUuid(),"vt2",pkg,EAbstractness.ABSTRACT,vt1)

        val a = EAbstractness.ABSTRACT
        val c = ECyclicity.ACYCLIC
        val m = EMultiEdgedness.UNCONSTRAINED
        val s = ESelfLooping.SELF_LOOPS_NOT_ALLOWED

        val et0 = root.rootDirectedEdgeType
        val et1 = DirectedEdgeType(makeUuid(), "et1", pkg, a, c, m, s, null, vt1, null, null, null, null, et0, null, vt2)
        val et2 = DirectedEdgeType(makeUuid(),"et2", pkg, a, c, m, s, null, vt1, null, null, null, null, et1, null, vt2)
        val et3 = DirectedEdgeType(makeUuid(),"et3", pkg, a, c, m, s, null, vt1, null, null, null, null, et2, null, vt2)
        val et4 = DirectedEdgeType(makeUuid(),"et4", pkg, a, c, m, s, null, vt1, null, null, null, null, et3, null, vt2)

        assertEquals(et0, et1.superType)
        assertEquals( et1, et2.superType)
        assertEquals( et2, et3.superType)
        assertEquals( et3, et4.superType)

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
