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
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding packages to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class PackageDependencyTests
    : SchemaGraphTests() {

    @Test
    fun `A graph can establish package dependencies`() {

        lateinit var pkg1 : Package
        lateinit var pkg2a : Package
        lateinit var pkg2b : Package
        lateinit var pkg3 : Package
        lateinit var pkg4 : Package

        lateinit var dep12a: PackageDependency
        lateinit var dep12b: PackageDependency
        lateinit var dep2a3: PackageDependency
        lateinit var dep2b3: PackageDependency
        lateinit var dep34: PackageDependency

        fun check(m: Model) =
            with(m) {
                with(graph) {
                    assertEquals(9, numConcepts)
                    assertEquals(13, numConnections)
                    assertFalse(isEmpty())
                    assertTrue(isNotEmpty())
                    assertTrue(containsConnection(dep12a))
                    assertTrue(containsConnection(dep12b))
                    assertTrue(containsConnection(dep2a3))
                    assertTrue(containsConnection(dep2b3))
                    assertTrue(containsConnection(dep34))
                    assertEquals(dep12a, connection(dep12a.id))
                    assertEquals(dep12b, connection(dep12b.id))
                    assertEquals(dep2a3, connection(dep2a3.id))
                    assertEquals(dep2b3, connection(dep2b3.id))
                    assertEquals(dep34, connection(dep34.id))
                }

                assertTrue(consumerPackages(pkg1).isEmpty())
                assertEquals(1,consumerPackages(pkg2a).size)
                assertTrue(consumerPackages(pkg2a).contains(pkg1))
                assertEquals(1,consumerPackages(pkg2b).size)
                assertTrue(consumerPackages(pkg2b).contains(pkg1))
                assertEquals(2,consumerPackages(pkg3).size)
                assertTrue(consumerPackages(pkg3).contains(pkg2a))
                assertTrue(consumerPackages(pkg3).contains(pkg2b))
                assertEquals(1,consumerPackages(pkg4).size)
                assertTrue(consumerPackages(pkg4).contains(pkg3))

                assertTrue(transitiveConsumerPackages(pkg1).isEmpty())
                assertEquals(1,transitiveConsumerPackages(pkg2a).size)
                assertTrue(transitiveConsumerPackages(pkg2a).contains(pkg1))
                assertEquals(1,transitiveConsumerPackages(pkg2b).size)
                assertTrue(transitiveConsumerPackages(pkg2b).contains(pkg1))
                assertEquals(3,transitiveConsumerPackages(pkg3).size)
                assertTrue(transitiveConsumerPackages(pkg3).contains(pkg1))
                assertTrue(transitiveConsumerPackages(pkg3).contains(pkg2a))
                assertTrue(transitiveConsumerPackages(pkg3).contains(pkg2b))
                assertEquals(4,transitiveConsumerPackages(pkg4).size)
                assertTrue(transitiveConsumerPackages(pkg4).contains(pkg1))
                assertTrue(transitiveConsumerPackages(pkg4).contains(pkg2a))
                assertTrue(transitiveConsumerPackages(pkg4).contains(pkg2b))
                assertTrue(transitiveConsumerPackages(pkg4).contains(pkg3))

                assertTrue(hasConsumer(pkg2a,pkg1))
                assertTrue(hasConsumer(pkg2b,pkg1))
                assertTrue(hasConsumer(pkg3,pkg2a))
                assertTrue(hasConsumer(pkg3,pkg2b))
                assertTrue(hasConsumer(pkg4,pkg3))

                assertTrue(hasTransitiveConsumer(pkg2a,pkg1))
                assertTrue(hasTransitiveConsumer(pkg2b,pkg1))
                assertTrue(hasTransitiveConsumer(pkg3,pkg2a))
                assertTrue(hasTransitiveConsumer(pkg3,pkg2b))
                assertTrue(hasTransitiveConsumer(pkg3,pkg1))
                assertTrue(hasTransitiveConsumer(pkg4,pkg3))
                assertTrue(hasTransitiveConsumer(pkg4,pkg2a))
                assertTrue(hasTransitiveConsumer(pkg4,pkg2b))
                assertTrue(hasTransitiveConsumer(pkg4,pkg1))

                assertTrue(supplierPackages(pkg4).isEmpty())
                assertEquals(1,supplierPackages(pkg3).size)
                assertTrue(supplierPackages(pkg3).contains(pkg4))
                assertEquals(1,supplierPackages(pkg2a).size)
                assertTrue(supplierPackages(pkg2a).contains(pkg3))
                assertEquals(1,supplierPackages(pkg2b).size)
                assertTrue(supplierPackages(pkg2b).contains(pkg3))
                assertEquals(2,supplierPackages(pkg1).size)
                assertTrue(supplierPackages(pkg1).contains(pkg2a))
                assertTrue(supplierPackages(pkg1).contains(pkg2b))

                assertTrue(transitiveSupplierPackages(pkg4).isEmpty())
                assertEquals(1,transitiveSupplierPackages(pkg3).size)
                assertTrue(transitiveSupplierPackages(pkg3).contains(pkg4))
                assertEquals(2,transitiveSupplierPackages(pkg2a).size)
                assertTrue(transitiveSupplierPackages(pkg2a).contains(pkg3))
                assertTrue(transitiveSupplierPackages(pkg2a).contains(pkg4))
                assertEquals(2,transitiveSupplierPackages(pkg2b).size)
                assertTrue(transitiveSupplierPackages(pkg2b).contains(pkg3))
                assertTrue(transitiveSupplierPackages(pkg2b).contains(pkg4))
                assertEquals(4,transitiveSupplierPackages(pkg1).size)
                assertTrue(transitiveSupplierPackages(pkg1).contains(pkg2a))
                assertTrue(transitiveSupplierPackages(pkg1).contains(pkg2b))
                assertTrue(transitiveSupplierPackages(pkg1).contains(pkg3))
                assertTrue(transitiveSupplierPackages(pkg1).contains(pkg3))

                assertTrue(hasSupplier(pkg3,pkg4))
                assertTrue(hasSupplier(pkg2a,pkg3))
                assertTrue(hasSupplier(pkg2b,pkg3))
                assertTrue(hasSupplier(pkg1,pkg2a))
                assertTrue(hasSupplier(pkg1,pkg2b))

                assertTrue(hasTransitiveSupplier(pkg3,pkg4))
                assertTrue(hasTransitiveSupplier(pkg2a,pkg3))
                assertTrue(hasTransitiveSupplier(pkg2a,pkg4))
                assertTrue(hasTransitiveSupplier(pkg2b,pkg3))
                assertTrue(hasTransitiveSupplier(pkg2b,pkg4))
                assertTrue(hasTransitiveSupplier(pkg1,pkg2a))
                assertTrue(hasTransitiveSupplier(pkg1,pkg2b))
                assertTrue(hasTransitiveSupplier(pkg1,pkg3))
                assertTrue(hasTransitiveSupplier(pkg1,pkg4))
            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(m.makeUuid(), false, "pkg1")
            pkg2a = Package(m.makeUuid(), false, "pkg2a")
            pkg2b = Package(m.makeUuid(), false, "pkg2b")
            pkg3 = Package(m.makeUuid(), false, "pkg3")
            pkg4 = Package(m.makeUuid(), false, "pkg4")

            with(g) {
                addConcept(pkg1)
                addConcept(pkg2a)
                addConcept(pkg2b)
                addConcept(pkg3)
                addConcept(pkg4)

                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg1))
                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg2a))
                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg2b))
                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg3))
                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg4))

                dep12a = PackageDependency(m.makeUuid(), pkg1, pkg2a)
                dep12b = PackageDependency(m.makeUuid(), pkg1, pkg2b)
                dep2a3 = PackageDependency(m.makeUuid(), pkg2a, pkg3)
                dep2b3 = PackageDependency(m.makeUuid(), pkg2b, pkg3)
                dep34 = PackageDependency(m.makeUuid(), pkg3, pkg4)

                addConnection(dep12a)
                addConnection(dep12b)
                addConnection(dep2a3)
                addConnection(dep2b3)
                addConnection(dep34)
            }
        }

    }

    // TODO: circular package dependencies

}

//---------------------------------------------------------------------------------------------------------------------

