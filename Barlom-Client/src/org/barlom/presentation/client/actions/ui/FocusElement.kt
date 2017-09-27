//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.ui

import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.state.ApplicationUiState

class FocusElement(private val focusedElement: AbstractPackagedElement) : IUiAction {

    override fun apply(uiState: ApplicationUiState) {
        uiState.focusedElement = focusedElement
    }

    override val description: String
        get() = "Select element in left panel."

}