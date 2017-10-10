//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions

import org.barlom.presentation.client.state.ApplicationUiState
import org.barlom.presentation.client.state.ELeftPanelType


class LeftPanelActions {

    companion object {

        /**
         * Changes which [leftPanelType] is shown on the left side of the application.
         */
        fun change(leftPanelType: ELeftPanelType): UiAction {

            return { uiState: ApplicationUiState ->
                uiState.leftPanelType = leftPanelType
                uiState.focusedElement = null

                when (leftPanelType) {
                    ELeftPanelType.BROWSE    -> "Browse in left panel."
                    ELeftPanelType.FAVORITES -> "Show favorites in left panel."
                    ELeftPanelType.SEARCH    -> "Search in left panel."
                }
            }

        }

    }

}
