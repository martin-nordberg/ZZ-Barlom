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
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.UiAction
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.w3c.dom.events.Event

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
            viewCyclicityField(this, revDispatchModel, focusedElement, isRoot)
            viewMultiEdgednessField(this, revDispatchModel, focusedElement, isRoot)
            viewSelfLoopingField(this, revDispatchModel, focusedElement, isRoot)
        }

    }

}

private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    vertexType: VertexType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldAbstractness = vertexType.abstractness

    viewRadioGroup(
        this,
        "abstractness",
        "Abstract?",
        vertexType.id.toString(),
        vertexType.abstractness,
        listOf(
            RadioConfig(isRoot, EAbstractness.ABSTRACT, "Abstract"),
            RadioConfig(isRoot, EAbstractness.CONCRETE, "Concrete")
        )
    ) { event: Event ->

        val newAbstractness = EAbstractness.valueOf(event.target.asDynamic().value)

        if (newAbstractness != oldAbstractness) {
            revDispatchModel(VertexTypeActions.changeAbstractness(vertexType, newAbstractness))
        }

    }

}

private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldAbstractness = edgeType.abstractness

    viewRadioGroup(
        this,
        "abstractness",
        "Abstract?",
        edgeType.id.toString(),
        edgeType.abstractness,
        listOf(
            RadioConfig(isRoot, EAbstractness.ABSTRACT, "Abstract"),
            RadioConfig(isRoot, EAbstractness.CONCRETE, "Concrete")
        )
    ) { event: Event ->

        val newAbstractness = EAbstractness.valueOf(event.target.asDynamic().value)

        if (newAbstractness != oldAbstractness) {
            revDispatchModel(AbstractEdgeTypeActions.changeAbstractness(edgeType, newAbstractness))
        }

    }

}

private fun viewCyclicityField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldCyclicity = edgeType.cyclicity

    viewRadioGroup(
        this,
        "cyclicity",
        "Cycles?",
        edgeType.id.toString(),
        edgeType.cyclicity,
        listOf(
            RadioConfig(isRoot, ECyclicity.ACYCLIC, "Allowed"),
            RadioConfig(isRoot, ECyclicity.POTENTIALLY_CYCLIC, "Not Allowed"),
            RadioConfig(isRoot || edgeType.abstractness.isConcrete(), ECyclicity.UNCONSTRAINED, "Unconstrained")
        )
    ) { event: Event ->

        val newCyclicity = ECyclicity.valueOf(event.target.asDynamic().value)

        if (newCyclicity != oldCyclicity) {
            revDispatchModel(AbstractEdgeTypeActions.changeCyclicity(edgeType, newCyclicity))
        }

    }

}

private fun viewMultiEdgednessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldMultiEdgedness = edgeType.multiEdgedness

    viewRadioGroup(
        this,
        "multiedgedness",
        "Multi-Edges?",
        edgeType.id.toString(),
        edgeType.multiEdgedness,
        listOf(
            RadioConfig(isRoot, EMultiEdgedness.MULTI_EDGES_ALLOWED, "Allowed"),
            RadioConfig(isRoot, EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED, "Not Allowed"),
            RadioConfig(isRoot || edgeType.abstractness.isConcrete(), EMultiEdgedness.UNCONSTRAINED, "Unconstrained")
        )
    ) { event: Event ->

        val newMultiEdgedness = EMultiEdgedness.valueOf(event.target.asDynamic().value)

        if (newMultiEdgedness != oldMultiEdgedness) {
            revDispatchModel(AbstractEdgeTypeActions.changeMultiEdgedness(edgeType, newMultiEdgedness))
        }

    }

}

private fun viewSelfLoopingField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldSelfLooping = edgeType.selfLooping

    viewRadioGroup(
        this,
        "selflooping",
        "Self Looping?",
        edgeType.id.toString(),
        edgeType.selfLooping,
        listOf(
            RadioConfig(isRoot, ESelfLooping.SELF_LOOPS_ALLOWED, "Allowed"),
            RadioConfig(isRoot, ESelfLooping.SELF_LOOPS_NOT_ALLOWED, "Not Allowed"),
            RadioConfig(isRoot || edgeType.abstractness.isConcrete(), ESelfLooping.UNCONSTRAINED, "Unconstrained")
        )
    ) { event: Event ->

        val newSelfLooping = ESelfLooping.valueOf(event.target.asDynamic().value)

        if (newSelfLooping != oldSelfLooping) {
            revDispatchModel(AbstractEdgeTypeActions.changeSelfLooping(edgeType, newSelfLooping))
        }

    }

}

private fun viewNameField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    namedElement: AbstractNamedElement,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldName = namedElement.name

    label("#name-field.c-label.o-form-element") {

        text("Name:")

        inputText(
            ".c-field.c-field--label",
            key = "name-" + namedElement.id,
            disabled = isRoot,
            placeholder = "enter lowercase name",
            style = "width:50em",
            value = namedElement.name
        ) {

            onblur { event ->

                val newName: String = event.target.asDynamic().value

                if (newName != oldName) {
                    revDispatchModel(NamedElementActions.rename(namedElement, newName))
                }

            }

        }

    }

}

data class RadioConfig<T>(val disabled: Boolean, val value: T, val label: String)

private fun <T> viewRadioGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    id: String,
    currentValue: T,
    radios: List<RadioConfig<T>>,
    handleChange: (Event) -> Unit
) = katyDomComponent(builder) {

    fieldset("#" + name + "-fieldset.o-fieldset.c-list.c-list--inline.c-list--unstyled") {

        legend("#" + name + "-legend.o-fieldset__legend") {
            text(legend)
        }

        for (radio in radios) {

            val key = radio.value.toString().toLowerCase()
            val value = radio.value.toString()

            label("#" + key + "-label.c-field.c-field--choice.c-list__item") {

                inputRadioButton(
                    key = key + "-" + id,
                    checked = radio.value == currentValue,
                    disabled = radio.disabled,
                    name = name,
                    value = value
                ) {
                    onchange(handleChange)
                }

                text(radio.label)

            }

        }

    }

}

