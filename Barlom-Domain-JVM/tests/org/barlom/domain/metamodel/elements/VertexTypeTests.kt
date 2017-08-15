//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.api.types.EAbstractness
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
 * Tests of VertexType.
 */
class VertexTypeTests {

    @Test
    fun `Vertex types construct as expected`() {

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy(root) }
            val vtId = makeUuid()
            val vt = VertexType(vtId, "vt", EAbstractness.CONCRETE, root.rootVertexType) { containedBy(pkg) }

            assertEquals(vtId, vt.id)
            assertEquals("vt", vt.name)
            assertEquals(EAbstractness.CONCRETE, vt.abstractness)
            assertEquals(pkg, vt.parentPackage)
        }

    }

    @Test
    fun `Vertex types have paths`() {

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy(root) }
            val vt = VertexType(makeUuid(), "vt", EAbstractness.CONCRETE, root.rootVertexType) { containedBy(pkg) }

            assertEquals("pkg.vt", vt.path)
        }

    }

    @Test
    fun `Vertex types track their supertype and subtypes`() {

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy(root) }
            val vt0 = root.rootVertexType
            val vt1 = VertexType(makeUuid(), "vt1", EAbstractness.ABSTRACT, vt0) { containedBy(pkg) }
            val vt2 = VertexType(makeUuid(), "vt2", EAbstractness.ABSTRACT, vt1) { containedBy(pkg) }
            val vt3 = VertexType(makeUuid(), "vt3", EAbstractness.ABSTRACT, vt2) { containedBy(pkg) }
            val vt4 = VertexType(makeUuid(), "vt4", EAbstractness.CONCRETE, vt3) { containedBy(pkg) }

            assertEquals(vt0, vt1.superType)
            assertEquals(vt1, vt2.superType)
            assertEquals(vt2, vt3.superType)
            assertEquals(vt3, vt4.superType)

            assertTrue(vt0.subTypes.contains(vt1))
            assertFalse(vt0.subTypes.contains(vt2))
            assertTrue(vt1.subTypes.contains(vt2))
            assertFalse(vt1.subTypes.contains(vt3))
            assertTrue(vt2.subTypes.contains(vt3))
            assertTrue(vt3.subTypes.contains(vt4))
            assertTrue(vt4.subTypes.isEmpty())

            assertFalse(vt0.isSubTypeOf(vt0))
            assertFalse(vt1.isSubTypeOf(vt1))
            assertFalse(vt2.isSubTypeOf(vt2))
            assertFalse(vt3.isSubTypeOf(vt3))

            assertTrue(vt1.isSubTypeOf(vt0))
            assertFalse(vt0.isSubTypeOf(vt1))

            assertTrue(vt2.isSubTypeOf(vt0))
            assertTrue(vt2.isSubTypeOf(vt1))
            assertFalse(vt1.isSubTypeOf(vt2))

            assertTrue(vt3.isSubTypeOf(vt0))
            assertTrue(vt3.isSubTypeOf(vt1))
            assertTrue(vt3.isSubTypeOf(vt2))

            assertTrue(vt4.isSubTypeOf(vt0))
            assertTrue(vt4.isSubTypeOf(vt1))
            assertTrue(vt4.isSubTypeOf(vt2))
            assertTrue(vt4.isSubTypeOf(vt3))

            assertTrue(vt0.transitiveSubTypes.contains(vt1))
            assertTrue(vt0.transitiveSubTypes.contains(vt2))
            assertTrue(vt0.transitiveSubTypes.contains(vt3))
            assertTrue(vt0.transitiveSubTypes.contains(vt4))

            assertTrue(vt1.transitiveSubTypes.contains(vt2))
            assertTrue(vt1.transitiveSubTypes.contains(vt3))
            assertTrue(vt1.transitiveSubTypes.contains(vt4))

            assertTrue(vt2.transitiveSubTypes.contains(vt3))
            assertTrue(vt2.transitiveSubTypes.contains(vt4))

            assertTrue(vt3.transitiveSubTypes.contains(vt4))
        }

    }

}
