//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.connections.ConceptTypeInheritance
import o.barlom.domain.graphschema.api.connections.PackageContainment
import o.barlom.domain.graphschema.api.model.Model
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of adding packages to a schema graph.
 */
@Suppress("RemoveRedundantBackticks")
class ConceptTypeTests
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
            with(m.graph) {
                assertEquals(5, numConcepts)
                assertEquals(5, numConnections)
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
                assertEquals(m.rootPackage, concept(m.rootPackageId))
                assertEquals(m.rootConceptType, concept(m.rootConceptTypeId))
                assertEquals(pkg1, concept(pkg1.id))
                assertEquals(ct1a, concept(ct1a.id))
                assertEquals(ct1b, concept(ct1b.id))
                assertEquals(ctc1a, connection(ctc1a.id))
                assertEquals(ctc1b, connection(ctc1b.id))
                assertEquals(cti1a, connection(cti1a.id))
                assertEquals(cti1b, connection(cti1b.id))
            }

        runWriteCheckTest(::check) { m, g ->
            pkg1 = Package(m.makeId(), false, "pkg1")
            ct1a = ConceptType(m.makeId(), name = "ConceptType1")
            ct1b = ConceptType(m.makeId(), name = "ConceptType2")
            ctc1a = ConceptTypeContainment(m.makeId(), pkg1.id, ct1a.id)
            ctc1b = ConceptTypeContainment(m.makeId(), pkg1.id, ct1b.id)
            cti1a = ConceptTypeInheritance(m.makeId(), ct1a.id, m.rootConceptTypeId)
            cti1b = ConceptTypeInheritance(m.makeId(), ct1b.id, m.rootConceptTypeId)

            with(g) {
                addConcept(pkg1)
                addConcept(ct1a)
                addConcept(ct1b)
                addConnection(PackageContainment(m.makeId(), m.rootPackageId, pkg1.id))
                addConnection(ctc1a)
                addConnection(ctc1b)
                addConnection(cti1a)
                addConnection(cti1b)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

