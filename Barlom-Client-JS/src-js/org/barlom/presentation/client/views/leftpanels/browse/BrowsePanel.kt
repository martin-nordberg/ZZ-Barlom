//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels.browse

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.model.Model
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelAction
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Generates the browse panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewBrowsePanel(
    builder: KatyDomFlowContentBuilder,
    m: Model,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    fun revDispatchBrowse(browseAction: BrowsePanelAction) {
        revDispatchUi { uiState -> browseAction(uiState.browsePanelState) }
    }

    div("#browse-panel.o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        ul("#package-list.c-tree") {

            viewRootPackageTreeItem(this, m.rootPackage, browsePanelState,
                                    revDispatchModel, revDispatchUi, ::revDispatchBrowse)

        }

    }

}
