//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.views.listitems

import o.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import o.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import o.barlom.domain.metamodel.api.vertices.EdgeAttributeType
import o.barlom.domain.metamodel.api.vertices.VertexAttributeType
import js.barlom.presentation.client.messages.Message
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.application.katydidListItemsComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.katydid.vdom.builders.lists.KatydidOrderedListContentBuilder

/** Generates the path to the focused element. */
fun viewFocusedElementPath(
    builder: KatydidFlowContentBuilder<Message>,
    focusedElement: AbstractNamedElement?
) = katydidComponent(builder) {

    if (focusedElement == null) {
        text("Choose an element in the left panel.")
    }
    else {

        @Suppress("RedundantUnitReturnType")
        fun viewPath(
            builder: KatydidOrderedListContentBuilder<Message>,
            element: AbstractNamedElement,
            bold: Boolean = false
        ): Unit = katydidListItemsComponent(builder) {

            when (element) {
                is AbstractPackagedElement ->
                    if (element.parents.isNotEmpty()) {
                        viewPath(builder, element.parents[0])
                    }
                is EdgeAttributeType       ->
                    if (element.edgeTypes.isNotEmpty()) {
                        viewPath(builder, element.edgeTypes[0])
                    }
                is VertexAttributeType     ->
                    if (element.vertexTypes.isNotEmpty()) {
                        viewPath(builder, element.vertexTypes[0])
                    }
            }

            li(".c-breadcrumbs__crumb", element.id) {

                classes("c-text--loud" to bold)

                viewListItem(this, element)

                if (bold) {
                    span("c-text") {
                        text(" : ${element::class.simpleName}")
                    }
                }

            }

        }

        ol("#focused-element-path.c-breadcrumbs") {
            viewPath(this, focusedElement, true)
        }


    }

}
