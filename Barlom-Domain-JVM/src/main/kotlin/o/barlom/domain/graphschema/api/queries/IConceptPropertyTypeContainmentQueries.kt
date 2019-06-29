//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.ConceptPropertyType
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.connections.Containment
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

interface IConceptPropertyTypeContainmentQueries {

    /** The graph at the core of this model. */
    val graph: IGraph

    ////

    /**
     * Finds the immediate child concept types in a package with ID [parentConceptTypeId].
     */
    fun ConceptType.childConceptPropertyTypes(): List<ConceptPropertyType> =
        graph.findConceptsConnectedFrom(this, Containment.CONCEPT_PROPERTY_TYPE_CONTAINMENT_TYPE)

    /**
     * Tests whether the package with ID [parentConceptTypeId] has a child concept type with ID [childConceptPropertyTypeId].
     */
    fun ConceptType.hasChild(childConceptPropertyType: ConceptPropertyType): Boolean =
        graph.hasConceptConnectedFrom(this, Containment.CONCEPT_PROPERTY_TYPE_CONTAINMENT_TYPE) { connection ->
            connection.childId == childConceptPropertyType
        }

    /**
     * Tests whether the concept type with ID [childConceptPropertyTypeId] has parent with ID [parentConceptTypeId].
     */
    fun ConceptPropertyType.hasParent(parentConceptType: ConceptType): Boolean =
        graph.hasConceptConnectedTo(this, Containment.CONCEPT_PROPERTY_TYPE_CONTAINMENT_TYPE) { connection ->
            connection.parentId == parentConceptType
        }

    /**
     * Finds the parent package of the concept type with ID [childConceptPropertyTypeId].
     */
    fun ConceptPropertyType.parentConceptType(): ConceptType? =
        graph.findConceptConnectedTo(this, Containment.CONCEPT_PROPERTY_TYPE_CONTAINMENT_TYPE)

}

//---------------------------------------------------------------------------------------------------------------------

