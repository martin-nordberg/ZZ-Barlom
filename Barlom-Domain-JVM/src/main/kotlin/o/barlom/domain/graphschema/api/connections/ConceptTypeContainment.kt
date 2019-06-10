//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent package to contained child concept type.
 */
data class ConceptTypeContainment(
    override val id: Id<ConceptTypeContainment>,
    override val fromConceptId: Id<Package>,
    override val toConceptId: Id<ConceptType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractPackagedElementContainment<ConceptTypeContainment, ConceptType>() {

    val childConceptTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

