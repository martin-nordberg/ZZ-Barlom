//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.presentation.client.actions

import o.barlom.presentation.client.ApplicationState
import o.barlom.presentation.client.state.ApplicationUiState

/**
 * Function defining an action against the application state as a whole.
 */
typealias AppAction = (appState: ApplicationState) -> String


/**
 * Function defining an action against the UI state only.
 */
typealias UiAction = (uiState: ApplicationUiState) -> String
