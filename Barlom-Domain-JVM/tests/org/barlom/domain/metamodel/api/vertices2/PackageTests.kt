//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests of Package.
 */
class PackageTests {

    @Test
    fun `Packages have paths`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage(makeUuid(), "pkg")
            val subpkg = model.makePackage(makeUuid(), "subpkg")

            model.makePackageContainment(makeUuid(), root, pkg)
            model.makePackageContainment(makeUuid(), pkg, subpkg)

            assertEquals("pkg", pkg.path)
            assertEquals("pkg.subpkg", subpkg.path)
        }

    }

    @Test
    fun `Packages track their children and their parent and grandparents`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage(makeUuid(), "pkg")
            val subpkg = model.makePackage(makeUuid(), "subpkg")

            model.makePackageContainment(makeUuid(), root, pkg)
            model.makePackageContainment(makeUuid(), pkg, subpkg)

            assertEquals(root, pkg.parentPackageContainments[0].parent)
            assertEquals(pkg, subpkg.parentPackageContainments[0].parent)

            assertEquals(pkg, root.childPackageContainments[0].child)
            assertEquals(subpkg, pkg.childPackageContainments[0].child)

            assertTrue(root.childPackages.contains(pkg))
            assertTrue(pkg.childPackages.contains(subpkg))
            assertFalse(root.childPackages.contains(subpkg))

            assertTrue(pkg.isChildOf(root))
            assertTrue(subpkg.isChildOf(root))
            assertTrue(subpkg.isChildOf(pkg))
            assertFalse(root.isChildOf(pkg))
            assertFalse(root.isChildOf(subpkg))
            assertFalse(pkg.isChildOf(subpkg))
        }

    }

    @Test
    fun `Packages track their adjacent and transitive dependencies`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg1 = model.makePackage(makeUuid(), "pkg1")
            val pkg2 = model.makePackage(makeUuid(), "pkg2")
            val pkg3 = model.makePackage(makeUuid(), "pkg3")

            model.makePackageContainment(makeUuid(), root, pkg1)
            model.makePackageContainment(makeUuid(), root, pkg2)
            model.makePackageContainment(makeUuid(), root, pkg3)

            model.makePackageDependency(makeUuid(), pkg1, pkg2)
            model.makePackageDependency(makeUuid(), pkg2, pkg3)

            assertTrue(pkg1.hasSupplierPackage(pkg2))
            assertFalse(pkg2.hasSupplierPackage(pkg1))
            assertTrue(pkg2.hasSupplierPackage(pkg3))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg3))
            assertFalse(pkg1.hasSupplierPackage(pkg3))

            assertTrue(pkg2.hasConsumerPackage(pkg1))
            assertTrue(pkg3.hasConsumerPackage(pkg2))
            assertTrue(pkg3.hasTransitiveConsumerPackage(pkg1))
            assertFalse(pkg3.hasConsumerPackage(pkg1))

            assertTrue(pkg1.supplierPackages.contains(pkg2))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg2))
            assertTrue(pkg1.supplierPackageDependencies[0].supplier == pkg2)
            assertTrue(pkg2.consumerPackageDependencies[0].consumer == pkg1)
            assertFalse(pkg1.supplierPackages.contains(pkg3))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg3))

            assertTrue(pkg2.consumerPackages.contains(pkg1))
            assertTrue(pkg3.transitiveConsumerPackages.contains(pkg1))
            assertFalse(pkg3.consumerPackages.contains(pkg1))
            assertTrue(pkg2.consumerPackages.contains(pkg1))
            assertTrue(pkg2.transitiveConsumerPackages.contains(pkg1))
        }

    }

    @Test
    fun `Direct circular package dependencies are handled correctly`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg1 = model.makePackage(makeUuid(), "pkg1")
            val pkg2 = model.makePackage(makeUuid(), "pkg2")

            model.makePackageContainment(makeUuid(), root, pkg1)
            model.makePackageContainment(makeUuid(), root, pkg2)

            model.makePackageDependency(makeUuid(), pkg1, pkg2)
            model.makePackageDependency(makeUuid(), pkg2, pkg1)

            assertTrue(pkg1.hasTransitiveConsumerPackage(pkg2))
            assertTrue(pkg1.hasTransitiveConsumerPackage(pkg1))

            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg2))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg1))

            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg2))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg1))

            assertTrue(pkg1.transitiveConsumerPackages.contains(pkg2))
            assertTrue(pkg1.transitiveConsumerPackages.contains(pkg1))
        }

    }

    @Test
    fun `Indirect circular package dependencies are handled correctly`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg1 = model.makePackage(makeUuid(), "pkg1")
            val pkg2 = model.makePackage(makeUuid(), "pkg2")
            val pkg3 = model.makePackage(makeUuid(), "pkg3")

            model.makePackageContainment(makeUuid(), root, pkg1)
            model.makePackageContainment(makeUuid(), root, pkg2)
            model.makePackageContainment(makeUuid(), root, pkg3)

            model.makePackageDependency(makeUuid(), pkg1, pkg2)
            model.makePackageDependency(makeUuid(), pkg2, pkg3)
            model.makePackageDependency(makeUuid(), pkg3, pkg1)

            assertTrue(pkg1.hasTransitiveConsumerPackage(pkg2))
            assertTrue(pkg1.hasTransitiveConsumerPackage(pkg3))
            assertTrue(pkg1.hasTransitiveConsumerPackage(pkg1))

            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg2))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg3))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg1))

            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg2))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg3))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg1))

            assertTrue(pkg1.transitiveConsumerPackages.contains(pkg2))
            assertTrue(pkg1.transitiveConsumerPackages.contains(pkg3))
            assertTrue(pkg1.transitiveConsumerPackages.contains(pkg1))
        }

    }

}