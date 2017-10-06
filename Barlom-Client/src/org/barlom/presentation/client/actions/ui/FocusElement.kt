//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.ui

import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.state.ApplicationUiState


fun focusElement(focusedElement: AbstractPackagedElement): UiAction {

    fun result(uiState: ApplicationUiState): String {
        uiState.focusedElement = focusedElement

        return "Select element ${focusedElement.path} in left panel."
    }

    return ::result

}
