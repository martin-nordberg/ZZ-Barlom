//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.GeneralActions
import org.barlom.presentation.client.actions.UiAction
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder


/**
 * Generates the icon and clickable name for an abstract [element].
 */
fun viewListItem(
    builder: KatyDomFlowContentBuilder,
    element: AbstractPackagedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    span(".c-link", element.id) {

        onclick { e ->
            revDispatchUi(GeneralActions.focus(element))
            e.stopPropagation()
        }

        span(".mdi", "icon") {

            classes(
                "mdi-ray-start-arrow directed-edge-type" to (element is DirectedEdgeType),
                "mdi-folder package-icon" to (element is Package && !element.isRoot),
                "mdi-book-open root-package-icon" to (element is Package && element.isRoot),
                "mdi-ray-start-end undirected-edge-type-icon" to (element is UndirectedEdgeType),
                "mdi-ray-vertex vertex-type-icon" to (element is VertexType)
            )
        }

        text(" " + element.name)

    }

}


