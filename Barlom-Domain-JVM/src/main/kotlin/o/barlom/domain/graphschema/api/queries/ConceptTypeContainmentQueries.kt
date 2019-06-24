//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate child concept types in a package with ID [parentPackageId].
 */
fun Model.childConceptTypes(parentPackageId: Id<Package>): List<ConceptType> =
    graph.findConceptsConnectedFrom(parentPackageId, ConceptTypeContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [parentPackageId] has a child concept type with ID [childConceptTypeId].
 */
fun Model.hasChild(parentPackageId: Id<Package>, childConceptTypeId: Id<ConceptType>): Boolean =
    graph.hasConceptConnectedFrom(parentPackageId, ConceptTypeContainment.TYPE) { connection ->
        connection.childElementId == childConceptTypeId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the concept type with ID [childConceptTypeId] has parent with ID [parentPackageId].
 */
fun Model.hasParent(childConceptTypeId: Id<ConceptType>, parentPackageId: Id<Package>): Boolean =
    graph.hasConceptConnectedTo(childConceptTypeId, ConceptTypeContainment.TYPE) { connection ->
        connection.parentPackageId == parentPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the parent package of the concept type with ID [childConceptTypeId].
 */
fun Model.parentPackage(childConceptTypeId: Id<ConceptType>): Package? =
    graph.findConceptConnectedTo(childConceptTypeId, ConceptTypeContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

