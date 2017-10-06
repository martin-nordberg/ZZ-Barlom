//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.ui

import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.state.ApplicationUiState
import org.barlom.presentation.client.state.ELeftPanelType


fun changeLeftPanel(leftPanelType: ELeftPanelType): UiAction {

    fun result(uiState: ApplicationUiState): String {
        uiState.leftPanelType = leftPanelType
        uiState.focusedElement = null

        return when (leftPanelType) {
            ELeftPanelType.BROWSE    -> "Browse in left panel."
            ELeftPanelType.FAVORITES -> "Show favorites in left panel."
            ELeftPanelType.SEARCH    -> "Search in left panel."
        }
    }

    return ::result

}

