//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConstrainedDataType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent package to child contained data type.
 */
data class ConstrainedDataTypeContainment(
    override val id: Id<ConstrainedDataTypeContainment>,
    override val fromConceptId: Id<Package>,
    override val toConceptId: Id<ConstrainedDataType>,
    override var sharing: ESharing = ESharing.SHARED
) : AbstractPackagedElementContainment<ConstrainedDataTypeContainment, ConstrainedDataType>() {

    val childConstrainedDataTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

