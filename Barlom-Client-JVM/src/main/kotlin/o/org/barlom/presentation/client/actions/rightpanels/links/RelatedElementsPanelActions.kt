//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.presentation.client.actions.rightpanels.links

import o.org.barlom.presentation.client.state.rightpanels.RelatedElementsPanelState

/**
 * Function defining an action against the related elements panel state state only.
 */
typealias RelatedElementsPanelAction = (relatedElementsPanelState: RelatedElementsPanelState) -> String


/**
 * Actions affecting the related elements panel.
 */
object RelatedElementsPanelActions {

    fun resetSupplierPackagePath(): RelatedElementsPanelAction {

        return { relatedElementsPanelState: RelatedElementsPanelState ->
            relatedElementsPanelState.newSupplierPackagePath = ""
            "Reset supplier package path."
        }

    }

    fun setSupplierPackagePath(newValue: String): RelatedElementsPanelAction {

        return { relatedElementsPanelState: RelatedElementsPanelState ->
            relatedElementsPanelState.newSupplierPackagePath = newValue
            "Entered new supplier package path '$newValue'."
        }

    }

}
