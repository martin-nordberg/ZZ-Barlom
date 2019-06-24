//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.connections.ConceptTypeInheritance
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the concept type with ID [superTypeId] has a sub type with ID [subTypeId].
 */
fun Model.hasSubType(superTypeId: Id<ConceptType>, subTypeId: Id<ConceptType>): Boolean =
    graph.hasConceptConnectedFrom(subTypeId, ConceptTypeInheritance.TYPE) { connection ->
        connection.superTypeId == superTypeId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the concept type with ID [subTypeId] has super type with ID [superTypeId].
 */
fun Model.hasSuperType(subTypeId: Id<ConceptType>, superTypeId: Id<ConceptType>): Boolean =
    hasSubType(superTypeId, subTypeId)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a concept type with ID [superTypeId] has a direct or indirect sub type with ID [subTypeId].
 */
fun Model.hasTransitiveSubType(superTypeId: Id<ConceptType>, subTypeId: Id<ConceptType>): Boolean =
    graph.hasTransitiveConceptConnectedTo(superTypeId, ConceptTypeInheritance.TYPE) { connection ->
        connection.subTypeId == subTypeId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a concept type with ID [subTypeId] has a direct or indirect super type with ID [superTypeId].
 */
fun Model.hasTransitiveSuperType(subTypeId: Id<ConceptType>, superTypeId: Id<ConceptType>): Boolean =
    graph.hasTransitiveConceptConnectedFrom(subTypeId, ConceptTypeInheritance.TYPE) { connection ->
        connection.superTypeId == superTypeId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate sub types of a concept type with id [superTypeId].
 */
fun Model.subTypes(superTypeId: Id<ConceptType>): List<ConceptType> =
    graph.findConceptsConnectedTo(superTypeId, ConceptTypeInheritance.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate super types of a concept type with ID [subTypeId].
 */
fun Model.superTypes(subTypeId: Id<ConceptType>): List<ConceptType> =
    graph.findConceptsConnectedFrom(subTypeId, ConceptTypeInheritance.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the direct or indirect sub types of a concept type with id [superTypeId].
 */
fun Model.transitiveSubTypes(superTypeId: Id<ConceptType>): Set<ConceptType> =
    graph.findTransitiveConceptsConnectedTo(superTypeId, ConceptTypeInheritance.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the direct or indirect super types of a concept type with id [subTypeId].
 */
fun Model.transitiveSuperTypes(subTypeId: Id<ConceptType>): Set<ConceptType> =
    graph.findTransitiveConceptsConnectedFrom(subTypeId, ConceptTypeInheritance.TYPE)

//---------------------------------------------------------------------------------------------------------------------

