//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.connections.Inheritance
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

interface IConceptTypeInheritanceQueries {

    /** The graph at the core of this model. */
    val graph: IGraph

    ////

    /**
     * Tests whether the concept type with ID [superTypeId] has a sub type with ID [subTypeId].
     */
    fun ConceptType.hasSubType(subType: ConceptType): Boolean =
        graph.hasConceptConnectedFrom(subType, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE) { connection ->
            connection.superTypeId == this
        }

    /**
     * Tests whether the concept type with ID [subTypeId] has super type with ID [superTypeId].
     */
    fun ConceptType.hasSuperType(superType: ConceptType): Boolean =
        graph.hasConceptConnectedTo(superType, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE) { connection ->
            connection.subTypeId == this
        }

    /**
     * Tests whether a concept type with ID [superTypeId] has a direct or indirect sub type with ID [subTypeId].
     */
    fun ConceptType.hasTransitiveSubType(subType: ConceptType): Boolean =
        graph.hasTransitiveConceptConnectedTo(this, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE) { connection ->
            connection.subTypeId == subType
        }

    /**
     * Tests whether a concept type with ID [subTypeId] has a direct or indirect super type with ID [superTypeId].
     */
    fun ConceptType.hasTransitiveSuperType(superType: ConceptType): Boolean =
        graph.hasTransitiveConceptConnectedFrom(this, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE) { connection ->
            connection.superTypeId == superType
        }

    /**
     * Finds the immediate sub types of a concept type with id [superTypeId].
     */
    fun ConceptType.subTypes(): List<ConceptType> =
        graph.findConceptsConnectedTo(this, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE)

    /**
     * Finds the immediate super types of a concept type with ID [subTypeId].
     */
    fun ConceptType.superTypes(): List<ConceptType> =
        graph.findConceptsConnectedFrom(this, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE)

    /**
     * Finds the direct or indirect sub types of a concept type with id [superTypeId].
     */
    fun ConceptType.transitiveSubTypes(): Set<ConceptType> =
        graph.findTransitiveConceptsConnectedTo(this, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE)

    /**
     * Finds the direct or indirect super types of a concept type with id [subTypeId].
     */
    fun ConceptType.transitiveSuperTypes(): Set<ConceptType> =
        graph.findTransitiveConceptsConnectedFrom(this, Inheritance.CONCEPT_TYPE_INHERITANCE_TYPE)

}

//---------------------------------------------------------------------------------------------------------------------

