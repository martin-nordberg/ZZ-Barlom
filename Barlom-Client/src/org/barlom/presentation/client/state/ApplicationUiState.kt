//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.revisions.V

/**
 * The UI (non-model) state of the application.
 */
class ApplicationUiState(revHistory: RevisionHistory) {

    private val _leftPanelType: V<ELeftPanelType>

    init {

        var __leftPanelType: V<ELeftPanelType>? = null

        revHistory.update("Initialize UI state.") {
            __leftPanelType = V(ELeftPanelType.BROWSE)
        }

        _leftPanelType = __leftPanelType!!

    }

    var leftPanelType
        get() = _leftPanelType.get()
        set(value) = _leftPanelType.set(value)

}
