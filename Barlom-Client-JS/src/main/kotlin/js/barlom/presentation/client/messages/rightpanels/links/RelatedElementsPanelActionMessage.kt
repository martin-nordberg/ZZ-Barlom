//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.messages.rightpanels.links

import o.barlom.presentation.client.ApplicationState
import o.barlom.presentation.client.actions.rightpanels.links.RelatedElementsPanelAction
import js.barlom.presentation.client.messages.ActionMessage

/**
 * Message carrying its own behavior as an action [relatedElementsPanelAction] affecting only the related
 * elements panel state.
 */
class RelatedElementsPanelActionMessage(
    private val relatedElementsPanelAction: RelatedElementsPanelAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) =
        applicationState.revHistory.update {
            relatedElementsPanelAction.invoke(applicationState.uiState.relatedElementsPanelState)
        }

}
