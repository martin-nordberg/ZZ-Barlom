//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.presentation.client.state.ApplicationState

/**
 * A concrete application action applying to just the UI portion of the application state.
 */
class UiAction(

    private val uiAction: IUiAction

) : IAction<ApplicationState> {

    override fun apply(appState: ApplicationState) {
        uiAction.apply(appState.uiState)
    }

    override val description
        get() = uiAction.description

}