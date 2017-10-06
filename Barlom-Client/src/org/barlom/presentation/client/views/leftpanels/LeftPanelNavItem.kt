//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels

import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.actions.changeLeftPanel
import org.barlom.presentation.client.state.ELeftPanelType
import org.barlom.presentation.client.state.ELeftPanelType.*
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/** Shows one of the nav items at the top of the left panel. */
fun viewLeftPanelNavItem(
    builder: KatyDomFlowContentBuilder,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    panelType: ELeftPanelType,
    focusedPanelType: ELeftPanelType
) {

    val toolTip = toolTip(panelType)

    katyDomComponent(builder) {

        span(".c-nav__item.c-tooltip.c-tooltip--bottom", toolTip) {

            classes("c-nav__item--active" to (panelType == focusedPanelType))

            attribute("aria-label", toolTip)

            onclick {
                revDispatchUi(changeLeftPanel(panelType))
            }

            span(".u-large.mdi." + iconName(panelType), "icon") {}

        }

    }

}

private fun iconName(panelType: ELeftPanelType) =
    ".mdi-" + when (panelType) {
        BROWSE    -> "folder"
        FAVORITES -> "folder-star"
        SEARCH    -> "magnify"
    }

private fun toolTip(panelType: ELeftPanelType) =
    when (panelType) {
        BROWSE    -> "Browse"
        FAVORITES -> "Favorites"
        SEARCH    -> "Search"
    }
