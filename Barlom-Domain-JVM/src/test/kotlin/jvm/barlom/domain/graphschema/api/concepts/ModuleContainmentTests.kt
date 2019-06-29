//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api.concepts

import jvm.barlom.domain.graphschema.api.SchemaGraphTests
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.ModuleContainment
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.domain.graphschema.api.types.ESharing
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding modules to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class ModuleContainmentTests
    : SchemaGraphTests() {

    @Test
    fun `Root elements of the model have fixed names`() {

        fun check(m: Model) =
            with(m) {
                assertEquals("Schema", rootModule.name)
                assertEquals("RootConceptType", rootConceptType.name)
                assertEquals("RootDirectedConnectionType", rootDirectedConnectionType.name)
                assertEquals("RootUndirectedConnectionType", rootUndirectedConnectionType.name)
            }

        runWriteCheckTest(::check) { _, _ -> }
    }

    @Test
    fun `A graph can have modules added`() {

        lateinit var mod1: Module
        lateinit var mod2: Module
        lateinit var mod3: Module
        lateinit var mod4: Module

        fun check(m: Model) =
            with(m.graph) {
                assertEquals(8, numConcepts)
                assertEquals(3, numConnections)
                assertFalse(isEmpty())
                assertTrue(isNotEmpty())
                assertTrue(containsConcept(m.rootModule))
                assertTrue(containsConcept(mod1))
                assertTrue(containsConcept(mod2))
                assertTrue(containsConcept(mod3))
                assertTrue(containsConcept(mod4))
                assertEquals(m.rootModule, concept(m.rootModule.id))
                assertEquals(mod1, concept(mod1.id))
                assertEquals(mod2, concept(mod2.id))
                assertEquals(mod3, concept(mod3.id))
                assertEquals(mod4, concept(mod4.id))
            }

        runWriteCheckTest(::check) { mu ->
            with(mu) {
                mod1 = module("mod1")
                mod2 = module("mod2")
                mod3 = module("mod3")
                mod4 = module("mod4")
            }
        }

    }

    @Test
    fun `A graph can have modules with submodules`() {

        lateinit var mod1: Module
        lateinit var mod1a: Module
        lateinit var mod1ai: Module
        lateinit var mod1b: Module

        lateinit var containment1: ModuleContainment
        lateinit var containment1a: ModuleContainment
        lateinit var containment1ai: ModuleContainment
        lateinit var containment1b: ModuleContainment

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

                assertEquals("Schema", rootModule.path())
                assertEquals("mod1", mod1.path())
                assertEquals("mod1.mod1a", mod1a.path())
                assertEquals("mod1.mod1a.mod1ai", mod1ai.path())
                assertEquals("mod1.mod1b", mod1b.path())

                assertEquals(null, rootModule.parentModule())
                assertEquals(rootModule, mod1.parentModule())
                assertEquals(mod1, mod1a.parentModule())
                assertEquals(mod1a, mod1ai.parentModule())
                assertEquals(mod1, mod1b.parentModule())

                assertTrue(rootModule.childModules().contains(mod1))
                assertTrue(mod1.childModules().contains(mod1a))
                assertTrue(mod1a.childModules().contains(mod1ai))
                assertTrue(mod1.childModules().contains(mod1b))

                assertTrue(rootModule.transitiveChildModules().contains(mod1))
                assertTrue(rootModule.transitiveChildModules().contains(mod1a))
                assertTrue(rootModule.transitiveChildModules().contains(mod1ai))
                assertTrue(rootModule.transitiveChildModules().contains(mod1b))
                assertTrue(mod1.transitiveChildModules().contains(mod1a))
                assertTrue(mod1.transitiveChildModules().contains(mod1ai))
                assertTrue(mod1a.transitiveChildModules().contains(mod1ai))
                assertTrue(mod1.transitiveChildModules().contains(mod1b))

                assertTrue(rootModule.hasChild(mod1))
                assertTrue(mod1.hasChild(mod1a))
                assertTrue(mod1a.hasChild(mod1ai))
                assertTrue(mod1.hasChild(mod1b))
                assertFalse(rootModule.hasChild(mod1a))
                assertFalse(mod1.hasChild(mod1ai))
                assertFalse(mod1a.hasChild(mod1))

                assertTrue(rootModule.hasTransitiveChild(mod1))
                assertTrue(rootModule.hasTransitiveChild(mod1a))
                assertTrue(rootModule.hasTransitiveChild(mod1ai))
                assertTrue(rootModule.hasTransitiveChild(mod1b))
                assertTrue(mod1.hasTransitiveChild(mod1a))
                assertTrue(mod1.hasTransitiveChild(mod1ai))
                assertTrue(mod1a.hasTransitiveChild(mod1ai))
                assertTrue(mod1.hasTransitiveChild(mod1b))

                assertTrue(mod1.hasParent(rootModule))
                assertTrue(mod1a.hasParent(mod1))
                assertTrue(mod1ai.hasParent(mod1a))
                assertTrue(mod1b.hasParent(mod1))
                assertFalse(mod1a.hasParent(rootModule))
                assertFalse(mod1ai.hasParent(mod1))
                assertFalse(mod1.hasParent(mod1a))

                assertTrue(mod1.hasTransitiveParent(rootModule))
                assertTrue(mod1a.hasTransitiveParent(rootModule))
                assertTrue(mod1ai.hasTransitiveParent(rootModule))
                assertTrue(mod1b.hasTransitiveParent(rootModule))
                assertTrue(mod1a.hasTransitiveParent(mod1))
                assertTrue(mod1ai.hasTransitiveParent(mod1))
                assertTrue(mod1ai.hasTransitiveParent(mod1a))
                assertTrue(mod1b.hasTransitiveParent(mod1))
            }

        runWriteCheckTest(::check) { mu ->
            with(mu) {
                mod1 = module("mod1")
                mod1a = module("mod1a")
                mod1ai = module("mod1ai")
                mod1b = module("mod1b")

                containment1 = rootModule.contains(mod1)
                containment1a = mod1a.containedBy(mod1, ESharing.NOT_SHARED)
                containment1ai = mod1ai.containedBy(mod1a, ESharing.NOT_SHARED)
                containment1b = mod1.contains(mod1b)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

