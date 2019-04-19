//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.application

import js.katydid.vdom.api.KatydidApplication
import js.katydid.vdom.api.KatydidApplicationCycle
import o.barlom.infrastructure.revisions.RevisionHistory
import o.barlom.presentation.client.ApplicationState
import js.barlom.presentation.client.messages.ActionMessage
import js.barlom.presentation.client.messages.Message
import js.barlom.presentation.client.views.leftpanels.browse.viewBrowsePanel
import js.barlom.presentation.client.views.leftpanels.favorites.viewFavoritesPanel
import js.barlom.presentation.client.views.leftpanels.search.viewSearchPanel
import js.barlom.presentation.client.views.leftpanels.viewLeftPanelNavItem
import js.barlom.presentation.client.views.listitems.viewFocusedElementPath
import js.barlom.presentation.client.views.rightpanels.forms.viewPropertiesForm
import js.barlom.presentation.client.views.rightpanels.links.viewRelatedElements
import js.barlom.presentation.client.views.rightpanels.viewRightPanelNavItem
import o.barlom.presentation.client.state.leftpanels.ELeftPanelType
import o.barlom.presentation.client.state.rightpanels.ERightPanelType
import o.katydid.vdom.builders.KatydidFlowContentBuilder

class Application : KatydidApplication<ApplicationState, Message> {

    override fun initialize(): KatydidApplicationCycle<ApplicationState, Message> {

        // Create the revision history for the application.
        val revHistory = RevisionHistory("Initial model")

        // Initialize the model.
        lateinit var result: ApplicationState
        revHistory.update {

            result = ApplicationState()

            val m = result.model

            m.apply {

                val root = rootPackage

                val pkg1 = makePackage {
                    name = "pkg1"
                    makePackageContainment(root, this)
                }

                val pkg1a = makePackage {
                    name = "pkg1a"
                    makePackageContainment(pkg1, this)
                }

                val pkg1ai = makePackage {
                    name = "pkg1ai"
                    makePackageContainment(pkg1a, this)
                }

                val pkg1aii = makePackage {
                    name = "pkg1aii"
                    makePackageContainment(pkg1a, this)
                }

                val VertexTypeA = makeVertexType {
                    name = "VertexTypeA"
                    makeVertexTypeContainment(pkg1a, this)
                }

                val VertexTypeB = makeVertexType {
                    name = "VertexTypeB"
                    makeVertexTypeContainment(pkg1a, this)
                }

                val UndirectedEdgeTypeX = makeUndirectedEdgeType {
                    name = "UndirectedEdgeTypeX"
                    makeUndirectedEdgeTypeContainment(pkg1a, this)
                }

                makeUndirectedEdgeTypeConnectivity(UndirectedEdgeTypeX, VertexTypeA)

                val UndirectedEdgeTypeY = makeUndirectedEdgeType {
                    name = "UndirectedEdgeTypeY"
                    makeUndirectedEdgeTypeContainment(pkg1a, this)
                }

                makeUndirectedEdgeTypeConnectivity(UndirectedEdgeTypeY, VertexTypeB)

                val DirectedEdgeTypeP = makeDirectedEdgeType {
                    name = "DirectedEdgeTypeP"
                    makeDirectedEdgeTypeContainment(pkg1a, this)
                }

                makeDirectedEdgeTypeHeadConnectivity(DirectedEdgeTypeP, VertexTypeA)
                makeDirectedEdgeTypeTailConnectivity(DirectedEdgeTypeP, VertexTypeB)

                val DirectedEdgeTypeQ = makeDirectedEdgeType {
                    name = "DirectedEdgeTypeQ"
                    makeDirectedEdgeTypeContainment(pkg1a, this)
                }

                makeDirectedEdgeTypeHeadConnectivity(DirectedEdgeTypeQ, VertexTypeB)
                makeDirectedEdgeTypeTailConnectivity(DirectedEdgeTypeQ, VertexTypeA)

                val pkg1b = makePackage {
                    name = "pkg1b"
                    makePackageContainment(pkg1, this)
                }

                val pkg1bi = makePackage {
                    name = "pkg1bi"
                    makePackageContainment(pkg1b, this)
                }

                val pkg1bii = makePackage {
                    name = "pkg1bii"
                    makePackageContainment(pkg1b, this)
                }

                val pkg2 = makePackage {
                    name = "pkg2"
                    makePackageContainment(root, this)
                }

                val pkg3 = makePackage {
                    name = "pkg3"
                    makePackageContainment(root, this)
                }

                makePackageDependency(pkg2, pkg1)
                makePackageDependency(pkg3, pkg1)

            }
            "Initialized application state."
        }

        return KatydidApplicationCycle(result)

    }

    override fun update(applicationState: ApplicationState, message: Message): KatydidApplicationCycle<ApplicationState, Message> {

        if (message is ActionMessage) {
            val actionDescription = message.executeAction(applicationState)
            console.log(actionDescription)
            return KatydidApplicationCycle(applicationState)
        }

        TODO("Update function not yet implemented for non-action messages")

    }

    override fun view(applicationState: ApplicationState): KatydidFlowContentBuilder<Message>.() -> Unit {

        val m = applicationState.model
        val ui = applicationState.uiState
        val revHistory = applicationState.revHistory

        return {

            revHistory.review {

                main("#BarlomMetamodelingEnvironment.o-grid.o-grid--no-gutter.o-panel.u-small") {

                    div("#left-panel-container.o-grid__cell--width-20.o-panel-container") {

                        nav("#left-navigation.c-nav.c-nav--inline") {

                            viewLeftPanelNavItem(this, ELeftPanelType.BROWSE, ui.leftPanelType)
                            viewLeftPanelNavItem(this, ELeftPanelType.FAVORITES, ui.leftPanelType)
                            viewLeftPanelNavItem(this, ELeftPanelType.SEARCH, ui.leftPanelType)

                        }

                        when (ui.leftPanelType) {
                            ELeftPanelType.BROWSE    ->
                                viewBrowsePanel(this, m, ui.browsePanelState)
                            ELeftPanelType.FAVORITES ->
                                viewFavoritesPanel(this, m)
                            ELeftPanelType.SEARCH    ->
                                viewSearchPanel(this, m)
                        }

                    }

                    div("#right-panel-container.o-grid__cell--width-80.o-panel-container") {

                        nav("#right-navigation.c-nav.c-nav--inline.c-nav--light") {

                            div("#focused-element-container.c-nav__content") {
                                viewFocusedElementPath(this, ui.focusedElement)
                            }

                            viewRightPanelNavItem(this, ERightPanelType.SETTINGS)
                            viewRightPanelNavItem(this, ERightPanelType.HELP)

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

}
