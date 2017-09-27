//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views

import org.barlom.domain.metamodel.api.actions.IModelAction
import org.barlom.presentation.client.actions.IAction
import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.actions.ModelAction
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.actions.ui.ChangeLeftPanel
import org.barlom.presentation.client.state.ApplicationState
import org.barlom.presentation.client.state.ELeftPanelType
import org.barlom.presentation.client.views.leftpanels.viewBrowsePanel
import org.barlom.presentation.client.views.leftpanels.viewFavoritesPanel
import org.barlom.presentation.client.views.leftpanels.viewSearchPanel
import org.barlom.presentation.client.views.listitems.viewFocusedElementPath
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom

/**
 * Generates the view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun view(appState: ApplicationState, dispatch: (action: IAction<ApplicationState>) -> Unit): KatyDomHtmlElement {

    val m = appState.model
    val ui = appState.uiState

    /**
     * Creates and dispatches an action inside a rev history transaction.
     */
    fun revDispatch(action: IAction<ApplicationState>) {
        appState.revHistory.review {
            dispatch(action)
        }
    }

    /**
     * Creates and dispatches a model-specific action inside a rev history transaction.
     */
    fun revDispatchModel(makeModelAction: () -> IModelAction) {
        revDispatch(ModelAction(makeModelAction()))
    }

    /**
     * Creates and dispatches a UI-specific action inside a rev history transaction.
     */
    fun revDispatchUi(makeUiAction: () -> IUiAction) {
        revDispatch(UiAction(makeUiAction()))
    }

    // Sample view, much TBD
    return katyDom {

        m.revHistory.review {

            main("#BarlomMetamodelingEnvironment.o-grid.o-grid--no-gutter.o-panel.u-small") {

                div(".o-grid__cell--width-20.o-panel-container") {

                    nav(".c-nav.c-nav--inline") {

                        span(".c-nav__item.c-nav__item--active.c-tooltip.c-tooltip--bottom") {
                            attribute("aria-label", "Browse")
                            onclick {
                                revDispatchUi { ChangeLeftPanel(ELeftPanelType.BROWSE) }
                            }
                            span(".mdi.mdi-folder.u-large") {}
                        }

                        span(".c-nav__item.c-tooltip.c-tooltip--bottom") {
                            attribute("aria-label", "Favorites")
                            onclick {
                                revDispatchUi { ChangeLeftPanel(ELeftPanelType.FAVORITES) }
                            }
                            span(".mdi.mdi-folder-star.u-large") {}
                        }

                        span(".c-nav__item.c-tooltip.c-tooltip--bottom") {
                            attribute("aria-label", "Search")
                            onclick {
                                revDispatchUi { ChangeLeftPanel(ELeftPanelType.SEARCH) }
                            }
                            span(".mdi.mdi-magnify.u-large") {}
                        }

                    }

                    when (ui.leftPanelType) {
                        ELeftPanelType.BROWSE    ->
                            viewBrowsePanel(this, m, ::revDispatchModel, ui.focusedElement, ::revDispatchUi)
                        ELeftPanelType.FAVORITES ->
                            viewFavoritesPanel(this, m, ::revDispatchModel)
                        ELeftPanelType.SEARCH    ->
                            viewSearchPanel(this, m, ::revDispatchModel)
                    }

                }

                div(".o-grid__cell--width-80.o-panel-container") {

                    nav(".c-nav.c-nav--inline.c-nav--light") {

                        div(".c-nav__content") {
                            viewFocusedElementPath(this, ui.focusedElement, ::revDispatchUi)
                        }

                        span(".c-nav__item.c-nav__item--right.c-tooltip.c-tooltip--bottom") {
                            attribute("aria-label", "Settings")
                            span(".mdi.mdi-settings.u-large") {}
                        }

                        span(".c-nav__item.c-nav__item--right.c-tooltip.c-tooltip--bottom") {
                            attribute("aria-label", "Help")
                            span(".mdi.mdi-help-circle.u-large") {}
                        }

                    }

                }

            }

        }

    } as KatyDomHtmlElement

}


