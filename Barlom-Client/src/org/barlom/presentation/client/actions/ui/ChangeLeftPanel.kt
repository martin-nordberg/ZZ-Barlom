//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.ui

import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.state.ApplicationUiState
import org.barlom.presentation.client.state.ELeftPanelType

class ChangeLeftPanel(private val leftPanelType: ELeftPanelType) : IUiAction {

    override fun apply(uiState: ApplicationUiState) {
        uiState.leftPanelType = leftPanelType
    }

    override val description: String
        get() = when (leftPanelType) {
            ELeftPanelType.BROWSE    -> "Browse in left panel."
            ELeftPanelType.FAVORITES -> "Show favorites in left panel."
            ELeftPanelType.SEARCH    -> "Search in left panel."
        }

}