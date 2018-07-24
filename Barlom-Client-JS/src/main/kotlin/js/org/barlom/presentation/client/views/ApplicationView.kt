//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views

import o.org.barlom.presentation.client.ApplicationState
import js.org.barlom.presentation.client.messages.Message
import o.org.barlom.presentation.client.state.leftpanels.ELeftPanelType.*
import o.org.barlom.presentation.client.state.rightpanels.ERightPanelType.HELP
import o.org.barlom.presentation.client.state.rightpanels.ERightPanelType.SETTINGS
import js.org.barlom.presentation.client.views.leftpanels.browse.viewBrowsePanel
import js.org.barlom.presentation.client.views.leftpanels.favorites.viewFavoritesPanel
import js.org.barlom.presentation.client.views.leftpanels.search.viewSearchPanel
import js.org.barlom.presentation.client.views.leftpanels.viewLeftPanelNavItem
import js.org.barlom.presentation.client.views.listitems.viewFocusedElementPath
import js.org.barlom.presentation.client.views.rightpanels.forms.viewPropertiesForm
import js.org.barlom.presentation.client.views.rightpanels.links.viewRelatedElements
import js.org.barlom.presentation.client.views.rightpanels.viewRightPanelNavItem
import o.org.katydom.elements.AbstractKatyDomHtmlElement
import o.org.katydom.application.katyDom

/**
 * Generates the view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun view(appState: ApplicationState): AbstractKatyDomHtmlElement<Message> {

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


