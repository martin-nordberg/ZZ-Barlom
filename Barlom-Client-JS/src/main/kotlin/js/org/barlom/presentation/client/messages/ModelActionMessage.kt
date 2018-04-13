//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.messages

import o.org.barlom.domain.metamodel.api.actions.ModelAction
import o.org.barlom.presentation.client.ApplicationState

/**
 * A message that carries its own action [modelAction] that acts only upon the model state.
 */
class ModelActionMessage(
    val modelAction: ModelAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) =
        applicationState.revHistory.update {
            modelAction.invoke(applicationState.model)
        }

}
