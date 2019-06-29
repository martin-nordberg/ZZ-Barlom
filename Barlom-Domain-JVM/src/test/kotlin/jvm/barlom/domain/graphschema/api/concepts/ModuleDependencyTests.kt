//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api.concepts

import jvm.barlom.domain.graphschema.api.SchemaGraphTests
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.Dependency
import o.barlom.domain.graphschema.api.connections.ModuleDependency
import o.barlom.domain.graphschema.api.model.Model
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding modules to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class ModuleDependencyTests
    : SchemaGraphTests() {

    @Test
    fun `A graph can establish module dependencies`() {

        lateinit var mod1: Module
        lateinit var mod2a: Module
        lateinit var mod2b: Module
        lateinit var mod3: Module
        lateinit var mod4: Module

        lateinit var dep12a: ModuleDependency
        lateinit var dep12b: ModuleDependency
        lateinit var dep2a3: ModuleDependency
        lateinit var dep2b3: ModuleDependency
        lateinit var dep34: ModuleDependency

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

                assertTrue(mod1.consumers().isEmpty())
                assertEquals(1, mod2a.consumers().size)
                assertTrue(mod2a.consumers().contains(mod1))
                assertEquals(1, mod2b.consumers().size)
                assertTrue(mod2b.consumers().contains(mod1))
                assertEquals(2, mod3.consumers().size)
                assertTrue(mod3.consumers().contains(mod2a))
                assertTrue(mod3.consumers().contains(mod2b))
                assertEquals(1, mod4.consumers().size)
                assertTrue(mod4.consumers().contains(mod3))

                assertTrue(mod1.transitiveConsumers().isEmpty())
                assertEquals(1, mod2a.transitiveConsumers().size)
                assertTrue(mod2a.transitiveConsumers().contains(mod1))
                assertEquals(1, mod2b.transitiveConsumers().size)
                assertTrue(mod2b.transitiveConsumers().contains(mod1))
                assertEquals(3, mod3.transitiveConsumers().size)
                assertTrue(mod3.transitiveConsumers().contains(mod1))
                assertTrue(mod3.transitiveConsumers().contains(mod2a))
                assertTrue(mod3.transitiveConsumers().contains(mod2b))
                assertEquals(4, mod4.transitiveConsumers().size)
                assertTrue(mod4.transitiveConsumers().contains(mod1))
                assertTrue(mod4.transitiveConsumers().contains(mod2a))
                assertTrue(mod4.transitiveConsumers().contains(mod2b))
                assertTrue(mod4.transitiveConsumers().contains(mod3))

                assertTrue(mod2a.hasConsumer(mod1))
                assertTrue(mod2b.hasConsumer(mod1))
                assertTrue(mod3.hasConsumer(mod2a))
                assertTrue(mod3.hasConsumer(mod2b))
                assertTrue(mod4.hasConsumer(mod3))

                assertTrue(mod2a.hasTransitiveConsumer(mod1))
                assertTrue(mod2b.hasTransitiveConsumer(mod1))
                assertTrue(mod3.hasTransitiveConsumer(mod2a))
                assertTrue(mod3.hasTransitiveConsumer(mod2b))
                assertTrue(mod3.hasTransitiveConsumer(mod1))
                assertTrue(mod4.hasTransitiveConsumer(mod3))
                assertTrue(mod4.hasTransitiveConsumer(mod2a))
                assertTrue(mod4.hasTransitiveConsumer(mod2b))
                assertTrue(mod4.hasTransitiveConsumer(mod1))

                assertTrue(mod4.suppliers().isEmpty())
                assertEquals(1, mod3.suppliers().size)
                assertTrue(mod3.suppliers().contains(mod4))
                assertEquals(1, mod2a.suppliers().size)
                assertTrue(mod2a.suppliers().contains(mod3))
                assertEquals(1, mod2b.suppliers().size)
                assertTrue(mod2b.suppliers().contains(mod3))
                assertEquals(2, mod1.suppliers().size)
                assertTrue(mod1.suppliers().contains(mod2a))
                assertTrue(mod1.suppliers().contains(mod2b))

                assertTrue(mod4.transitiveSuppliers().isEmpty())
                assertEquals(1, mod3.transitiveSuppliers().size)
                assertTrue(mod3.transitiveSuppliers().contains(mod4))
                assertEquals(2, mod2a.transitiveSuppliers().size)
                assertTrue(mod2a.transitiveSuppliers().contains(mod3))
                assertTrue(mod2a.transitiveSuppliers().contains(mod4))
                assertEquals(2, mod2b.transitiveSuppliers().size)
                assertTrue(mod2b.transitiveSuppliers().contains(mod3))
                assertTrue(mod2b.transitiveSuppliers().contains(mod4))
                assertEquals(4, mod1.transitiveSuppliers().size)
                assertTrue(mod1.transitiveSuppliers().contains(mod2a))
                assertTrue(mod1.transitiveSuppliers().contains(mod2b))
                assertTrue(mod1.transitiveSuppliers().contains(mod3))
                assertTrue(mod1.transitiveSuppliers().contains(mod3))

                assertTrue(mod3.hasSupplier(mod4))
                assertTrue(mod2a.hasSupplier(mod3))
                assertTrue(mod2b.hasSupplier(mod3))
                assertTrue(mod1.hasSupplier(mod2a))
                assertTrue(mod1.hasSupplier(mod2b))

                assertTrue(mod3.hasTransitiveSupplier(mod4))
                assertTrue(mod2a.hasTransitiveSupplier(mod3))
                assertTrue(mod2a.hasTransitiveSupplier(mod4))
                assertTrue(mod2b.hasTransitiveSupplier(mod3))
                assertTrue(mod2b.hasTransitiveSupplier(mod4))
                assertTrue(mod1.hasTransitiveSupplier(mod2a))
                assertTrue(mod1.hasTransitiveSupplier(mod2b))
                assertTrue(mod1.hasTransitiveSupplier(mod3))
                assertTrue(mod1.hasTransitiveSupplier(mod4))
            }

        runWriteCheckTest(::check) { mu ->
            with(mu) {
                mod1 = module("mod1")
                mod2a = module("mod2a")
                mod2b = module("mod2b")
                mod3 = module("mod3")
                mod4 = module("mod4")
                mod1.containedBy(rootModule)
                mod2a.containedBy(rootModule)
                mod2b.containedBy(rootModule)
                mod3.containedBy(rootModule)
                mod4.containedBy(rootModule)

                with(graph) {
                    dep12a = ModuleDependency(Dependency.MODULE_DEPENDENCY_TYPE, makeUuid(), mod1, mod2a)
                    dep12b = ModuleDependency(Dependency.MODULE_DEPENDENCY_TYPE, makeUuid(), mod1, mod2b)
                    dep2a3 = ModuleDependency(Dependency.MODULE_DEPENDENCY_TYPE, makeUuid(), mod2a, mod3)
                    dep2b3 = ModuleDependency(Dependency.MODULE_DEPENDENCY_TYPE, makeUuid(), mod2b, mod3)
                    dep34 = ModuleDependency(Dependency.MODULE_DEPENDENCY_TYPE, makeUuid(), mod3, mod4)

                    addConnection(dep12a)
                    addConnection(dep12b)
                    addConnection(dep2a3)
                    addConnection(dep2b3)
                    addConnection(dep34)
                }
            }
        }

    }

    // TODO: circular package dependencies

}

//---------------------------------------------------------------------------------------------------------------------

