//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.leftpanels.search

import o.org.barlom.domain.metamodel.api.model.Model
import js.org.barlom.presentation.client.messages.Message
import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Generates the search panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewSearchPanel(
    builder: KatyDomFlowContentBuilder<Message>,
    m: Model
) = katyDomComponent(builder) {

    div(".o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        text("Search (TBD)")

    }

}