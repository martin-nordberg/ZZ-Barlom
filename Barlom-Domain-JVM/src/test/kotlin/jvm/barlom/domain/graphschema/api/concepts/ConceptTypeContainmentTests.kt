//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api.concepts

import jvm.barlom.domain.graphschema.api.SchemaGraphTests
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.connections.ConceptTypeInheritance
import o.barlom.domain.graphschema.api.connections.PackageContainment
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.domain.graphschema.api.queries.childConceptTypes
import o.barlom.domain.graphschema.api.queries.hasChild
import o.barlom.domain.graphschema.api.queries.hasParent
import o.barlom.domain.graphschema.api.queries.parentPackage
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

        lateinit var pkg1: Package
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
                    assertTrue(containsConcept(m.rootPackage))
                    assertTrue(containsConcept(m.rootConceptType))
                    assertTrue(containsConcept(pkg1))
                    assertTrue(containsConcept(ct1a))
                    assertTrue(containsConcept(ct1b))
                    assertTrue(containsConnection(ctc1a))
                    assertTrue(containsConnection(ctc1b))
                    assertTrue(containsConnection(cti1a))
                    assertTrue(containsConnection(cti1b))
                    assertEquals(m.rootPackage, concept(m.rootPackage.id))
                    assertEquals(m.rootConceptType, concept(m.rootConceptType.id))
                    assertEquals(pkg1, concept(pkg1.id))
                    assertEquals(ct1a, concept(ct1a.id))
                    assertEquals(ct1b, concept(ct1b.id))
                    assertEquals(ctc1a, connection(ctc1a.id))
                    assertEquals(ctc1b, connection(ctc1b.id))
                    assertEquals(cti1a, connection(cti1a.id))
                    assertEquals(cti1b, connection(cti1b.id))
                }

                assertTrue(childConceptTypes(pkg1).contains(ct1a))
                assertTrue(childConceptTypes(pkg1).contains(ct1b))

                assertTrue(hasChild(pkg1,ct1a))
                assertTrue(hasChild(pkg1,ct1b))

                assertTrue(hasParent(ct1a,pkg1))
                assertTrue(hasParent(ct1b,pkg1))

                assertEquals(pkg1,parentPackage(ct1a))
                assertEquals(pkg1,parentPackage(ct1b))
            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(m.makeUuid(), false, "pkg1")
            ct1a = ConceptType(m.makeUuid(), name = "ConceptType1")
            ct1b = ConceptType(m.makeUuid(), name = "ConceptType2")
            ctc1a = ConceptTypeContainment(m.makeUuid(), pkg1, ct1a)
            ctc1b = ConceptTypeContainment(m.makeUuid(), pkg1, ct1b)
            cti1a = ConceptTypeInheritance(m.makeUuid(), ct1a, m.rootConceptType)
            cti1b = ConceptTypeInheritance(m.makeUuid(), ct1b, m.rootConceptType)

            with(g) {
                addConcept(pkg1)
                addConcept(ct1a)
                addConcept(ct1b)
                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg1.id))
                addConnection(ctc1a)
                addConnection(ctc1b)
                addConnection(cti1a)
                addConnection(cti1b)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

