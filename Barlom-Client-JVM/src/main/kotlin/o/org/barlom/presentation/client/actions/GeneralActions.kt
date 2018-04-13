//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.presentation.client.actions

import o.org.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import x.org.barlom.infrastructure.uuids.Uuid
import o.org.barlom.presentation.client.ApplicationState
import o.org.barlom.presentation.client.state.ApplicationUiState


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
