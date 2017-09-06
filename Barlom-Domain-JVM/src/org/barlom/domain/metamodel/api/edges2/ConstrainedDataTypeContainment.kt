//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.edges2

import org.barlom.domain.metamodel.api.vertices2.ConstrainedDataType
import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.infrastructure.uuids.Uuid

/**
 * Interface for Barlom constrained data type containment.
 */
class ConstrainedDataTypeContainment internal constructor(

    override val id: Uuid,
    override val parent: Package,
    override val child: ConstrainedDataType

) : AbstractPackagedElementContainment() {

    init {

        // Register both ends.
        parent.addConstrainedDataTypeContainment(this)
        child.addConstrainedDataTypeContainment(this)

    }

}