//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api.concepts

import jvm.barlom.domain.graphschema.api.SchemaGraphTests
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.connections.ConceptTypeInheritance
import o.barlom.domain.graphschema.api.connections.Inheritance
import o.barlom.domain.graphschema.api.model.Model
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding concept types to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class ConceptTypeContainmentTests
    : SchemaGraphTests() {

    @Test
    fun `A graph can have concept types added to a package`() {

        lateinit var mod1: Module
        lateinit var ct1a: ConceptType
        lateinit var ct1b: ConceptType

        lateinit var ctc1a: ConceptTypeContainment
        lateinit var ctc1b: ConceptTypeContainment

        lateinit var cti1a: ConceptTypeInheritance
        lateinit var cti1b: ConceptTypeInheritance

        fun check(m: Model) =
            with(m) {
                with(graph) {
                    assertEquals(7, numConcepts)
                    assertEquals(8, numConnections)
                    assertFalse(isEmpty())
                    assertTrue(isNotEmpty())
                    assertTrue(containsConcept(m.rootModule))
                    assertTrue(containsConcept(m.rootConceptType))
                    assertTrue(containsConcept(mod1))
                    assertTrue(containsConcept(ct1a))
                    assertTrue(containsConcept(ct1b))
                    assertTrue(containsConnection(ctc1a))
                    assertTrue(containsConnection(ctc1b))
                    assertTrue(containsConnection(cti1a))
                    assertTrue(containsConnection(cti1b))
                    assertEquals(m.rootModule, concept(m.rootModule.id))
                    assertEquals(m.rootConceptType, concept(m.rootConceptType.id))
                    assertEquals(mod1, concept(mod1.id))
                    assertEquals(ct1a, concept(ct1a.id))
                    assertEquals(ct1b, concept(ct1b.id))
                    assertEquals(ctc1a, connection(ctc1a.id))
                    assertEquals(ctc1b, connection(ctc1b.id))
                    assertEquals(cti1a, connection(cti1a.id))
                    assertEquals(cti1b, connection(cti1b.id))
                }

                assertTrue(mod1.childConceptTypes().contains(ct1a))
                assertTrue(mod1.childConceptTypes().contains(ct1b))

                assertEquals("mod1.ConceptType1a", ct1a.path())
                assertEquals("mod1.ConceptType1b", ct1b.path())

                assertTrue(mod1.hasChild(ct1a))
                assertTrue(mod1.hasChild(ct1b))

                assertTrue(ct1a.hasParent(mod1))
                assertTrue(ct1b.hasParent(mod1))

                assertEquals(mod1, ct1a.parentModule())
                assertEquals(mod1, ct1b.parentModule())
            }

        runWriteCheckTest(::check) { mu ->
            with(mu) {
                mod1 = module("mod1")
                mod1.containedBy(rootModule)
                ct1a = conceptType("ConceptType1a")
                ct1b = conceptType("ConceptType1b")
                ctc1a = ct1a.containedBy(mod1)
                ctc1b = mod1.contains(ct1b)
                cti1a =
                    ConceptTypeInheritance(Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE, makeUuid(), ct1a, rootConceptType)
                cti1b =
                    ConceptTypeInheritance(Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE, makeUuid(), ct1b, rootConceptType)

                with(graph) {
                    addConnection(cti1a)
                    addConnection(cti1b)
                }
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

