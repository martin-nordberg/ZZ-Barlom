//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.messages

import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.actions.UiAction

class UiActionMessage(
    private val uiAction: UiAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) {

        applicationState.revHistory.update {
            val result = uiAction.invoke(applicationState.uiState)
            console.log(result)
            result
        }

    }

}
