//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.vertices

import org.barlom.domain.metamodel.api.vertices.IConstrainedDataType
import org.barlom.domain.metamodel.impl.edges.ConstrainedDataTypeContainment


/**
 * Internal interface to a constrained data type implementation (keeper of edges).
 */
internal interface IConstrainedDataTypeImpl : IConstrainedDataType {

    /**
     * Adds the given containment edge to this data type.
     */
    fun addConstrainedDataTypeContainment(constrainedDataTypeContainment: ConstrainedDataTypeContainment)

}