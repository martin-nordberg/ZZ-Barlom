package org.barlom.presentation.client.views.rightpanels

import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.actions.ui.ChangeLeftPanel
import org.barlom.presentation.client.state.ELeftPanelType
import org.barlom.presentation.client.state.ERightPanelType
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/** Shows one of the nav items at the top of the right panel. */
fun viewRightPanelNavItem(
    builder: KatyDomFlowContentBuilder,
    revDispatchUi: (makeUiAction: () -> IUiAction) -> Unit,
    panelType: ERightPanelType
) = katyDomComponent(builder) {

    span(".c-nav__item.c-nav__item--right.c-tooltip.c-tooltip--bottom") {

        attribute("aria-label", toolTip(panelType))

        onclick {
            // TODO revDispatchUi { Tbd(panelType) }
        }

        span(".u-large.mdi." + iconName(panelType)) {}

    }

}

private fun iconName(panelType: ERightPanelType) =
    ".mdi-" + when ( panelType ) {
        ERightPanelType.HELP -> "help-circle"
        ERightPanelType.SETTINGS   -> "settings"
    }

private fun toolTip(panelType: ERightPanelType) =
    when ( panelType ) {
        ERightPanelType.HELP    -> "Help"
        ERightPanelType.SETTINGS    -> "Settings"
    }