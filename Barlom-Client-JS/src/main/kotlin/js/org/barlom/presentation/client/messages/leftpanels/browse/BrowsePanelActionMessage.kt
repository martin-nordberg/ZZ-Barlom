//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.messages.leftpanels.browse

import o.org.barlom.presentation.client.ApplicationState
import o.org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelAction
import js.org.barlom.presentation.client.messages.ActionMessage

class BrowsePanelActionMessage(
    private val browsePanelAction: BrowsePanelAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) =
        applicationState.revHistory.update {
            browsePanelAction.invoke(applicationState.uiState.browsePanelState)
        }

}


