//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views

import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.messages.Message
import org.barlom.presentation.client.state.leftpanels.ELeftPanelType.*
import org.barlom.presentation.client.state.rightpanels.ERightPanelType.HELP
import org.barlom.presentation.client.state.rightpanels.ERightPanelType.SETTINGS
import org.barlom.presentation.client.views.leftpanels.browse.viewBrowsePanel
import org.barlom.presentation.client.views.leftpanels.favorites.viewFavoritesPanel
import org.barlom.presentation.client.views.leftpanels.search.viewSearchPanel
import org.barlom.presentation.client.views.leftpanels.viewLeftPanelNavItem
import org.barlom.presentation.client.views.listitems.viewFocusedElementPath
import org.barlom.presentation.client.views.rightpanels.forms.viewPropertiesForm
import org.barlom.presentation.client.views.rightpanels.links.viewRelatedElements
import org.barlom.presentation.client.views.rightpanels.viewRightPanelNavItem
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom

/**
 * Generates the view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun view(appState: ApplicationState): KatyDomHtmlElement<Message> {

    val m = appState.model
    val ui = appState.uiState
    val revHistory = appState.revHistory

    // Sample view, much TBD
    return katyDom {

        revHistory.review {

            main("#BarlomMetamodelingEnvironment.o-grid.o-grid--no-gutter.o-panel.u-small") {

                div("#left-panel-container.o-grid__cell--width-20.o-panel-container") {

                    nav("#left-navigation.c-nav.c-nav--inline") {

                        viewLeftPanelNavItem(this, BROWSE, ui.leftPanelType)
                        viewLeftPanelNavItem(this, FAVORITES, ui.leftPanelType)
                        viewLeftPanelNavItem(this, SEARCH, ui.leftPanelType)

                    }

                    when (ui.leftPanelType) {
                        BROWSE    ->
                            viewBrowsePanel(this, m, ui.browsePanelState)
                        FAVORITES ->
                            viewFavoritesPanel(this, m)
                        SEARCH    ->
                            viewSearchPanel(this, m)
                    }

                }

                div("#right-panel-container.o-grid__cell--width-80.o-panel-container") {

                    nav("#right-navigation.c-nav.c-nav--inline.c-nav--light") {

                        div("#focused-element-container.c-nav__content") {
                            viewFocusedElementPath(this, ui.focusedElement)
                        }

                        viewRightPanelNavItem(this, SETTINGS)
                        viewRightPanelNavItem(this, HELP)

                    }

                    if (ui.focusedElement != null) {

                        div("#right-forms-container.o-grid") {

                            div("#properties-form-container.o-grid__cell--width-60") {
                                viewPropertiesForm(this, ui.focusedElement!!)
                            }

                            div("#related-elements-container.o-grid__cell--width-40") {
                                viewRelatedElements(this, ui.relatedElementsPanelState,
                                                    ui.focusedElement!!)
                            }

                        }

                    }

                }

            }

        }

    }

}


