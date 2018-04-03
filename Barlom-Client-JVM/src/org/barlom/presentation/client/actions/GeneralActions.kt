//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.state.ApplicationUiState


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
