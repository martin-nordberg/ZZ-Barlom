//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.revisions.V

/**
 * The UI (non-model) state of the application.
 */
class ApplicationUiState(revHistory: RevisionHistory) {

    private val _focusedElement: V<AbstractDocumentedElement?>

    private val _leftPanelType: V<ELeftPanelType>

    init {

        var __focusedElement: V<AbstractDocumentedElement?>? = null
        var __leftPanelType: V<ELeftPanelType>? = null

        revHistory.update("Initialize UI state.") {
            __focusedElement = V(null)
            __leftPanelType = V(ELeftPanelType.BROWSE)
        }

        _focusedElement = __focusedElement!!
        _leftPanelType = __leftPanelType!!

    }

    var focusedElement
        get() = _focusedElement.get()
        set(value) = _focusedElement.set(value)

    var leftPanelType
        get() = _leftPanelType.get()
        set(value) = _leftPanelType.set(value)

}