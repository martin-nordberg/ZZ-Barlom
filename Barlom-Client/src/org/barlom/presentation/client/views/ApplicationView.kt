//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views

import org.barlom.domain.metamodel.api.actions.AddPackage
import org.barlom.domain.metamodel.api.actions.IModelAction
import org.barlom.presentation.client.actions.IAction
import org.barlom.presentation.client.actions.ModelAction
import org.barlom.presentation.client.state.ApplicationState
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom

/**
 * Generates the view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun view(appState: ApplicationState, dispatch: (action: IAction<ApplicationState>) -> Unit): KatyDomHtmlElement {

    val m = appState.model

    /**
     * Creates and dispatches an action inside a rev history transaction.
     */
    fun revDispatch(action: IAction<ApplicationState>) {
        m.revHistory.review {
            dispatch(action)
        }
    }

    /**
     * Creates and dispatches an action inside a rev history transaction.
     */
    fun revDispatchModel(makeModelAction: () -> IModelAction) {
        revDispatch(ModelAction(makeModelAction()))
    }

    // Sample view, much TBD
    return katyDom {

        m.revHistory.review {

            main("#BarlomMetamodelingEnvironment.o-grid.o-grid--no-gutter.o-panel") {

                div( ".o-grid__cell--width-100.o-panel-container") {

                    nav(".c-nav.c-nav--inline.c-nav--light") {

                        span(".c-nav__item") {
                            // TODO: i(".fa.fa-bars"){}
                        }

                        span(".c-nav__content") {
                            text("Barlom Metamodeling Environment")
                        }

                    }

                    div(".o-panel.o-panel--nav-top") {

                        ul("#package-list") {

                            for (pkg in m.rootPackage.children) {
                                li { text(pkg.name) }
                            }

                            li {

                                onclick {
                                    revDispatchModel { AddPackage(m.rootPackage) }
                                    console.log("Clicked")
                                }

                                text("New Package")

                            }

                        }

                    }

                }

            }

        }

    } as KatyDomHtmlElement

}


