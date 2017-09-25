//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels

import org.barlom.domain.metamodel.api.actions.IModelAction
import org.barlom.domain.metamodel.api.model.Model
import org.katydom.api.katyDomComponent

/**
 * Generates the favorites panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewFavoritesPanel(
    m: Model,
    revDispatchModel: (makeModelAction: () -> IModelAction) -> Unit
) = katyDomComponent {

    div(".o-panel.o-panel--nav-top.u-pillar-box--small.barlom-favorites-panel") {

        text("Favorites (TBD)")

    }

}