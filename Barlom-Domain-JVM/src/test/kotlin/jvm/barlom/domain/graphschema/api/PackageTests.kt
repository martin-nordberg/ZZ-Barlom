//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api

import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.PackageContainment
import o.barlom.domain.graphschema.api.connections.PackageDependency
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.domain.graphschema.api.types.ESharing
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
                assertEquals(6, numConcepts)
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

        lateinit var containment1: PackageContainment
        lateinit var containment1a: PackageContainment
        lateinit var containment1b: PackageContainment

        fun check(m: Model) =
            with(m.graph) {
                assertEquals(5, numConcepts)
                assertEquals(3, numConnections)
                assertTrue(containsConnection(containment1))
                assertTrue(containsConnection(containment1a))
                assertTrue(containsConnection(containment1b))
                assertEquals(containment1, connection(containment1.id))
                assertEquals(containment1a, connection(containment1a.id))
                assertEquals(containment1b, connection(containment1b.id))
            }

        runWriteCheckTest(::check) { m, g ->
            val pkg1 = Package(Id(m.makeUuid()), false, "pkg1")
            val pkg1a = Package(Id(m.makeUuid()), false, "pkg1a")
            val pkg1b = Package(Id(m.makeUuid()), false, "pkg1b")

            containment1 = PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg1.id)
            containment1a = PackageContainment(Id(m.makeUuid()), pkg1.id, pkg1a.id, ESharing.NOT_SHARED)
            containment1b = PackageContainment(Id(m.makeUuid()), pkg1.id, pkg1b.id)

            with(g) {
                addConcept(pkg1)
                addConcept(pkg1a)
                addConcept(pkg1b)

                addConnection(containment1)
                addConnection(containment1a)
                addConnection(containment1b)
            }
        }

    }

    @Test
    fun `A graph can establish package dependencies`() {

        lateinit var dep12: PackageDependency
        lateinit var dep23: PackageDependency
        lateinit var dep34: PackageDependency

        fun check(m: Model) =
            with(m.graph) {
                assertEquals(6, numConcepts)
                assertEquals(7, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConnection(dep12))
                assertTrue(containsConnection(dep23))
                assertTrue(containsConnection(dep34))
                assertEquals(dep12, connection(dep12.id))
                assertEquals(dep23, connection(dep23.id))
                assertEquals(dep34, connection(dep34.id))
                assertEquals(dep12, connection(dep12.id))
                assertEquals(dep23, connection(dep23.id))
                assertEquals(dep34, connection(dep34.id))
            }

        runWriteCheckTest(::check) { m, g ->
            val pkg1 = Package(Id(m.makeUuid()), false, "pkg1")
            val pkg2 = Package(Id(m.makeUuid()), false, "pkg2")
            val pkg3 = Package(Id(m.makeUuid()), false, "pkg3")
            val pkg4 = Package(Id(m.makeUuid()), false, "pkg4")

            with(g) {
                addConcept(pkg1)
                addConcept(pkg2)
                addConcept(pkg3)
                addConcept(pkg4)

                addConnection(PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg1.id))
                addConnection(PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg2.id))
                addConnection(PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg3.id))
                addConnection(PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg4.id))

                dep12 = PackageDependency(Id(m.makeUuid()), pkg1.id, pkg2.id)
                dep23 = PackageDependency(Id(m.makeUuid()), pkg2.id, pkg3.id)
                dep34 = PackageDependency(Id(m.makeUuid()), pkg3.id, pkg4.id)

                addConnection(dep12)
                addConnection(dep23)
                addConnection(dep34)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

