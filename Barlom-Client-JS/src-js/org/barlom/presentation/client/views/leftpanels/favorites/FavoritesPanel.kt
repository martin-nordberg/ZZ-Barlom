//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels.favorites

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.model.Model
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Generates the favorites panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewFavoritesPanel(
    builder: KatyDomFlowContentBuilder,
    m: Model,
    revDispatchModel: (modelAction: ModelAction) -> Unit
) = katyDomComponent(builder) {

    div(".o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        text("Favorites (TBD)")

    }

}