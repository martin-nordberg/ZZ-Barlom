//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.views.listitems

import o.barlom.domain.metamodel.api.vertices.*
import o.barlom.presentation.client.actions.GeneralActions
import js.barlom.presentation.client.messages.Message
import js.barlom.presentation.client.messages.UiActionMessage
import o.katydid.events.eventhandling.onclick
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.application.katydidPhrasingComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.katydid.vdom.builders.KatydidPhrasingContentBuilder
import kotlin.reflect.KClass


/**
 * Generates the icon and clickable name for an abstract [element].
 */
fun viewListItem(
    builder: KatydidFlowContentBuilder<Message>,
    element: AbstractNamedElement
) = katydidComponent(builder) {

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
    builder: KatydidPhrasingContentBuilder<Message>,
    elementClass: KClass<T>,
    isRoot: Boolean
) = katydidPhrasingComponent(builder) {

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


