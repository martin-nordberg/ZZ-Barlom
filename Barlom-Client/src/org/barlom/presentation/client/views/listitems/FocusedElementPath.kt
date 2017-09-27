//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.presentation.client.actions.IUiAction
import org.katydom.api.katyDomComponent
import org.katydom.api.katyDomListItemComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder

/** Generates the path to the focused element. */
fun viewFocusedElementPath(
    builder: KatyDomFlowContentBuilder,
    focusedElement: AbstractPackagedElement?,
    revDispatchUi: (makeUiAction: () -> IUiAction) -> Unit
) = katyDomComponent(builder) {

    if (focusedElement == null) {
        text("Choose an element in the left panel.")
    }
    else {

        @Suppress("RedundantUnitReturnType")
        fun viewPath(
            builder: KatyDomListItemContentBuilder,
            element: AbstractPackagedElement,
            bold: Boolean = false
        ): Unit = katyDomListItemComponent(builder) {

            if (element.parents.isNotEmpty()) {
                viewPath(builder, element.parents[0])
            }

            li(".c-breadcrumbs__crumb") {

                classes("c-text--loud" to bold)

                viewListItem(this, element, revDispatchUi)

            }

        }

        ol(".c-breadcrumbs") {
            viewPath(this, focusedElement, true)
        }

    }

}