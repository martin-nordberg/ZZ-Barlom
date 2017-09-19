//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

/**
 * Interface defining an action against the application state as a whole.
 */
interface IAction<AppState> {

    fun apply(appState: AppState)

    val description: String

}
