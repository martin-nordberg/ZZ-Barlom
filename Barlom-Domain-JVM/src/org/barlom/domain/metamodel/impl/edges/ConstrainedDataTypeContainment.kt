//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.edges

import org.barlom.domain.metamodel.api.edges.IConstrainedDataTypeContainment
import org.barlom.domain.metamodel.impl.vertices.IConstrainedDataTypeImpl
import org.barlom.domain.metamodel.impl.vertices.INonRootPackageImpl
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for constrained data type containment.
 */
internal data class ConstrainedDataTypeContainment(

    override val id: Uuid,
    override val parent: INonRootPackageImpl,
    override val child: IConstrainedDataTypeImpl

) : IConstrainedDataTypeContainment {

    init {

        // Register both ends.
        parent.addConstrainedDataTypeContainment(this)
        child.addConstrainedDataTypeContainment(this)

    }

}
