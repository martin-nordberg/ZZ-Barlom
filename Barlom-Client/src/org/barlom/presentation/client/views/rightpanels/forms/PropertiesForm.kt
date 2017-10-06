//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.forms

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.rename
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.presentation.client.actions.UiAction
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

fun viewPropertiesForm(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractPackagedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    val oldName = focusedElement.name

    form {

        // TODO: treat root elements differently

        label("#properties-form.c-label.o-form-element.form--properties") {

            text("Name:")

            inputText(
                ".c-field.c-field--label",
                key = "name-" + focusedElement.id,
                placeholder = "enter lowercase name",
                style = "width:50em",
                value = focusedElement.name
            ) {

                onblur { event ->

                    val newName: String = event.target.asDynamic().value

                    if (newName != oldName) {
                        revDispatchModel(rename(focusedElement, newName))
                    }

                }

            }

        }

    }

}