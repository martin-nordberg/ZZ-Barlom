//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels.browse

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.presentation.client.messages.Message
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Generates the browse panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewBrowsePanel(
    builder: KatyDomFlowContentBuilder<Message>,
    m: Model,
    browsePanelState: BrowsePanelState
) = katyDomComponent(builder) {

    comment("The Browse Panel", "c1")

    div("#browse-panel.o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        ul("#package-list.c-tree") {

            viewRootPackageTreeItem(this, m.rootPackage, browsePanelState)

        }

    }

}
