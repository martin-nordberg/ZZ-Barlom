//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.messages

import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.actions.AppAction
import org.barlom.presentation.client.actions.UiAction

/**
 * Message that carries its own behavior as an action [uiAction] that affects the UI state.
 */
class AppActionMessage(
    private val appAction: AppAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) =
        applicationState.revHistory.update {
            appAction.invoke(applicationState)
        }

}
