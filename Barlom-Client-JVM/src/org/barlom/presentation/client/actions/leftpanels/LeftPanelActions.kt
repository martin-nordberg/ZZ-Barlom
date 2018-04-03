//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.leftpanels

import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.state.ApplicationUiState
import org.barlom.presentation.client.state.leftpanels.ELeftPanelType


/**
 * Actions that act upon the UI state of the left panel.
 */
object LeftPanelActions {

    /**
     * Changes which [leftPanelType] is shown on the left side of the application.
     */
    fun change(leftPanelType: ELeftPanelType): UiAction {

        return { uiState: ApplicationUiState ->
            uiState.leftPanelType = leftPanelType
            uiState.focusedElement = null

            when (leftPanelType) {
                ELeftPanelType.BROWSE    -> "Browsed in left panel."
                ELeftPanelType.FAVORITES -> "Showed favorites in left panel."
                ELeftPanelType.SEARCH    -> "Searched in left panel."
            }
        }

    }

}
