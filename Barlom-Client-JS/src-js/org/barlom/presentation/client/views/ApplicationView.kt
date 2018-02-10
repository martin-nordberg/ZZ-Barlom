//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.presentation.client.ApplicationState
import org.barlom.presentation.client.actions.AppAction
import org.barlom.presentation.client.actions.UiAction
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
fun view(appState: ApplicationState, dispatch: (action: AppAction) -> Unit): KatyDomHtmlElement {

    val m = appState.model
    val ui = appState.uiState
    val revHistory = appState.revHistory

    /**
     * Creates and dispatches an action inside a rev history transaction.
     */
    fun revDispatch(action: AppAction) {
        revHistory.review {
            dispatch(action)
        }
    }

    /**
     * Creates and dispatches a model-specific action inside a rev history transaction.
     */
    fun revDispatchModel(modelAction: ModelAction) {
        revDispatch { appState -> modelAction(appState.model) }
    }

    /**
     * Creates and dispatches a UI-specific action inside a rev history transaction.
     */
    fun revDispatchUi(uiAction: UiAction) {
        revDispatch { appState -> uiAction(appState.uiState) }
    }

    // Sample view, much TBD
    return katyDom {

        revHistory.review {

            main("#BarlomMetamodelingEnvironment.o-grid.o-grid--no-gutter.o-panel.u-small") {

                div("#left-panel-container.o-grid__cell--width-20.o-panel-container") {

                    nav("#left-navigation.c-nav.c-nav--inline") {

                        viewLeftPanelNavItem(this, ::revDispatchUi, BROWSE, ui.leftPanelType)
                        viewLeftPanelNavItem(this, ::revDispatchUi, FAVORITES, ui.leftPanelType)
                        viewLeftPanelNavItem(this, ::revDispatchUi, SEARCH, ui.leftPanelType)

                    }

                    when (ui.leftPanelType) {
                        BROWSE    ->
                            viewBrowsePanel(this, m, ui.browsePanelState, ::revDispatchModel, ::revDispatchUi)
                        FAVORITES ->
                            viewFavoritesPanel(this, m, ::revDispatchModel)
                        SEARCH    ->
                            viewSearchPanel(this, m, ::revDispatchModel)
                    }

                }

                div("#right-panel-container.o-grid__cell--width-80.o-panel-container") {

                    nav("#right-navigation.c-nav.c-nav--inline.c-nav--light") {

                        div("#focused-element-container.c-nav__content") {
                            viewFocusedElementPath(this, ui.focusedElement, ::revDispatchUi)
                        }

                        viewRightPanelNavItem(this, ::revDispatchUi, SETTINGS)
                        viewRightPanelNavItem(this, ::revDispatchUi, HELP)

                    }

                    if (ui.focusedElement != null) {

                        div("#right-forms-container.o-grid") {

                            div("#properties-form-container.o-grid__cell--width-60") {
                                viewPropertiesForm(this, ::revDispatchModel, ui.focusedElement!!, ::revDispatchUi)
                            }

                            div("#related-elements-container.o-grid__cell--width-40") {
                                viewRelatedElements(this, ui.relatedElementsPanelState, ::revDispatchModel,
                                                    ui.focusedElement!!, ::revDispatchUi)
                            }

                        }

                    }

                }

            }

        }

    } as KatyDomHtmlElement

}


