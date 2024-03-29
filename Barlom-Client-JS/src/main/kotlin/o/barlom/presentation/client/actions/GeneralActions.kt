//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.presentation.client.actions

import o.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import x.barlom.infrastructure.uuids.Uuid
import o.barlom.presentation.client.ApplicationState
import o.barlom.presentation.client.state.ApplicationUiState


object GeneralActions {

    /**
     * Changes the element that is focused for editing or browsing.
     */
    fun focus(focusedElement: AbstractNamedElement): UiAction {

        return { uiState: ApplicationUiState ->
            uiState.focusedElement = focusedElement

            "Selected element ${focusedElement.path} for review."
        }

    }

    /**
     * Changes the element that is focused for editing or browsing given its [id].
     */
    fun focusById(id: Uuid): AppAction {

        return { appState: ApplicationState ->
            val focusedElement = appState.model.findVertexById(id)

            if ( focusedElement != null ) {
                appState.uiState.focusedElement = focusedElement

                "Selected element ${focusedElement.path} for review."
            }
            else {
                "Failed to find element with ID $id."
            }

        }

    }

}
