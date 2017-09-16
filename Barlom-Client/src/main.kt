//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.infrastructure.uuids.prefetchUuid
import org.barlom.presentation.model.ApplicationState
import org.barlom.presentation.tests.runTests
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom
import org.katydom.api.makeKatyDomLifecycle
import kotlin.browser.document
import kotlin.browser.window


/**
 * Main entry point for the Barlom Metamodeling client application.
 */
fun main(args: Array<String>) {

    if (runTests()) {
        return
    }


    // Find the root application DOM element to put the app into (failing if not found).
    var appElement = document.getElementById("app")!!

    // Start up the UUID machinery.
    prefetchUuid()

    // Initialize the model.
    val appState = initializeAppState()

    // Create the KatyDOM lifecycle for build and patching the view.
    val lifecycle = makeKatyDomLifecycle()

    // Start with an empty div just to avoid nullable node type.
    var appVdomNode = katyDom {
        div("#application") {}
    } as KatyDomHtmlElement

    /**
     * Dispatches an action triggered by an event in the latest edition of the view.
     */
    fun dispatch(action: IAction) {

        window.setTimeout(
            {
                // Update the model.
                action.apply(appState)

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


    console.log("Lifecycle started.")

}


/**
 * Creates and initializes the application state.
 */
fun initializeAppState(): ApplicationState {

    val result = ApplicationState()

    val m = result.model

    m.revHistory.update("Initialized model") {

        val root = m.rootPackage

        val pkg1 = m.makePackage {
            name = "pkg1"
            m.makePackageContainment(root, this)
        }

        val pkg2 = m.makePackage {
            name = "pkg2"
            m.makePackageContainment(root, this)
        }

        val pkg3 = m.makePackage {
            name = "pkg3"
            m.makePackageContainment(root, this)
        }

        m.makePackageDependency(pkg2, pkg1)
        m.makePackageDependency(pkg3, pkg1)

    }

    return result

}


/**
 * Interface defining an action against the application state as a whole.
 */
interface IAction {

    fun apply(appState: ApplicationState)

}

/**
 * Sample concrete action to add a package to a given package.
 */
class AddPackage(val parentPackage: Package) : IAction {

    override fun apply(appState: ApplicationState) {
        appState.model.revHistory.update("Add package") {
            appState.model.makePackage() {
                appState.model.makePackageContainment(parentPackage, this)
            }
        }
    }

}


/**
 * Generates the view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun view(appState: ApplicationState, dispatch: (action: IAction) -> Unit): KatyDomHtmlElement {

    val m = appState.model

    /**
     * Creates and dispatches an action inside a rev history transaction.
     */
    fun revDispatch(makeAction:()->IAction) {
        m.revHistory.review {
            val action = makeAction()
            dispatch( action )
        }
    }

    // Sample view, much TBD
    return katyDom {

        m.revHistory.review {

            div("#BarlomMetamodelingEnvironment") {

                ul("#package-list") {

                    for (pkg in m.rootPackage.children) {
                        li { text(pkg.name) }
                    }

                    li {
                        onclick {
                            revDispatch { AddPackage(m.rootPackage) }
                            console.log("Clicked")
                        }
                        text("New Package")
                    }

                }

            }

        }

    } as KatyDomHtmlElement

}
