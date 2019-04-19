//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.edges

import o.barlom.domain.metamodel.api.vertices.ConstrainedDataType
import o.barlom.domain.metamodel.api.vertices.Package
import x.barlom.infrastructure.uuids.Uuid

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


    override fun remove() {

        // Unregister both ends.
        parent.removeConstrainedDataTypeContainment(this)
        child.removeConstrainedDataTypeContainment(this)

    }

}
