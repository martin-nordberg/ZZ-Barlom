//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractPackagedConcept
import o.barlom.domain.graphschema.api.concepts.Package

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a connection representing containment within a package.
 */
abstract class AbstractPackagedElementContainment<
    Connection : AbstractPackagedElementContainment<Connection, ChildConcept>,
    ChildConcept : AbstractPackagedConcept<ChildConcept>
> : AbstractContainment<Connection, Package, ChildConcept>() {

    val parentPackageId
        get() = fromConceptId

}

//---------------------------------------------------------------------------------------------------------------------

