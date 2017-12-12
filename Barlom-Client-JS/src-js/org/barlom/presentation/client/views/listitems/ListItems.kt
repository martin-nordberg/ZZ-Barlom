//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.GeneralActions
import org.barlom.presentation.client.actions.UiAction
import org.katydom.api.katyDomComponent
import org.katydom.api.katyDomPhrasingComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import kotlin.reflect.KClass


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

        viewListItemIcon(this, element::class, element is Package && element.isRoot)

        text(" " + element.name)

    }

}


fun <T : AbstractPackagedElement> viewListItemIcon(
    builder: KatyDomPhrasingContentBuilder,
    elementClass: KClass<T>,
    isRoot: Boolean
) = katyDomPhrasingComponent(builder) {

    span(".mdi", "icon") {

        classes(
            "mdi-square-outline constrained-string-icon" to (elementClass == ConstrainedString::class),
            "mdi-ray-start-arrow directed-edge-type-icon" to (elementClass == DirectedEdgeType::class),
            "mdi-folder package-icon" to (elementClass == Package::class && !isRoot),
            "mdi-book-open root-package-icon" to (elementClass == Package::class && isRoot),
            "mdi-ray-start-end undirected-edge-type-icon" to (elementClass == UndirectedEdgeType::class),
            "mdi-ray-vertex vertex-type-icon" to (elementClass == VertexType::class)
        )

    }

}


