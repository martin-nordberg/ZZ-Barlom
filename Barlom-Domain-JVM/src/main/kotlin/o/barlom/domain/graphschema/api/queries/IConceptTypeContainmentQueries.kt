//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.Containment
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

interface IConceptTypeContainmentQueries {

    /** The graph at the core of this model. */
    val graph: IGraph

    ////

    /**
     * Finds the immediate child concept types in a package with ID [parentModuleId].
     */
    fun Module.childConceptTypes(): List<ConceptType> =
        graph.findConceptsConnectedFrom(this, Containment.CONCEPT_TYPE_CONTAINMENT_TYPE)

    /**
     * Tests whether the package with ID [parentModuleId] has a child concept type with ID [childConceptTypeId].
     */
    fun Module.hasChild(childConceptType: ConceptType): Boolean =
        graph.hasConceptConnectedFrom(this, Containment.CONCEPT_TYPE_CONTAINMENT_TYPE) { connection ->
            connection.childId == childConceptType
        }

    /**
     * Tests whether the concept type with ID [childConceptTypeId] has parent with ID [parentModuleId].
     */
    fun ConceptType.hasParent(parentModule: Module): Boolean =
        graph.hasConceptConnectedTo(this, Containment.CONCEPT_TYPE_CONTAINMENT_TYPE) { connection ->
            connection.parentId == parentModule
        }

    /**
     * Finds the parent package of the concept type with ID [childConceptTypeId].
     */
    fun ConceptType.parentModule(): Module? =
        graph.findConceptConnectedTo(this, Containment.CONCEPT_TYPE_CONTAINMENT_TYPE)

}

//---------------------------------------------------------------------------------------------------------------------

