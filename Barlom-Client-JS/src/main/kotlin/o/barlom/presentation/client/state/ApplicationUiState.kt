//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.presentation.client.state

import o.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import o.barlom.infrastructure.revisions.V
import o.barlom.presentation.client.state.leftpanels.ELeftPanelType
import o.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState
import o.barlom.presentation.client.state.rightpanels.RelatedElementsPanelState

/**
 * The UI (non-model) state of the application.
 */
class ApplicationUiState() {

    private val _focusedElement: V<AbstractNamedElement?> = V(null)

    private val _leftPanelType: V<ELeftPanelType> = V(
        ELeftPanelType.BROWSE)


    val browsePanelState = BrowsePanelState(_focusedElement)

    val relatedElementsPanelState = RelatedElementsPanelState()


    var focusedElement
        get() = _focusedElement.get()
        set(value) = _focusedElement.set(value)

    var leftPanelType
        get() = _leftPanelType.get()
        set(value) = _leftPanelType.set(value)


}
