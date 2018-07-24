//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.listitems

import o.org.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import o.org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import o.org.barlom.domain.metamodel.api.vertices.EdgeAttributeType
import o.org.barlom.domain.metamodel.api.vertices.VertexAttributeType
import js.org.barlom.presentation.client.messages.Message
import o.org.katydom.application.katyDomComponent
import o.org.katydom.application.katyDomListItemsComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.builders.lists.KatyDomOrderedListContentBuilder

/** Generates the path to the focused element. */
fun viewFocusedElementPath(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: AbstractNamedElement?
) = katyDomComponent(builder) {

    if (focusedElement == null) {
        text("Choose an element in the left panel.")
    }
    else {

        @Suppress("RedundantUnitReturnType")
        fun viewPath(
            builder: KatyDomOrderedListContentBuilder<Message>,
            element: AbstractNamedElement,
            bold: Boolean = false
        ): Unit = katyDomListItemsComponent(builder) {

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