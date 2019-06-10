//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from consumer package to supplier package.
 */
data class PackageDependency(
    override val id: Id<PackageDependency>,
    override val fromConceptId: Id<Package>,
    override val toConceptId: Id<Package>,
    override var sharing: ESharing = ESharing.NOT_SHARED
) : AbstractPackagedElementContainment<PackageDependency, Package>() {

    val consumerPackageId
        get() = fromConceptId

    val producerPackageId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

