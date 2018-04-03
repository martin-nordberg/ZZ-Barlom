//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.messages.leftpanels.browse

import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelAction
import org.barlom.presentation.client.messages.ActionMessage

class BrowsePanelActionMessage(
    private val browsePanelAction: BrowsePanelAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) {

        applicationState.revHistory.update {
            val result = browsePanelAction.invoke(applicationState.uiState.browsePanelState)
            console.log( result )
            result
        }

    }

}
