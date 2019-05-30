//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api

import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.PackageContainment
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.Id
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding packages to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class PackageTests
    : SchemaGraphTests() {

    @Test
    fun `A graph can have packages added`() {

        lateinit var pkg1: Package
        lateinit var pkg2: Package
        lateinit var pkg3: Package
        lateinit var pkg4: Package

        fun check(m: Model) =
            with(m.graph) {
                assertEquals(5, numConcepts)
                assertEquals(0, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(m.rootPackage))
                assertTrue(containsConcept(pkg1))
                assertTrue(containsConcept(pkg2))
                assertTrue(containsConcept(pkg3))
                assertTrue(containsConcept(pkg4))
                assertEquals(m.rootPackage, concept(m.rootPackageId))
                assertEquals(pkg1, concept(pkg1.id))
                assertEquals(pkg2, concept(pkg2.id))
                assertEquals(pkg3, concept(pkg3.id))
                assertEquals(pkg4, concept(pkg4.id))
            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(Id(m.makeUuid()), false, "pkg1")
            pkg2 = Package(Id(m.makeUuid()), false, "pkg2")
            pkg3 = Package(Id(m.makeUuid()), false, "pkg3")
            pkg4 = Package(Id(m.makeUuid()), false, "pkg4")

            with(g) {
                addConcept(pkg1)
                addConcept(pkg2)
                addConcept(pkg3)
                addConcept(pkg4)
            }
        }

    }

    @Test
    fun `A graph can have packages with subpackages`() {

        lateinit var pkg1: Package
        lateinit var pkg1a: Package
        lateinit var pkg1b: Package

        fun check(m: Model) =
            with(m.graph) {
                assertEquals(4, numConcepts)
                assertEquals(3, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(pkg1))
                assertTrue(containsConcept(pkg1a))
                assertTrue(containsConcept(pkg1b))
                assertEquals(m.rootPackage, concept(m.rootPackageId))
                assertEquals(pkg1, concept(pkg1.id))
                assertEquals(pkg1a, concept(pkg1a.id))
                assertEquals(pkg1b, concept(pkg1b.id))
            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(Id(m.makeUuid()), false, "pkg1")
            pkg1a = Package(Id(m.makeUuid()), false, "pkg1a")
            pkg1b = Package(Id(m.makeUuid()), false, "pkg1b")

            with(g) {
                addConcept(pkg1)
                addConcept(pkg1a)
                addConcept(pkg1b)

                addConnection(PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg1.id))
                addConnection(PackageContainment(Id(m.makeUuid()), pkg1.id, pkg1a.id))
                addConnection(PackageContainment(Id(m.makeUuid()), pkg1.id, pkg1b.id))
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

