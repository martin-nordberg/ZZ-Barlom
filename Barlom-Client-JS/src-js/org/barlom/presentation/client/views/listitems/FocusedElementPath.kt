//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.AbstractNamedElement
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices.EdgeAttributeType
import org.barlom.domain.metamodel.api.vertices.VertexAttributeType
import org.barlom.presentation.client.messages.Message
import org.katydom.api.katyDomComponent
import org.katydom.api.katyDomListItemComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder

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
            builder: KatyDomListItemContentBuilder<Message>,
            element: AbstractNamedElement,
            bold: Boolean = false
        ): Unit = katyDomListItemComponent(builder) {

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