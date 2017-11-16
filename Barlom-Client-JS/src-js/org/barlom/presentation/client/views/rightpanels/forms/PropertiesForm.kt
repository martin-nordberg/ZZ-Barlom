//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.forms

import org.barlom.domain.metamodel.api.actions.AbstractEdgeTypeActions
import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.NamedElementActions
import org.barlom.domain.metamodel.api.actions.VertexTypeActions
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.vertices.AbstractEdgeType
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.domain.metamodel.api.vertices.VertexType
import org.barlom.presentation.client.actions.UiAction
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

fun viewPropertiesForm(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractPackagedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    val isRoot = focusedElement is Package && focusedElement.isRoot ||
        focusedElement is VertexType && focusedElement.isRoot ||
        focusedElement is AbstractEdgeType && focusedElement.isRoot

    form("#properties-form.form--properties", action = "javascript:void(0);") {

        // Name
        viewNameField(this, revDispatchModel, focusedElement, isRoot)

        // Abstractness
        if (focusedElement is VertexType) {
            viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
        }
        else if (focusedElement is AbstractEdgeType) {
            viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
        }

    }

}

private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: VertexType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldAbstractness = focusedElement.abstractness

    fieldset("#abstractness-fieldset.o-fieldset.c-list.c-list--inline.c-list--unstyled") {

        legend("#abstractness-legend.o-fieldset__legend") {
            text("Abstract?")
        }

        label("#abstract-label.c-field.c-field--choice.c-list__item") {

            inputRadioButton(
                key = "abstract-" + focusedElement.id,
                checked = focusedElement.abstractness.isAbstract(),
                disabled = isRoot,
                name = "abstractness",
                value = EAbstractness.ABSTRACT.toString()
            ) {

                onchange { event ->

                    val newAbstractness = EAbstractness.valueOf(event.target.asDynamic().value)

                    if (newAbstractness != oldAbstractness) {
                        revDispatchModel(VertexTypeActions.changeAbstractness(focusedElement, newAbstractness))
                    }

                }

            }

            text("Abstract")

        }

        label("#concrete-label.c-field.c-field--choice.c-list__item") {

            inputRadioButton(
                key = "concrete-" + focusedElement.id,
                checked = focusedElement.abstractness.isConcrete(),
                disabled = isRoot,
                name = "abstractness",
                value = EAbstractness.CONCRETE.toString()
            ) {

                onchange { event ->

                    val newAbstractness = EAbstractness.valueOf(event.target.asDynamic().value)

                    if (newAbstractness != oldAbstractness) {
                        revDispatchModel(VertexTypeActions.changeAbstractness(focusedElement, newAbstractness))
                    }

                }

            }

            text("Concrete")

        }

    }

}

private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractEdgeType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldAbstractness = focusedElement.abstractness

    fieldset("#abstractness-fieldset.o-fieldset.c-list.c-list--inline.c-list--unstyled") {

        legend("#abstractness-legend.o-fieldset__legend") {
            text("Abstract?")
        }

        label("#abstract-label.c-field.c-field--choice.c-list__item") {

            inputRadioButton(
                key = "abstract-" + focusedElement.id,
                checked = focusedElement.abstractness.isAbstract(),
                disabled = isRoot,
                name = "abstractness",
                value = EAbstractness.ABSTRACT.toString()
            ) {

                onchange { event ->

                    val newAbstractness = EAbstractness.valueOf(event.target.asDynamic().value)

                    if (newAbstractness != oldAbstractness) {
                        revDispatchModel(AbstractEdgeTypeActions.changeAbstractness(focusedElement, newAbstractness))
                    }

                }

            }

            text("Abstract")

        }

        label("#concrete-label.c-field.c-field--choice.c-list__item") {

            inputRadioButton(
                key = "concrete-" + focusedElement.id,
                checked = focusedElement.abstractness.isConcrete(),
                disabled = isRoot,
                name = "abstractness",
                value = EAbstractness.CONCRETE.toString()
            ) {

                onchange { event ->

                    val newAbstractness = EAbstractness.valueOf(event.target.asDynamic().value)

                    if (newAbstractness != oldAbstractness) {
                        revDispatchModel(AbstractEdgeTypeActions.changeAbstractness(focusedElement, newAbstractness))
                    }

                }

            }

            text("Concrete")

        }

    }

}

private fun viewNameField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractPackagedElement,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldName = focusedElement.name

    label("#name-field.c-label.o-form-element") {

        text("Name:")

        inputText(
            ".c-field.c-field--label",
            key = "name-" + focusedElement.id,
            disabled = isRoot,
            placeholder = "enter lowercase name",
            style = "width:50em",
            value = focusedElement.name
        ) {

            onblur { event ->

                val newName: String = event.target.asDynamic().value

                if (newName != oldName) {
                    revDispatchModel(NamedElementActions.rename(focusedElement, newName))
                }

            }

        }

    }

}
