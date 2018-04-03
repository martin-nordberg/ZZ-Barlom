//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.messages

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.presentation.client.ApplicationState

class ModelActionMessage(
    val modelAction: ModelAction
) : ActionMessage {

    override fun executeAction(applicationState: ApplicationState) {

        applicationState.revHistory.update {
            val result = modelAction.invoke(applicationState.model)
            console.log(result)
            result
        }

    }

}
