//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.ConceptPropertyType
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.connections.ConceptPropertyTypeContainment
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate child concept types in a package with ID [parentConceptTypeId].
 */
fun Model.childConceptPropertyTypes(parentConceptTypeId: Id<ConceptType>): List<ConceptPropertyType> =
    graph.findConceptsConnectedFrom(parentConceptTypeId, ConceptPropertyTypeContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [parentConceptTypeId] has a child concept type with ID [childConceptPropertyTypeId].
 */
fun Model.hasChild(parentConceptTypeId: Id<ConceptType>, childConceptPropertyTypeId: Id<ConceptPropertyType>): Boolean =
    graph.hasConceptConnectedFrom(parentConceptTypeId, ConceptPropertyTypeContainment.TYPE) { connection ->
        connection.childConceptPropertyTypeId == childConceptPropertyTypeId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the concept type with ID [childConceptPropertyTypeId] has parent with ID [parentConceptTypeId].
 */
fun Model.hasParent(childConceptPropertyTypeId: Id<ConceptPropertyType>, parentConceptTypeId: Id<ConceptType>): Boolean =
    graph.hasConceptConnectedTo(childConceptPropertyTypeId, ConceptPropertyTypeContainment.TYPE) { connection ->
        connection.parentConceptTypeId == parentConceptTypeId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the parent package of the concept type with ID [childConceptPropertyTypeId].
 */
fun Model.parentConceptType(childConceptPropertyTypeId: Id<ConceptPropertyType>): ConceptType? =
    graph.findConceptConnectedTo(childConceptPropertyTypeId, ConceptPropertyTypeContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

