//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client

import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.presentation.client.actions.IAction
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom
import org.katydom.api.makeKatyDomLifecycle
import kotlin.browser.document
import kotlin.browser.window

/**
 * Runs the application through its reduxish/elmish lifecycle.
 */
fun <AppState> runApplication(
    appId: String,
    revHistory: RevisionHistory,
    initializeAppState: (RevisionHistory) -> AppState,
    view: (appState: AppState, dispatch: (action: IAction<AppState>) -> Unit) -> KatyDomHtmlElement
) {

    // Find the root application DOM element to put the app into (failing if not found).
    var appElement = document.getElementById(appId)!!

    // Initialize the model.
    val appState = initializeAppState(revHistory)

    // Create the KatyDOM lifecycle for build and patching the view.
    val lifecycle = makeKatyDomLifecycle()

    // Start with an empty div just to avoid nullable node type.
    var appVdomNode = katyDom {
        div("#application") {}
    } as KatyDomHtmlElement

    /**
     * Dispatches an action triggered by an event in the latest edition of the view.
     */
    fun dispatch(action: IAction<AppState>) {

        window.setTimeout(
            {
                // Update the model.
                revHistory.update(action.description) {
                    action.apply(appState)
                }

                // Compute the new view (virtual DOM).
                val oldAppVdomNode = appVdomNode
                appVdomNode = view(appState, { nextAction -> dispatch(nextAction) })

                // Patch the new view into the real DOM.
                lifecycle.update(appElement, oldAppVdomNode, appVdomNode)
            },
            0
        )

    }

    // Create the initial virtual view. Establish dispatching of events for subsequent updates inside dispatch(..).
    appVdomNode = view(appState) { action -> dispatch(action) }

    // Build the DOM to match the initial view.
    appElement = lifecycle.build(appElement, appVdomNode)

}
