//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.messages

import o.barlom.presentation.client.ApplicationState
import o.barlom.presentation.client.actions.UiAction

/**
 * Message that carries its own behavior as an action [uiAction] that affects the UI state.
 */
class UiActionMessage(
    private val uiAction: UiAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) =
        applicationState.revHistory.update {
            uiAction.invoke(applicationState.uiState)
        }

}
