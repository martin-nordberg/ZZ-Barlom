//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.rightpanels

import js.org.barlom.presentation.client.messages.Message
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.katydid.vdom.eventhandling.onclick
import o.org.barlom.presentation.client.state.rightpanels.ERightPanelType

/** Shows one of the nav items at the top of the right panel. */
fun viewRightPanelNavItem(
    builder: KatydidFlowContentBuilder<Message>,
    panelType: ERightPanelType
) = katydidComponent(builder) {

    val toolTip = toolTip(panelType)

    span(".c-nav__item.c-nav__item--right.c-tooltip.c-tooltip--bottom", toolTip) {

        attribute("aria-label", toolTip)

        onclick {
            // TODO revDispatchUi { Tbd(panelType) }
            emptyList()
        }

        span(".u-large.mdi." + iconName(panelType), "icon") {}

    }

}

private fun iconName(panelType: ERightPanelType) =
    ".mdi-" + when (panelType) {
        ERightPanelType.HELP     -> "help-circle"
        ERightPanelType.SETTINGS -> "settings"
    }

private fun toolTip(panelType: ERightPanelType) =
    when (panelType) {
        ERightPanelType.HELP     -> "Help"
        ERightPanelType.SETTINGS -> "Settings"
    }