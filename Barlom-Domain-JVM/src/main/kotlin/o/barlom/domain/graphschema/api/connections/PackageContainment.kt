//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent package to contained child package.
 */
data class PackageContainment(
    override val id: Id<PackageContainment>,
    override val fromConceptId: Id<Package>,
    override val toConceptId: Id<Package>,
    override var isShared: Boolean = true
) : AbstractPackagedElementContainment<PackageContainment, Package>()

//---------------------------------------------------------------------------------------------------------------------

