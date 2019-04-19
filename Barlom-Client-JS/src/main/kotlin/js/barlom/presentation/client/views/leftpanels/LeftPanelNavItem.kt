//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.views.leftpanels

import o.barlom.presentation.client.actions.leftpanels.LeftPanelActions
import js.barlom.presentation.client.messages.Message
import js.barlom.presentation.client.messages.UiActionMessage
import o.katydid.events.eventhandling.onclick
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.barlom.presentation.client.state.leftpanels.ELeftPanelType
import o.barlom.presentation.client.state.leftpanels.ELeftPanelType.*

/** Shows one of the nav items at the top of the left panel. */
fun viewLeftPanelNavItem(
    builder: KatydidFlowContentBuilder<Message>,
    panelType: ELeftPanelType,
    focusedPanelType: ELeftPanelType
) {

    val toolTip = toolTip(panelType)

    katydidComponent(builder) {

        span(".c-nav__item.c-tooltip.c-tooltip--bottom", toolTip) {

            classes("c-nav__item--active" to (panelType == focusedPanelType))

            attribute("aria-label", toolTip)

            onclick {
                listOf(UiActionMessage(LeftPanelActions.change(panelType)))
            }

            span(".u-large.mdi." + iconName(panelType), "icon") {}

        }

    }

}

/** Computes the material design icon name for the given left [panelType]. */
private fun iconName(panelType: ELeftPanelType) =
    ".mdi-" + when (panelType) {
        BROWSE    -> "folder"
        FAVORITES -> "folder-star"
        SEARCH    -> "magnify"
    }

/** Computes a tool tip for given left [panelType]. */
private fun toolTip(panelType: ELeftPanelType) =
    when (panelType) {
        BROWSE    -> "Browse"
        FAVORITES -> "Favorites"
        SEARCH    -> "Search"
    }
