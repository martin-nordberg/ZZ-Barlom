//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.forms

import org.barlom.domain.metamodel.api.actions.*
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.viewcomponents.RadioConfig
import org.barlom.presentation.client.viewcomponents.viewRadioGroup
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

        when (focusedElement) {

            is VertexType -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
            }

            is UndirectedEdgeType -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
                viewCyclicityField(this, revDispatchModel, focusedElement, isRoot)
                viewMultiEdgednessField(this, revDispatchModel, focusedElement, isRoot)
                viewSelfLoopingField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxDegreeFields(this, revDispatchModel, focusedElement, isRoot)
            }

            is DirectedEdgeType -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
                viewCyclicityField(this, revDispatchModel, focusedElement, isRoot)
                viewMultiEdgednessField(this, revDispatchModel, focusedElement, isRoot)
                viewSelfLoopingField(this, revDispatchModel, focusedElement, isRoot)
            }

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

private fun viewMinMaxDegreeFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: UndirectedEdgeType,
    isRoot: Boolean
) = katyDomComponent(builder) {

    val oldMinDegree = edgeType.minDegree
    val oldMaxDegree = edgeType.maxDegree


    fieldset("#degree-field.o-fieldset") {

        legend() {
            text("Degree")
        }

        div(
            "#degree-block.c-input-group",
            style = "width:20em;"
        ) {

            div(
                "#mindegree-field.o-field"
            ) {

                inputNumber(
                    ".c-field",
                    key = "mindegree-" + edgeType.id,
                    disabled = isRoot,
                    min = 0,
                    max = edgeType.maxDegree,
                    placeholder = "minimum",
                    value = edgeType.minDegree
                ) {

                    onblur { event ->

                        val newMinDegree: Int = event.target.asDynamic().value

                        if (newMinDegree != oldMinDegree) {
                            revDispatchModel(UndirectedEdgeTypeActions.changeMinDegree(edgeType, newMinDegree))
                        }

                    }

                }

            }

            div(
                "#maxdegree-field.o-field"
            ) {

                inputNumber(
                    ".c-field",
                    key = "maxdegree-" + edgeType.id,
                    disabled = isRoot,
                    min = edgeType.minDegree ?: 0,
                    placeholder = "maximum",
                    value = edgeType.maxDegree
                ) {

                    onblur { event ->

                        val newMaxDegree: Int = event.target.asDynamic().value

                        if (newMaxDegree != oldMaxDegree) {
                            revDispatchModel(UndirectedEdgeTypeActions.changeMaxDegree(edgeType, newMaxDegree))
                        }

                    }

                }

            }

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

