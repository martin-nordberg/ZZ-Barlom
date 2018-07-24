//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.listitems

import o.org.barlom.domain.metamodel.api.vertices.*
import o.org.barlom.presentation.client.actions.GeneralActions
import js.org.barlom.presentation.client.messages.Message
import js.org.barlom.presentation.client.messages.UiActionMessage
import o.org.katydom.application.katyDomComponent
import o.org.katydom.application.katyDomPhrasingComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.builders.KatyDomPhrasingContentBuilder
import o.org.katydom.eventhandling.onclick
import kotlin.reflect.KClass


/**
 * Generates the icon and clickable name for an abstract [element].
 */
fun viewListItem(
    builder: KatyDomFlowContentBuilder<Message>,
    element: AbstractNamedElement
) = katyDomComponent(builder) {

    span(".c-link", element.id) {

        onclick { e ->
            e.stopPropagation()
            listOf(UiActionMessage(GeneralActions.focus(element)))
        }

        viewListItemIcon(this, element::class, element is Package && element.isRoot)

        text(" " + element.name)

    }

}


fun <T : AbstractNamedElement> viewListItemIcon(
    builder: KatyDomPhrasingContentBuilder<Message>,
    elementClass: KClass<T>,
    isRoot: Boolean
) = katyDomPhrasingComponent(builder) {

    span(".mdi", "icon") {

        classes(
            "mdi-square-outline constrained-boolean-icon" to (elementClass == ConstrainedBoolean::class),
            "mdi-square-outline constrained-datetime-icon" to (elementClass == ConstrainedDataType::class),
            "mdi-square-outline constrained-datetime-icon" to (elementClass == ConstrainedDateTime::class),
            "mdi-square-outline constrained-float64-icon" to (elementClass == ConstrainedFloat64::class),
            "mdi-square-outline constrained-integer32-icon" to (elementClass == ConstrainedInteger32::class),
            "mdi-square-outline constrained-string-icon" to (elementClass == ConstrainedString::class),
            "mdi-square-outline constrained-uuid-icon" to (elementClass == ConstrainedUuid::class),
            "mdi-ray-start-arrow directed-edge-type-icon" to (elementClass == DirectedEdgeType::class),
            "mdi-square edge-attribute-type-icon" to (elementClass == EdgeAttributeType::class),
            "mdi-folder package-icon" to (elementClass == Package::class && !isRoot),
            "mdi-book-open root-package-icon" to (elementClass == Package::class && isRoot),
            "mdi-ray-start-end undirected-edge-type-icon" to (elementClass == UndirectedEdgeType::class),
            "mdi-ray-vertex vertex-type-icon" to (elementClass == VertexType::class),
            "mdi-square vertex-attribute-type-icon" to (elementClass == VertexAttributeType::class)
        )

    }

}


