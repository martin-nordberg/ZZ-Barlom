//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api.concepts

import jvm.barlom.domain.graphschema.api.SchemaGraphTests
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.PackageContainment
import o.barlom.domain.graphschema.api.connections.PackageDependency
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.domain.graphschema.api.queries.*
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
    fun `Root elements of the model have fixed names`() {

        fun check(m: Model) =
            with(m) {
                assertEquals("Schema", rootPackage.name)
                assertEquals("RootConceptType", rootConceptType.name)
                assertEquals("RootDirectedConnectionType", rootDirectedConnectionType.name)
                assertEquals("RootUndirectedConnectionType", rootUndirectedConnectionType.name)
            }

        runWriteCheckTest(::check) { _, _ ->  }
    }

    @Test
    fun `A graph can have packages added`() {

        lateinit var pkg1: Package
        lateinit var pkg2: Package
        lateinit var pkg3: Package
        lateinit var pkg4: Package

        fun check(m: Model) =
            with(m.graph) {
                assertEquals(8, numConcepts)
                assertEquals(3, numConnections)
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

        lateinit var pkg1 : Package
        lateinit var pkg1a : Package
        lateinit var pkg1ai : Package
        lateinit var pkg1b : Package

        lateinit var containment1: PackageContainment
        lateinit var containment1a: PackageContainment
        lateinit var containment1ai: PackageContainment
        lateinit var containment1b: PackageContainment

        fun check(m: Model) =
            with(m) {
                with(graph) {
                    assertEquals(8, numConcepts)
                    assertEquals(7, numConnections)
                    assertTrue(containsConnection(containment1))
                    assertTrue(containsConnection(containment1a))
                    assertTrue(containsConnection(containment1ai))
                    assertTrue(containsConnection(containment1b))
                    assertEquals(containment1, connection(containment1.id))
                    assertEquals(containment1a, connection(containment1a.id))
                    assertEquals(containment1ai, connection(containment1ai.id))
                    assertEquals(containment1b, connection(containment1b.id))
                }

                assertEquals("Schema", path(rootPackage))
                assertEquals("pkg1", path(pkg1))
                assertEquals("pkg1.pkg1a", path(pkg1a))
                assertEquals("pkg1.pkg1a.pkg1ai", path(pkg1ai))
                assertEquals("pkg1.pkg1b", path(pkg1b))

                assertEquals(null, parentPackage(rootPackage))
                assertEquals(rootPackage, parentPackage(pkg1))
                assertEquals(pkg1, parentPackage(pkg1a))
                assertEquals(pkg1a, parentPackage(pkg1ai))
                assertEquals(pkg1, parentPackage(pkg1b))

                assertTrue(childPackages(rootPackage).contains(pkg1))
                assertTrue(childPackages(pkg1).contains(pkg1a))
                assertTrue(childPackages(pkg1a).contains(pkg1ai))
                assertTrue(childPackages(pkg1).contains(pkg1b))

                assertTrue(transitiveChildPackages(rootPackage).contains(pkg1))
                assertTrue(transitiveChildPackages(rootPackage).contains(pkg1a))
                assertTrue(transitiveChildPackages(rootPackage).contains(pkg1ai))
                assertTrue(transitiveChildPackages(rootPackage).contains(pkg1b))
                assertTrue(transitiveChildPackages(pkg1).contains(pkg1a))
                assertTrue(transitiveChildPackages(pkg1).contains(pkg1ai))
                assertTrue(transitiveChildPackages(pkg1a).contains(pkg1ai))
                assertTrue(transitiveChildPackages(pkg1).contains(pkg1b))

                assertTrue(hasChild(rootPackage, pkg1))
                assertTrue(hasChild(pkg1, pkg1a))
                assertTrue(hasChild(pkg1a, pkg1ai))
                assertTrue(hasChild(pkg1, pkg1b))
                assertFalse(hasChild(rootPackage, pkg1a))
                assertFalse(hasChild(pkg1, pkg1ai))
                assertFalse(hasChild(pkg1a, pkg1))

                assertTrue(hasTransitiveChild(rootPackage,pkg1))
                assertTrue(hasTransitiveChild(rootPackage,pkg1a))
                assertTrue(hasTransitiveChild(rootPackage,pkg1ai))
                assertTrue(hasTransitiveChild(rootPackage,pkg1b))
                assertTrue(hasTransitiveChild(pkg1,pkg1a))
                assertTrue(hasTransitiveChild(pkg1,pkg1ai))
                assertTrue(hasTransitiveChild(pkg1a,pkg1ai))
                assertTrue(hasTransitiveChild(pkg1,pkg1b))

                assertTrue(hasParent(pkg1, rootPackage))
                assertTrue(hasParent(pkg1a, pkg1))
                assertTrue(hasParent(pkg1ai, pkg1a))
                assertTrue(hasParent(pkg1b, pkg1))
                assertFalse(hasParent(pkg1a, rootPackage))
                assertFalse(hasParent(pkg1ai, pkg1))
                assertFalse(hasParent(pkg1, pkg1a))

                assertTrue(hasTransitiveParent(pkg1,rootPackage))
                assertTrue(hasTransitiveParent(pkg1a,rootPackage))
                assertTrue(hasTransitiveParent(pkg1ai,rootPackage))
                assertTrue(hasTransitiveParent(pkg1b,rootPackage))
                assertTrue(hasTransitiveParent(pkg1a,pkg1))
                assertTrue(hasTransitiveParent(pkg1ai,pkg1))
                assertTrue(hasTransitiveParent(pkg1ai,pkg1a))
                assertTrue(hasTransitiveParent(pkg1b,pkg1))
            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(Id(m.makeUuid()), false, "pkg1")
            pkg1a = Package(Id(m.makeUuid()), false, "pkg1a")
            pkg1ai = Package(Id(m.makeUuid()), false, "pkg1ai")
            pkg1b = Package(Id(m.makeUuid()), false, "pkg1b")

            containment1 = PackageContainment(Id(m.makeUuid()), m.rootPackageId, pkg1.id)
            containment1a = PackageContainment(Id(m.makeUuid()), pkg1.id, pkg1a.id, ESharing.NOT_SHARED)
            containment1ai = PackageContainment(Id(m.makeUuid()), pkg1a.id, pkg1ai.id, ESharing.NOT_SHARED)
            containment1b = PackageContainment(Id(m.makeUuid()), pkg1.id, pkg1b.id)

            with(g) {
                addConcept(pkg1)
                addConcept(pkg1a)
                addConcept(pkg1ai)
                addConcept(pkg1b)

                addConnection(containment1)
                addConnection(containment1a)
                addConnection(containment1ai)
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
                assertEquals(8, numConcepts)
                assertEquals(10, numConnections)
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

