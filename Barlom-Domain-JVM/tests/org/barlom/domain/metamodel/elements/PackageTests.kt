//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.impl.vertices.Package
import org.barlom.domain.metamodel.impl.edges.PackageDependency
import org.barlom.domain.metamodel.impl.vertices.RootPackage
import org.barlom.domain.metamodel.withRevHistory
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

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy( root ) }
            val subpkg = Package(makeUuid(), "subpkg") { containedBy( pkg ) }

            assertEquals("pkg", pkg.path)
            assertEquals("pkg.subpkg", subpkg.path)
        }

    }

    @Test
    fun `Packages track their children and their parent and grandparents`() {

        withRevHistory {
            val root = RootPackage()
            val pkg = Package(makeUuid(), "pkg") { containedBy(root) }
            val subpkg = Package(makeUuid(), "subpkg") { containedBy(pkg) }

            assertEquals(root, pkg.parentPackage)
            assertEquals(pkg, subpkg.parentPackage)

            assertTrue(root.childPackages.contains(pkg))
            assertTrue(pkg.childPackages.contains(subpkg))
            assertFalse(root.childPackages.contains(subpkg))

            assertTrue(pkg.isChildOf(root))
            assertTrue(subpkg.isChildOf(root))
            assertTrue(subpkg.isChildOf(pkg))
        }

    }

    @Test
    fun `Packages track their adjacent and transitive dependencies`() {

        withRevHistory {
            val root = RootPackage()
            val pkg1 = Package(makeUuid(), "pkg1") { containedBy(root) }
            val pkg2 = Package(makeUuid(), "pkg2") { containedBy(root) }
            val pkg3 = Package(makeUuid(), "pkg3") { containedBy(root) }

            PackageDependency(makeUuid(), pkg1, pkg2)
            PackageDependency(makeUuid(), pkg2, pkg3)

            assertTrue(pkg1.hasSupplierPackage(pkg2))
            assertFalse(pkg2.hasSupplierPackage(pkg1))
            assertTrue(pkg2.hasSupplierPackage(pkg3))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg3))
            assertFalse(pkg1.hasSupplierPackage(pkg3))

            assertTrue(pkg2.hasClientPackage(pkg1))
            assertTrue(pkg3.hasClientPackage(pkg2))
            assertTrue(pkg3.hasTransitiveClientPackage(pkg1))
            assertFalse(pkg3.hasClientPackage(pkg1))

            assertTrue(pkg1.supplierPackages.contains(pkg2))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg2))
            assertFalse(pkg1.supplierPackages.contains(pkg3))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg3))

            assertTrue(pkg2.clientPackages.contains(pkg1))
            assertTrue(pkg3.transitiveClientPackages.contains(pkg1))
            assertFalse(pkg3.clientPackages.contains(pkg1))
            assertTrue(pkg2.clientPackages.contains(pkg1))
            assertTrue(pkg2.transitiveClientPackages.contains(pkg1))
        }

    }

    @Test
    fun `Direct circular package dependencies are handled correctly`() {

        withRevHistory {
            val root = RootPackage()
            val pkg1 = Package(makeUuid(), "pkg1") { containedBy(root) }
            val pkg2 = Package(makeUuid(), "pkg2") { containedBy(root) }

            PackageDependency(makeUuid(), pkg1, pkg2)
            PackageDependency(makeUuid(), pkg2, pkg1)

            assertTrue(pkg1.hasTransitiveClientPackage(pkg2))
            assertTrue(pkg1.hasTransitiveClientPackage(pkg1))

            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg2))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg1))

            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg2))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg1))

            assertTrue(pkg1.transitiveClientPackages.contains(pkg2))
            assertTrue(pkg1.transitiveClientPackages.contains(pkg1))
        }

    }

    @Test
    fun `Indirect circular package dependencies are handled correctly`() {

        withRevHistory {
            val root = RootPackage()
            val pkg1 = Package(makeUuid(), "pkg1") { containedBy(root) }
            val pkg2 = Package(makeUuid(), "pkg2") { containedBy(root) }
            val pkg3 = Package(makeUuid(), "pkg3") { containedBy(root) }

            PackageDependency(makeUuid(), pkg1, pkg2)
            PackageDependency(makeUuid(), pkg2, pkg3)
            PackageDependency(makeUuid(), pkg3, pkg1)

            assertTrue(pkg1.hasTransitiveClientPackage(pkg2))
            assertTrue(pkg1.hasTransitiveClientPackage(pkg3))
            assertTrue(pkg1.hasTransitiveClientPackage(pkg1))

            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg2))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg3))
            assertTrue(pkg1.hasTransitiveSupplierPackage(pkg1))

            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg2))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg3))
            assertTrue(pkg1.transitiveSupplierPackages.contains(pkg1))

            assertTrue(pkg1.transitiveClientPackages.contains(pkg2))
            assertTrue(pkg1.transitiveClientPackages.contains(pkg3))
            assertTrue(pkg1.transitiveClientPackages.contains(pkg1))
        }

    }

}
