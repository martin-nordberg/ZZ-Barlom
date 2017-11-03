//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client

import org.barlom.infrastructure.revisions.RevisionHistory
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom
import org.katydom.api.makeKatyDomLifecycle
import kotlin.browser.document
import kotlin.browser.window

/**
 * Runs the application through its Redux-ish/Elm-ish lifecycle.
 * @param appId the DOM id of the element to replace with the whole application.
 * @param initializeAppState a function that initializes the application state.
 * @param view a function that computes the view from the application state.
 */
fun <AppState : Any> runApplication(
    appId: String,
    initializeAppState: () -> AppState,
    view: (appState: AppState, dispatch: (action: (AppState) -> String) -> Unit) -> KatyDomHtmlElement
) {

    // Find the root application DOM element to put the app into (failing if not found).
    var appElement = document.getElementById(appId)!!

    // Create the revision history for the application.
    val revHistory = RevisionHistory("Initial model")

    // Initialize the model.
    lateinit var appState: AppState
    revHistory.update {
        appState = initializeAppState()
        "Initialized application state."
    }

    // Create the KatyDOM lifecycle for build and patching the view.
    val lifecycle = makeKatyDomLifecycle()

    // Start with an empty div just to avoid nullable node type.
    var appVdomNode = katyDom {
        div("#application") {}
    } as KatyDomHtmlElement

    // Keep a queue of actions to reduce view recomputation when multiple action fire in sequence.
    val queuedActions: MutableList<(AppState) -> String> = mutableListOf()

    /**
     * Dispatches an [action] triggered by an event in the latest edition of the view.
     */
    fun dispatch(action: (AppState) -> String) {

        console.log("Dispatching...")

        // Queue the action for execution when next idle.
        queuedActions.add(action)

        // If we already had something queued, then we already triggered the processing.
        if (queuedActions.size > 1) {
            return
        }

        window.setTimeout(
            {

                for (queuedAction in queuedActions) {

                    // Update the model.
                    revHistory.update() {

                        val description = queuedAction(appState)

                        console.log("ACTION: ", description)

                        description
                    }

                }

                // Empty the queue.
                queuedActions.clear()

                // Compute the new view (virtual DOM).
                val oldAppVdomNode = appVdomNode
                appVdomNode = view(appState, ::dispatch)

                // Patch the new view into the real DOM.
                lifecycle.update(appElement, oldAppVdomNode, appVdomNode)

            },
            0
        )

    }

    // Create the initial virtual view. Establish dispatching of events for subsequent updates inside dispatch(..).
    appVdomNode = view(appState, ::dispatch)

    // Build the DOM to match the initial view.
    appElement = lifecycle.build(appElement, appVdomNode)

}
