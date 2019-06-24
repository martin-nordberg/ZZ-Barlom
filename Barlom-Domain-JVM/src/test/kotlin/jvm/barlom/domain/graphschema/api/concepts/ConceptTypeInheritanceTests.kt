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
import o.barlom.domain.graphschema.api.queries.*
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

        lateinit var pkg1: Package
        lateinit var ct1a: ConceptType
        lateinit var ct1b: ConceptType
        lateinit var ct2: ConceptType
        lateinit var ct3: ConceptType

        lateinit var ctc1a: ConceptTypeContainment
        lateinit var ctc1b: ConceptTypeContainment
        lateinit var ctc2: ConceptTypeContainment
        lateinit var ctc3: ConceptTypeContainment

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
                    assertTrue(containsConcept(m.rootPackage))
                    assertTrue(containsConcept(m.rootConceptType))
                    assertTrue(containsConcept(pkg1))
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

                assertTrue(childConceptTypes(pkg1).contains(ct1a))
                assertTrue(childConceptTypes(pkg1).contains(ct1b))
                assertTrue(childConceptTypes(pkg1).contains(ct2))
                assertTrue(childConceptTypes(pkg1).contains(ct3))

                assertEquals(1,subTypes(ct1a).size)
                assertTrue(subTypes(ct1a).contains(ct2))
                assertEquals(1,subTypes(ct1b).size)
                assertTrue(subTypes(ct1b).contains(ct2))
                assertEquals(1,subTypes(ct2).size)
                assertTrue(subTypes(ct2).contains(ct3))
                assertTrue(subTypes(ct3).isEmpty())

                assertTrue(hasSubType(ct1a,ct2))
                assertTrue(hasSubType(ct1b,ct2))
                assertTrue(hasSubType(ct2,ct3))

                assertEquals(2,transitiveSubTypes(ct1a).size)
                assertTrue(transitiveSubTypes(ct1a).contains(ct2))
                assertTrue(transitiveSubTypes(ct1a).contains(ct3))
                assertEquals(2,transitiveSubTypes(ct1b).size)
                assertTrue(transitiveSubTypes(ct1b).contains(ct2))
                assertTrue(transitiveSubTypes(ct1b).contains(ct3))
                assertEquals(1,transitiveSubTypes(ct2).size)
                assertTrue(transitiveSubTypes(ct2).contains(ct3))
                assertTrue(transitiveSubTypes(ct3).isEmpty())

                assertTrue(hasTransitiveSubType(ct1a,ct2))
                assertTrue(hasTransitiveSubType(ct1a,ct3))
                assertTrue(hasTransitiveSubType(ct1b,ct2))
                assertTrue(hasTransitiveSubType(ct1b,ct3))
                assertTrue(hasTransitiveSubType(ct2,ct3))

                assertTrue(superTypes(ct1a).isEmpty())
                assertTrue(superTypes(ct1b).isEmpty())
                assertEquals(2,superTypes(ct2).size)
                assertTrue(superTypes(ct2).contains(ct1a))
                assertTrue(superTypes(ct2).contains(ct1b))
                assertEquals(1,superTypes(ct3).size)
                assertTrue(superTypes(ct3).contains(ct2))

                assertTrue(hasSuperType(ct2,ct1a))
                assertTrue(hasSuperType(ct2,ct1b))
                assertTrue(hasSuperType(ct3,ct2))

                assertTrue(transitiveSuperTypes(ct1a).isEmpty())
                assertTrue(transitiveSuperTypes(ct1b).isEmpty())
                assertEquals(2,transitiveSuperTypes(ct2).size)
                assertTrue(transitiveSuperTypes(ct2).contains(ct1a))
                assertTrue(transitiveSuperTypes(ct2).contains(ct1b))
                assertEquals(3,transitiveSuperTypes(ct3).size)
                assertTrue(transitiveSuperTypes(ct3).contains(ct2))
                assertTrue(transitiveSuperTypes(ct3).contains(ct1a))
                assertTrue(transitiveSuperTypes(ct3).contains(ct1b))

                assertTrue(hasTransitiveSuperType(ct2,ct1a))
                assertTrue(hasTransitiveSuperType(ct2,ct1b))
                assertTrue(hasTransitiveSuperType(ct3,ct2))
                assertTrue(hasTransitiveSuperType(ct3,ct1a))
                assertTrue(hasTransitiveSuperType(ct3,ct1b))

            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(m.makeUuid(), false, "pkg1")
            ct1a = ConceptType(m.makeUuid(), name = "ConceptType1a")
            ct1b = ConceptType(m.makeUuid(), name = "ConceptType1b")
            ct2 = ConceptType(m.makeUuid(), name = "ConceptType2")
            ct3 = ConceptType(m.makeUuid(), name = "ConceptType3")
            ctc1a = ConceptTypeContainment(m.makeUuid(), pkg1, ct1a)
            ctc1b = ConceptTypeContainment(m.makeUuid(), pkg1, ct1b)
            ctc2 = ConceptTypeContainment(m.makeUuid(), pkg1, ct2)
            ctc3 = ConceptTypeContainment(m.makeUuid(), pkg1, ct3)
            // TODO: inherit from root concept type
            cti21a = ConceptTypeInheritance(m.makeUuid(), ct2, ct1a)
            cti21b = ConceptTypeInheritance(m.makeUuid(), ct2, ct1b)
            cti32 = ConceptTypeInheritance(m.makeUuid(), ct3, ct2)

            with(g) {
                addConcept(pkg1)
                addConcept(ct1a)
                addConcept(ct1b)
                addConcept(ct2)
                addConcept(ct3)
                addConnection(PackageContainment(m.makeUuid(), m.rootPackage, pkg1.id))
                addConnection(ctc1a)
                addConnection(ctc1b)
                addConnection(ctc2)
                addConnection(ctc3)
                addConnection(cti21a)
                addConnection(cti21b)
                addConnection(cti32)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

