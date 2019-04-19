//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.views.leftpanels.favorites

import o.barlom.domain.metamodel.api.model.Model
import js.barlom.presentation.client.messages.Message
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder

/**
 * Generates the favorites panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewFavoritesPanel(
    builder: KatydidFlowContentBuilder<Message>,
    m: Model
) = katydidComponent(builder) {

    div(".o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        text("Favorites (TBD)")

    }

}
