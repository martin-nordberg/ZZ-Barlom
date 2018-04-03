//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.actions.leftpanels.browse

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState


/**
 * Function defining an action against the browse panel state state only.
 */
typealias BrowsePanelAction = (browsePanelState: BrowsePanelState) -> String


/**
 * Actions affecting the browse panel.
 */
object BrowsePanelActions {

    /**
     * Expands or contracts a given [element] in the browse panel.
     */
    fun toggleExpanded(element: AbstractDocumentedElement): BrowsePanelAction {

        return { browsePanelState: BrowsePanelState ->

            if (browsePanelState.isExpandedElement(element)) {
                browsePanelState.removeExpandedElement(element)
                "Contracted element in browse panel."
            }
            else {
                browsePanelState.addExpandedElement(element)
                "Expanded element in browse panel."
            }

        }

    }

}
