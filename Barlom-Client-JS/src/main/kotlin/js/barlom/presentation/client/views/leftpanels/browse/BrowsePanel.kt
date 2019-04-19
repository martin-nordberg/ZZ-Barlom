//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.views.leftpanels.browse

import o.barlom.domain.metamodel.api.model.Model
import js.barlom.presentation.client.messages.Message
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState

/**
 * Generates the browse panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewBrowsePanel(
    builder: KatydidFlowContentBuilder<Message>,
    m: Model,
    browsePanelState: BrowsePanelState
) = katydidComponent(builder) {

    comment("The Browse Panel", "c1")

    div("#browse-panel.o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        ul("#package-list.c-tree") {

            viewRootPackageTreeItem(this, m.rootPackage, browsePanelState)

        }

    }

}
