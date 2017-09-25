package org.barlom.presentation.client.actions.ui

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.state.ApplicationUiState

class FocusElement(private val focusedElement: AbstractDocumentedElement) : IUiAction {

    override fun apply(uiState: ApplicationUiState) {
        uiState.focusedElement = focusedElement
    }

    override val description: String
        get() = "Select element in left panel."

}