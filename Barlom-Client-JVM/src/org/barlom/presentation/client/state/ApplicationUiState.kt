//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.infrastructure.revisions.V
import org.barlom.presentation.client.state.leftpanels.ELeftPanelType
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState

/**
 * The UI (non-model) state of the application.
 */
class ApplicationUiState() {

    private val _focusedElement: V<AbstractNamedElement?> = V(null)

    private val _leftPanelType: V<ELeftPanelType> = V(
        ELeftPanelType.BROWSE)


    val browsePanelState = BrowsePanelState(_focusedElement)


    var focusedElement
        get() = _focusedElement.get()
        set(value) = _focusedElement.set(value)

    var leftPanelType
        get() = _leftPanelType.get()
        set(value) = _leftPanelType.set(value)


}
