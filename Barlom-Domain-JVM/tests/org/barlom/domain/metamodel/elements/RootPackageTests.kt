//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

import org.barlom.domain.metamodel.impl.elements.Package
import org.barlom.domain.metamodel.impl.elements.RootPackage
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests of RootPackage.
 */
class RootPackageTests {

    @Test
    fun `Root packages have the same default ID`() {

        val pkg1 = RootPackage()
        val pkg2 = RootPackage()

        assertNotNull(pkg1.id)
        assertEquals(pkg1.id, pkg2.id)
    }

    @Test
    fun `A root package has one contained vertex type`() {

        val pkg = RootPackage()

        assertEquals(1, pkg.vertexTypes.size)
        assertTrue(pkg.vertexTypes.contains(pkg.rootVertexType))

    }

    @Test
    fun `A root package has one contained directed edge type`() {

        val pkg = RootPackage()

        assertEquals(1, pkg.directedEdgeTypes.size)
        assertTrue(pkg.directedEdgeTypes.contains(pkg.rootDirectedEdgeType))

    }

    @Test
    fun `A root package has one contained undirected edge type`() {

        val pkg = RootPackage()

        assertEquals(1, pkg.undirectedEdgeTypes.size)
        assertTrue(pkg.undirectedEdgeTypes.contains(pkg.rootUndirectedEdgeType))

    }

    @Test
    fun `A root package has no name`() {

        val pkg = RootPackage()

        assertFailsWith<UnsupportedOperationException> {
            pkg.name
        }

    }

    @Test
    fun `A root package has an empty path`() {

        val pkg = RootPackage()

        assertEquals("", pkg.path)

    }

    @Test
    fun `A root package holds child packages`() {

        val pkg = RootPackage()

        val subpkg1 = Package(makeUuid(), "subpkg1", pkg)

        assertEquals(1, pkg.childPackages.size)
        assertTrue(pkg.childPackages.contains(subpkg1))

        val subpkg2 = Package(makeUuid(), "subpkg2", pkg)

        assertEquals(2, pkg.childPackages.size)
        assertTrue(pkg.childPackages.contains(subpkg1))
        assertTrue(pkg.childPackages.contains(subpkg2))

        assertTrue(subpkg1.isChildOf(pkg))
        assertTrue(subpkg2.isChildOf(pkg))

    }

}
