//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.domain.metamodel.api.vertices.AbstractNamedElement
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

}
