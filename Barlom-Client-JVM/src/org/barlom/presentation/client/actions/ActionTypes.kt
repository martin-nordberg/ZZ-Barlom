//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.state.ApplicationUiState

/**
 * Function defining an action against the application state as a whole.
 */
typealias AppAction = (appState: ApplicationState) -> String


/**
 * Function defining an action against the UI state only.
 */
typealias UiAction = (uiState: ApplicationUiState) -> String
