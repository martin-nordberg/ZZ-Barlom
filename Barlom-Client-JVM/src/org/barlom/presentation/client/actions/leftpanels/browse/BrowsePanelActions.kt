//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.leftpanels.browse

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState


/**
 * Function defining an action against the UI state only.
 */
typealias BrowsePanelAction = (browsePanelState: BrowsePanelState) -> String


object BrowsePanelActions {

    fun toggleExpanded(element: AbstractDocumentedElement): BrowsePanelAction {

        return { browsePanelState: BrowsePanelState ->

            if (browsePanelState.isExpandedElement(element)) {
                browsePanelState.removeExpandedElement(element)
                "Contract element in browse panel."
            }
            else {
                browsePanelState.addExpandedElement(element)
                "Expand element in browse panel."
            }

        }

    }

}
