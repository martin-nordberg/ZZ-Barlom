//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.presentation.client.actions.leftpanels.browse

import o.org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import o.org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState


/**
 * Function defining an action against the browse panel state state only.
 */
typealias BrowsePanelAction = (browsePanelState: BrowsePanelState) -> String


/**
 * Actions affecting the browse panel.
 */
object BrowsePanelActions {

    /**
     * Expands a given [element] in the browse panel.
     */
    fun setExpanded(element: AbstractDocumentedElement): BrowsePanelAction {

        return { browsePanelState: BrowsePanelState ->

            if (browsePanelState.isExpandedElement(element)) {
                "Element already expanded in browse panel."
            }
            else {
                browsePanelState.addExpandedElement(element)
                "Expanded element in browse panel."
            }

        }

    }

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
