//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.domain.metamodel.api.actions.IModelAction
import org.barlom.presentation.client.state.ApplicationState

/**
 * A concrete application action applying to just the model portion of the application state.
 */
class ModelAction(

    private val modelAction: IModelAction

) : IAction<ApplicationState> {

    override fun apply(appState: ApplicationState) {
        modelAction.apply(appState.model)
    }

    override val description
        get() = modelAction.description

}
