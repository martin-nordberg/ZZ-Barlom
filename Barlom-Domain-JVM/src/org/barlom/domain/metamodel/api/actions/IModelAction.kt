//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model

/**
 * Represents an action taken to update a model.
 */
interface IModelAction {

    /**
     * Performs an update on the given [model].
     */
    fun apply(model: Model)

    /** A description of this action for use in the revision history. */
    val description: String

}