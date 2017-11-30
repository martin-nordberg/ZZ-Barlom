//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of Package.
 */
class PackageTests {

    @Test
    fun `Root elements of the model have fixed names`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                assertEquals("Metamodel", rootPackage.name)
                assertEquals("RootVertexType", rootVertexType.name)
                assertEquals("RootDirectedEdgeType", rootDirectedEdgeType.name)
                assertEquals("RootUndirectedEdgeType", rootUndirectedEdgeType.name)

                assertFailsWith(IllegalStateException::class) {
                    rootPackage.name = "blah"
                }

                assertFailsWith(IllegalStateException::class) {
                    rootVertexType.name = "blah"
                }

                assertFailsWith(IllegalStateException::class) {
                    rootDirectedEdgeType.name = "blah"
                }

                assertFailsWith(IllegalStateException::class) {
                    rootUndirectedEdgeType.name = "blah"
                }

            }

            "test"

        }

    }

    @Test
    fun `Root elements of the model have fixed descriptions`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                assertEquals("Root package.", rootPackage.description)
                assertEquals("Root vertex type.", rootVertexType.description)
                assertEquals("Root directed edge type.", rootDirectedEdgeType.description)
                assertEquals("Root undirected edge type.", rootUndirectedEdgeType.description)

                assertFailsWith(IllegalStateException::class) {
                    rootPackage.description = "blah"
                }

                assertFailsWith(IllegalStateException::class) {
                    rootVertexType.description = "blah"
                }

                assertFailsWith(IllegalStateException::class) {
                    rootDirectedEdgeType.description = "blah"
                }

                assertFailsWith(IllegalStateException::class) {
                    rootUndirectedEdgeType.description = "blah"
                }

            }

            "test"

        }

    }

    @Test
    fun `Packages are vertices of the graph`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg = makePackage { name = "pkg" }
                val subpkg = makePackage { name = "subpkg" }

                assertTrue(vertices.contains(root))
                assertTrue(vertices.contains(pkg))
                assertTrue(vertices.contains(subpkg))

                assertEquals(6, vertices.size)
            }

            "test"

        }

    }

    @Test
    fun `Packages links are edges of the graph`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg1 = makePackage { name = "pkg1" }
                val pkg2 = makePackage { name = "pkg2" }
                val pkg3 = makePackage { name = "pkg3" }

                makePackageContainment(root, pkg1)
                makePackageContainment(root, pkg2)
                makePackageContainment(root, pkg3)

                makePackageDependency(pkg1, pkg2)
                makePackageDependency(pkg2, pkg3)

                for (c in root.childPackageContainments) {
                    assertTrue(edges.contains(c))
                }

                for (d in pkg1.supplierPackageDependencies) {
                    assertTrue(edges.contains(d))
                }

                for (d in pkg2.supplierPackageDependencies) {
                    assertTrue(edges.contains(d))
                }

                assertEquals(8, edges.size)

            }

            "test"

        }

    }

    @Test
    fun `Packages have paths`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg = makePackage { name = "pkg" }
                val subpkg = makePackage { name = "subpkg" }

                makePackageContainment(root, pkg)
                makePackageContainment(pkg, subpkg)

                assertEquals("pkg", pkg.path)
                assertEquals("pkg.subpkg", subpkg.path)
            }

            "test"

        }

    }

    @Test
    fun `Packages track their children and their parent and grandparents`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg = makePackage { name = "pkg" }
                val subpkg = makePackage { name = "subpkg" }

                makePackageContainment(root, pkg)
                makePackageContainment(pkg, subpkg)

                assertEquals(root, pkg.parentPackageContainments[0].parent)
                assertEquals(pkg, subpkg.parentPackageContainments[0].parent)

                assertEquals(pkg, root.childPackageContainments[0].child)
                assertEquals(subpkg, pkg.childPackageContainments[0].child)

                assertTrue(root.children.contains(pkg))
                assertTrue(pkg.children.contains(subpkg))
                assertFalse(root.children.contains(subpkg))

                assertTrue(root.hasChild(pkg))
                assertFalse(root.hasChild(subpkg))
                assertTrue(pkg.hasChild(subpkg))
                assertFalse(pkg.hasChild(root))
                assertFalse(subpkg.hasChild(root))
                assertFalse(subpkg.hasChild(pkg))

                assertFalse(root.hasParent(pkg))
                assertFalse(root.hasParent(subpkg))
                assertFalse(pkg.hasParent(subpkg))
                assertTrue(pkg.hasParent(root))
                assertFalse(subpkg.hasParent(root))
                assertTrue(subpkg.hasParent(pkg))

                assertTrue(root.hasTransitiveChild(pkg))
                assertTrue(root.hasTransitiveChild(subpkg))
                assertTrue(pkg.hasTransitiveChild(subpkg))
                assertFalse(pkg.hasTransitiveChild(root))
                assertFalse(subpkg.hasTransitiveChild(root))
                assertFalse(subpkg.hasTransitiveChild(pkg))

                assertFalse(root.hasTransitiveParent(pkg))
                assertFalse(root.hasTransitiveParent(subpkg))
                assertFalse(pkg.hasTransitiveParent(subpkg))
                assertTrue(pkg.hasTransitiveParent(root))
                assertTrue(subpkg.hasTransitiveParent(root))
                assertTrue(subpkg.hasTransitiveParent(pkg))

            }

            "test"

        }

    }

    @Test
    fun `Packages track their adjacent and transitive dependencies`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg1 = makePackage { name = "pkg1" }
                val pkg2 = makePackage { name = "pkg2" }
                val pkg3 = makePackage { name = "pkg3" }

                makePackageContainment(root, pkg1)
                makePackageContainment(root, pkg2)
                makePackageContainment(root, pkg3)

                makePackageDependency(pkg1, pkg2)
                makePackageDependency(pkg2, pkg3)

                assertTrue(pkg1.hasSupplier(pkg2))
                assertFalse(pkg2.hasSupplier(pkg1))
                assertTrue(pkg2.hasSupplier(pkg3))
                assertTrue(pkg1.hasTransitiveSupplier(pkg3))
                assertFalse(pkg1.hasSupplier(pkg3))

                assertTrue(pkg2.hasConsumer(pkg1))
                assertTrue(pkg3.hasConsumer(pkg2))
                assertTrue(pkg3.hasTransitiveConsumer(pkg1))
                assertFalse(pkg3.hasConsumer(pkg1))

                assertTrue(pkg1.suppliers.contains(pkg2))
                assertTrue(pkg1.transitiveSuppliers.contains(pkg2))
                assertTrue(pkg1.supplierPackageDependencies[0].supplier == pkg2)
                assertTrue(pkg2.consumerPackageDependencies[0].consumer == pkg1)
                assertFalse(pkg1.suppliers.contains(pkg3))
                assertTrue(pkg1.transitiveSuppliers.contains(pkg3))

                assertTrue(pkg2.consumers.contains(pkg1))
                assertTrue(pkg3.transitiveConsumers.contains(pkg1))
                assertFalse(pkg3.consumers.contains(pkg1))
                assertTrue(pkg2.consumers.contains(pkg1))
                assertTrue(pkg2.transitiveConsumers.contains(pkg1))

            }

            "test"

        }

    }

    @Test
    fun `Direct circular package dependencies are handled correctly`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg1 = makePackage { name = "pkg1" }
                val pkg2 = makePackage { name = "pkg2" }

                makePackageContainment(root, pkg1)
                makePackageContainment(root, pkg2)

                makePackageDependency(pkg1, pkg2)
                makePackageDependency(pkg2, pkg1)

                assertTrue(pkg1.hasTransitiveConsumer(pkg2))
                assertTrue(pkg1.hasTransitiveConsumer(pkg1))

                assertTrue(pkg1.hasTransitiveSupplier(pkg2))
                assertTrue(pkg1.hasTransitiveSupplier(pkg1))

                assertTrue(pkg1.transitiveSuppliers.contains(pkg2))
                assertTrue(pkg1.transitiveSuppliers.contains(pkg1))

                assertTrue(pkg1.transitiveConsumers.contains(pkg2))
                assertTrue(pkg1.transitiveConsumers.contains(pkg1))

            }

            "test"

        }

    }

    @Test
    fun `Indirect circular package dependencies are handled correctly`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg1 = makePackage { name = "pkg1" }
                val pkg2 = makePackage { name = "pkg2" }
                val pkg3 = makePackage { name = "pkg3" }

                makePackageContainment(root, pkg1)
                makePackageContainment(root, pkg2)
                makePackageContainment(root, pkg3)

                makePackageDependency(pkg1, pkg2)
                makePackageDependency(pkg2, pkg3)
                makePackageDependency(pkg3, pkg1)

                assertTrue(pkg1.hasTransitiveConsumer(pkg2))
                assertTrue(pkg1.hasTransitiveConsumer(pkg3))
                assertTrue(pkg1.hasTransitiveConsumer(pkg1))

                assertTrue(pkg1.hasTransitiveSupplier(pkg2))
                assertTrue(pkg1.hasTransitiveSupplier(pkg3))
                assertTrue(pkg1.hasTransitiveSupplier(pkg1))

                assertTrue(pkg1.transitiveSuppliers.contains(pkg2))
                assertTrue(pkg1.transitiveSuppliers.contains(pkg3))
                assertTrue(pkg1.transitiveSuppliers.contains(pkg1))

                assertTrue(pkg1.transitiveConsumers.contains(pkg2))
                assertTrue(pkg1.transitiveConsumers.contains(pkg3))
                assertTrue(pkg1.transitiveConsumers.contains(pkg1))

            }

            "test"

        }

    }

}