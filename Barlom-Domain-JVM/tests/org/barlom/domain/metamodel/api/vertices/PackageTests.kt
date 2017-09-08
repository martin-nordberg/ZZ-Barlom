//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of Package.
 */
class PackageTests {

    @Test
    fun `Packages are vertices of the graph`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage { name = "pkg" }
            val subpkg = model.makePackage { name = "subpkg" }

            assertTrue(model.vertices.contains(root))
            assertTrue(model.vertices.contains(pkg))
            assertTrue(model.vertices.contains(subpkg))

            assertEquals(6, model.vertices.size)
        }

    }

    @Test
    fun `Packages links are edges of the graph`() {

        val model = Model()

        model.revHistory.update("test") {

            val root = model.rootPackage
            val pkg1 = model.makePackage { name = "pkg1" }
            val pkg2 = model.makePackage { name = "pkg2" }
            val pkg3 = model.makePackage { name = "pkg3" }

            model.makePackageContainment(root, pkg1)
            model.makePackageContainment(root, pkg2)
            model.makePackageContainment(root, pkg3)

            model.makePackageDependency(pkg1, pkg2)
            model.makePackageDependency(pkg2, pkg3)

            for (c in root.childPackageContainments) {
                assertTrue(model.edges.contains(c))
            }

            for (d in pkg1.supplierPackageDependencies) {
                assertTrue(model.edges.contains(d))
            }

            for (d in pkg2.supplierPackageDependencies) {
                assertTrue(model.edges.contains(d))
            }

            assertEquals(8, model.edges.size)

        }

    }

    @Test
    fun `Packages have paths`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage { name = "pkg" }
            val subpkg = model.makePackage { name = "subpkg" }

            model.makePackageContainment(root, pkg)
            model.makePackageContainment(pkg, subpkg)

            assertEquals("pkg", pkg.path)
            assertEquals("pkg.subpkg", subpkg.path)
        }

    }

    @Test
    fun `Packages track their children and their parent and grandparents`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage { name = "pkg" }
            val subpkg = model.makePackage { name = "subpkg" }

            model.makePackageContainment(root, pkg)
            model.makePackageContainment(pkg, subpkg)

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

    }

    @Test
    fun `Packages track their adjacent and transitive dependencies`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg1 = model.makePackage { name = "pkg1" }
            val pkg2 = model.makePackage { name = "pkg2" }
            val pkg3 = model.makePackage { name = "pkg3" }

            model.makePackageContainment(root, pkg1)
            model.makePackageContainment(root, pkg2)
            model.makePackageContainment(root, pkg3)

            model.makePackageDependency(pkg1, pkg2)
            model.makePackageDependency(pkg2, pkg3)

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

    }

    @Test
    fun `Direct circular package dependencies are handled correctly`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg1 = model.makePackage { name = "pkg1" }
            val pkg2 = model.makePackage { name = "pkg2" }

            model.makePackageContainment(root, pkg1)
            model.makePackageContainment(root, pkg2)

            model.makePackageDependency(pkg1, pkg2)
            model.makePackageDependency(pkg2, pkg1)

            assertTrue(pkg1.hasTransitiveConsumer(pkg2))
            assertTrue(pkg1.hasTransitiveConsumer(pkg1))

            assertTrue(pkg1.hasTransitiveSupplier(pkg2))
            assertTrue(pkg1.hasTransitiveSupplier(pkg1))

            assertTrue(pkg1.transitiveSuppliers.contains(pkg2))
            assertTrue(pkg1.transitiveSuppliers.contains(pkg1))

            assertTrue(pkg1.transitiveConsumers.contains(pkg2))
            assertTrue(pkg1.transitiveConsumers.contains(pkg1))
        }

    }

    @Test
    fun `Indirect circular package dependencies are handled correctly`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg1 = model.makePackage { name = "pkg1" }
            val pkg2 = model.makePackage { name = "pkg2" }
            val pkg3 = model.makePackage { name = "pkg3" }

            model.makePackageContainment(root, pkg1)
            model.makePackageContainment(root, pkg2)
            model.makePackageContainment(root, pkg3)

            model.makePackageDependency(pkg1, pkg2)
            model.makePackageDependency(pkg2, pkg3)
            model.makePackageDependency(pkg3, pkg1)

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

    }

}