//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels

import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.actions.ui.ChangeLeftPanel
import org.barlom.presentation.client.state.ELeftPanelType
import org.barlom.presentation.client.state.ELeftPanelType.*
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/** Shows one of the nav items at the top of the left panel. */
fun viewLeftPanelNavItem(
    builder: KatyDomFlowContentBuilder,
    revDispatchUi: (makeUiAction: () -> IUiAction) -> Unit,
    panelType: ELeftPanelType,
    focusedPanelType: ELeftPanelType
) = katyDomComponent(builder) {

    span(".c-nav__item.c-tooltip.c-tooltip--bottom") {

        classes("c-nav__item--active" to (panelType==focusedPanelType))

        attribute("aria-label", toolTip(panelType))

        onclick {
            revDispatchUi { ChangeLeftPanel(panelType) }
        }

        span(".u-large.mdi." + iconName(panelType)) {}

    }

}

private fun iconName(panelType:ELeftPanelType) =
    ".mdi-" + when ( panelType ) {
        BROWSE -> "folder"
        FAVORITES -> "folder-star"
        SEARCH -> "magnify"
    }

private fun toolTip(panelType:ELeftPanelType) =
    when ( panelType ) {
        BROWSE -> "Browse"
        FAVORITES -> "Favorites"
        SEARCH -> "Search"
    }
