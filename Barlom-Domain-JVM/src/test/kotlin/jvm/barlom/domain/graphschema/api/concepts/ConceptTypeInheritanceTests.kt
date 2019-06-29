//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api.concepts

import jvm.barlom.domain.graphschema.api.SchemaGraphTests
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.ConceptTypeInheritance
import o.barlom.domain.graphschema.api.connections.Inheritance
import o.barlom.domain.graphschema.api.model.Model
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding concept types with inheritance to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class ConceptTypeInheritanceTests
    : SchemaGraphTests() {

    @Test
    fun `Concept types can be connected by inheritance`() {

        lateinit var mod1: Module
        lateinit var ct1a: ConceptType
        lateinit var ct1b: ConceptType
        lateinit var ct2: ConceptType
        lateinit var ct3: ConceptType

        lateinit var cti21a: ConceptTypeInheritance
        lateinit var cti21b: ConceptTypeInheritance
        lateinit var cti32: ConceptTypeInheritance

        fun check(m: Model) =
            with(m) {
                with(graph) {
                    assertEquals(9, numConcepts)
                    assertEquals(11, numConnections)
                    assertFalse(isEmpty())
                    assertTrue(isNotEmpty())
                    assertTrue(containsConcept(m.rootModule))
                    assertTrue(containsConcept(m.rootConceptType))
                    assertTrue(containsConcept(mod1))
                    assertTrue(containsConcept(ct1a))
                    assertTrue(containsConcept(ct1b))
                    assertTrue(containsConcept(ct2))
                    assertTrue(containsConcept(ct3))
                    assertTrue(containsConnection(cti21a))
                    assertTrue(containsConnection(cti21b))
                    assertTrue(containsConnection(cti32))
                    assertEquals(cti21a, connection(cti21a.id))
                    assertEquals(cti21b, connection(cti21b.id))
                    assertEquals(cti32, connection(cti32.id))
                }

                assertTrue(mod1.childConceptTypes().contains(ct1a))
                assertTrue(mod1.childConceptTypes().contains(ct1b))
                assertTrue(mod1.childConceptTypes().contains(ct2))
                assertTrue(mod1.childConceptTypes().contains(ct3))

                assertEquals(1, ct1a.subTypes().size)
                assertTrue(ct1a.subTypes().contains(ct2))
                assertEquals(1, ct1b.subTypes().size)
                assertTrue(ct1b.subTypes().contains(ct2))
                assertEquals(1, ct2.subTypes().size)
                assertTrue(ct2.subTypes().contains(ct3))
                assertTrue(ct3.subTypes().isEmpty())

                assertTrue(ct1a.hasSubType(ct2))
                assertTrue(ct1b.hasSubType(ct2))
                assertTrue(ct2.hasSubType(ct3))

                assertEquals(2, ct1a.transitiveSubTypes().size)
                assertTrue(ct1a.transitiveSubTypes().contains(ct2))
                assertTrue(ct1a.transitiveSubTypes().contains(ct3))
                assertEquals(2, ct1b.transitiveSubTypes().size)
                assertTrue(ct1b.transitiveSubTypes().contains(ct2))
                assertTrue(ct1b.transitiveSubTypes().contains(ct3))
                assertEquals(1, ct2.transitiveSubTypes().size)
                assertTrue(ct2.transitiveSubTypes().contains(ct3))
                assertTrue(ct3.transitiveSubTypes().isEmpty())

                assertTrue(ct1a.hasTransitiveSubType(ct2))
                assertTrue(ct1a.hasTransitiveSubType(ct3))
                assertTrue(ct1b.hasTransitiveSubType(ct2))
                assertTrue(ct1b.hasTransitiveSubType(ct3))
                assertTrue(ct2.hasTransitiveSubType(ct3))

                assertTrue(ct1a.superTypes().isEmpty())
                assertTrue(ct1b.superTypes().isEmpty())
                assertEquals(2, ct2.superTypes().size)
                assertTrue(ct2.superTypes().contains(ct1a))
                assertTrue(ct2.superTypes().contains(ct1b))
                assertEquals(1, ct3.superTypes().size)
                assertTrue(ct3.superTypes().contains(ct2))

                assertTrue(ct2.hasSuperType(ct1a))
                assertTrue(ct2.hasSuperType(ct1b))
                assertTrue(ct3.hasSuperType(ct2))

                assertTrue(ct1a.transitiveSuperTypes().isEmpty())
                assertTrue(ct1b.transitiveSuperTypes().isEmpty())
                assertEquals(2, ct2.transitiveSuperTypes().size)
                assertTrue(ct2.transitiveSuperTypes().contains(ct1a))
                assertTrue(ct2.transitiveSuperTypes().contains(ct1b))
                assertEquals(3, ct3.transitiveSuperTypes().size)
                assertTrue(ct3.transitiveSuperTypes().contains(ct2))
                assertTrue(ct3.transitiveSuperTypes().contains(ct1a))
                assertTrue(ct3.transitiveSuperTypes().contains(ct1b))

                assertTrue(ct2.hasTransitiveSuperType(ct1a))
                assertTrue(ct2.hasTransitiveSuperType(ct1b))
                assertTrue(ct3.hasTransitiveSuperType(ct2))
                assertTrue(ct3.hasTransitiveSuperType(ct1a))
                assertTrue(ct3.hasTransitiveSuperType(ct1b))

            }

        runWriteCheckTest(::check) { mu ->
            with(mu) {
                mod1 = module("mod1")
                mod1.containedBy(rootModule)
                ct1a = conceptType("ConceptType1a")
                ct1b = conceptType("ConceptType1b")
                ct2 = conceptType("ConceptType2")
                ct3 = conceptType("ConceptType3")
                ct1a.containedBy(mod1)
                ct1b.containedBy(mod1)
                ct2.containedBy(mod1)
                ct3.containedBy(mod1)
                // TODO: inherit from root concept type
                cti21a = ConceptTypeInheritance(Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE, makeUuid(), ct2, ct1a)
                cti21b = ConceptTypeInheritance(Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE, makeUuid(), ct2, ct1b)
                cti32 = ConceptTypeInheritance(Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE, makeUuid(), ct3, ct2)

                with(graph) {
                    addConnection(cti21a)
                    addConnection(cti21b)
                    addConnection(cti32)
                }
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

