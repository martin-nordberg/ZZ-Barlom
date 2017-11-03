//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.infrastructure.revisions.RevisionHistory
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
    fun `Vertex types are vertices of the model graph`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val vt = makeVertexType()
                val c = makeVertexTypeContainment(pkg, vt)

                assertTrue(vertices.contains(vt))
                assertTrue(edges.contains(c))

            }

            "test"

        }

    }

    @Test
    fun `Vertex types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage { name = "pkg" }
                makePackageContainment(root, pkg)
                val vtId = makeUuid()
                val vt = makeVertexType(vtId) { name = "vt";abstractness = EAbstractness.ABSTRACT }
                makeVertexTypeContainment(pkg, vt)

                assertEquals(vtId, vt.id)
                assertEquals("vt", vt.name)
                assertEquals(EAbstractness.ABSTRACT, vt.abstractness)
                assertEquals(pkg, vt.parents[0])
                assertEquals(pkg, vt.vertexTypeContainments[0].parent)
                assertEquals(vt, pkg.vertexTypes[0])
                assertEquals(vt, pkg.vertexTypeContainments[0].child)

                assertTrue(vt.hasParent(pkg))
                assertTrue(pkg.hasChild(vt))

            }

            "test"

        }

    }

    @Test
    fun `Vertex types have paths`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage { name = "pkg" }
                makePackageContainment(root, pkg)
                val vtId = makeUuid()
                val vt = makeVertexType(vtId) { name = "vt" }
                makeVertexTypeContainment(pkg, vt)

                assertEquals("pkg.vt", vt.path)

            }

            "test"

        }

    }

    @Test
    fun `Vertex types track their supertype and subtypes`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage { name = "pkg" }
                makePackageContainment(root, pkg)
                val vt0 = rootVertexType
                val vt1 = makeVertexType { name = "vt1"; abstractness = EAbstractness.ABSTRACT }
                val vt2 = makeVertexType { name = "vt2"; abstractness = EAbstractness.ABSTRACT }
                val vt3 = makeVertexType { name = "vt3"; abstractness = EAbstractness.ABSTRACT }
                val vt4 = makeVertexType { name = "vt4" }
                makeVertexTypeContainment(pkg, vt1)
                makeVertexTypeContainment(pkg, vt2)
                makeVertexTypeContainment(pkg, vt3)
                makeVertexTypeContainment(pkg, vt4)
                makeVertexTypeInheritance(vt0, vt1)
                makeVertexTypeInheritance(vt1, vt2)
                makeVertexTypeInheritance(vt2, vt3)
                makeVertexTypeInheritance(vt3, vt4)

                assertEquals(vt0, vt1.superTypes[0])
                assertEquals(vt1, vt2.superTypes[0])
                assertEquals(vt2, vt3.superTypes[0])
                assertEquals(vt3, vt4.superTypes[0])

                assertEquals(vt0, vt1.superTypeVertexTypeInheritances[0].superType)
                assertEquals(vt1, vt2.superTypeVertexTypeInheritances[0].superType)
                assertEquals(vt2, vt3.superTypeVertexTypeInheritances[0].superType)
                assertEquals(vt3, vt4.superTypeVertexTypeInheritances[0].superType)

                assertTrue(vt0.subTypes.contains(vt1))
                assertFalse(vt0.subTypes.contains(vt2))
                assertTrue(vt1.subTypes.contains(vt2))
                assertFalse(vt1.subTypes.contains(vt3))
                assertTrue(vt2.subTypes.contains(vt3))
                assertTrue(vt3.subTypes.contains(vt4))
                assertTrue(vt4.subTypes.isEmpty())

                assertEquals(vt1, vt0.subTypeVertexTypeInheritances[0].subType)
                assertEquals(vt2, vt1.subTypeVertexTypeInheritances[0].subType)
                assertEquals(vt3, vt2.subTypeVertexTypeInheritances[0].subType)
                assertEquals(vt4, vt3.subTypeVertexTypeInheritances[0].subType)

                assertFalse(vt0.hasSubType(vt0))
                assertTrue(vt0.hasSubType(vt1))
                assertFalse(vt0.hasSubType(vt2))
                assertFalse(vt0.hasSubType(vt3))
                assertFalse(vt0.hasSubType(vt4))
                assertFalse(vt1.hasSubType(vt0))
                assertFalse(vt1.hasSubType(vt1))
                assertTrue(vt1.hasSubType(vt2))
                assertFalse(vt1.hasSubType(vt3))
                assertFalse(vt1.hasSubType(vt4))
                assertFalse(vt2.hasSubType(vt0))
                assertFalse(vt2.hasSubType(vt1))
                assertFalse(vt2.hasSubType(vt2))
                assertTrue(vt2.hasSubType(vt3))
                assertFalse(vt2.hasSubType(vt4))
                assertFalse(vt3.hasSubType(vt0))
                assertFalse(vt3.hasSubType(vt1))
                assertFalse(vt3.hasSubType(vt2))
                assertFalse(vt3.hasSubType(vt3))
                assertTrue(vt3.hasSubType(vt4))
                assertFalse(vt4.hasSubType(vt0))
                assertFalse(vt4.hasSubType(vt1))
                assertFalse(vt4.hasSubType(vt2))
                assertFalse(vt4.hasSubType(vt3))
                assertFalse(vt4.hasSubType(vt4))

                assertFalse(vt0.hasSuperType(vt0))
                assertFalse(vt0.hasSuperType(vt1))
                assertFalse(vt0.hasSuperType(vt2))
                assertFalse(vt0.hasSuperType(vt3))
                assertFalse(vt0.hasSuperType(vt4))
                assertTrue(vt1.hasSuperType(vt0))
                assertFalse(vt1.hasSuperType(vt1))
                assertFalse(vt1.hasSuperType(vt2))
                assertFalse(vt1.hasSuperType(vt3))
                assertFalse(vt1.hasSuperType(vt4))
                assertFalse(vt2.hasSuperType(vt0))
                assertTrue(vt2.hasSuperType(vt1))
                assertFalse(vt2.hasSuperType(vt2))
                assertFalse(vt2.hasSuperType(vt3))
                assertFalse(vt2.hasSuperType(vt4))
                assertFalse(vt3.hasSuperType(vt0))
                assertFalse(vt3.hasSuperType(vt1))
                assertTrue(vt3.hasSuperType(vt2))
                assertFalse(vt3.hasSuperType(vt3))
                assertFalse(vt3.hasSuperType(vt4))
                assertFalse(vt4.hasSuperType(vt0))
                assertFalse(vt4.hasSuperType(vt1))
                assertFalse(vt4.hasSuperType(vt2))
                assertTrue(vt4.hasSuperType(vt3))
                assertFalse(vt4.hasSuperType(vt4))

                assertFalse(vt0.hasTransitiveSubType(vt0))
                assertTrue(vt0.hasTransitiveSubType(vt1))
                assertTrue(vt0.hasTransitiveSubType(vt2))
                assertTrue(vt0.hasTransitiveSubType(vt3))
                assertTrue(vt0.hasTransitiveSubType(vt4))
                assertFalse(vt1.hasTransitiveSubType(vt0))
                assertFalse(vt1.hasTransitiveSubType(vt1))
                assertTrue(vt1.hasTransitiveSubType(vt2))
                assertTrue(vt1.hasTransitiveSubType(vt3))
                assertTrue(vt1.hasTransitiveSubType(vt4))
                assertFalse(vt2.hasTransitiveSubType(vt0))
                assertFalse(vt2.hasTransitiveSubType(vt1))
                assertFalse(vt2.hasTransitiveSubType(vt2))
                assertTrue(vt2.hasTransitiveSubType(vt3))
                assertTrue(vt2.hasTransitiveSubType(vt4))
                assertFalse(vt3.hasTransitiveSubType(vt0))
                assertFalse(vt3.hasTransitiveSubType(vt1))
                assertFalse(vt3.hasTransitiveSubType(vt2))
                assertFalse(vt3.hasTransitiveSubType(vt3))
                assertTrue(vt3.hasTransitiveSubType(vt4))
                assertFalse(vt4.hasTransitiveSubType(vt0))
                assertFalse(vt4.hasTransitiveSubType(vt1))
                assertFalse(vt4.hasTransitiveSubType(vt2))
                assertFalse(vt4.hasTransitiveSubType(vt3))
                assertFalse(vt4.hasTransitiveSubType(vt4))

                assertFalse(vt0.hasTransitiveSuperType(vt0))
                assertFalse(vt0.hasTransitiveSuperType(vt1))
                assertFalse(vt0.hasTransitiveSuperType(vt2))
                assertFalse(vt0.hasTransitiveSuperType(vt3))
                assertFalse(vt0.hasTransitiveSuperType(vt4))
                assertTrue(vt1.hasTransitiveSuperType(vt0))
                assertFalse(vt1.hasTransitiveSuperType(vt1))
                assertFalse(vt1.hasTransitiveSuperType(vt2))
                assertFalse(vt1.hasTransitiveSuperType(vt3))
                assertFalse(vt1.hasTransitiveSuperType(vt4))
                assertTrue(vt2.hasTransitiveSuperType(vt0))
                assertTrue(vt2.hasTransitiveSuperType(vt1))
                assertFalse(vt2.hasTransitiveSuperType(vt2))
                assertFalse(vt2.hasTransitiveSuperType(vt3))
                assertFalse(vt2.hasTransitiveSuperType(vt4))
                assertTrue(vt3.hasTransitiveSuperType(vt0))
                assertTrue(vt3.hasTransitiveSuperType(vt1))
                assertTrue(vt3.hasTransitiveSuperType(vt2))
                assertFalse(vt3.hasTransitiveSuperType(vt3))
                assertFalse(vt3.hasTransitiveSuperType(vt4))
                assertTrue(vt4.hasTransitiveSuperType(vt0))
                assertTrue(vt4.hasTransitiveSuperType(vt1))
                assertTrue(vt4.hasTransitiveSuperType(vt2))
                assertTrue(vt4.hasTransitiveSuperType(vt3))
                assertFalse(vt4.hasTransitiveSuperType(vt4))

            }

            "test"

        }

    }

}