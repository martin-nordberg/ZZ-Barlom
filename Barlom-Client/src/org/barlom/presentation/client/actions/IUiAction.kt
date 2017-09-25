//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.presentation.client.state.ApplicationUiState

/**
 * Represents an action taken to update the UI state.
 */
interface IUiAction {

    /**
     * Performs an update on the given [uiState].
     */
    fun apply(uiState: ApplicationUiState)

    /** A description of this action for use in the revision history. */
    val description: String

}