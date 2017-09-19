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

    /**
     * Wraps the model update of this action in a revision history transaction.
     */
    fun applyWithRev(model: Model, maxRetries: Int = 0) {

        model.revHistory.update(this.description, maxRetries) {
            this.apply(model)
        }

    }

    /** A description of this action for use in the revision history. */
    val description: String

}