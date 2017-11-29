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
import org.barlom.presentation.client.viewcomponents.*
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

        when (focusedElement) {

            is Package            -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
            }

            is VertexType         -> {
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

            is DirectedEdgeType   -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewForwardReverseNameFields(this, revDispatchModel, focusedElement, isRoot)
                viewRoleNameFields(this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
                viewCyclicityField(this, revDispatchModel, focusedElement, isRoot)
                viewMultiEdgednessField(this, revDispatchModel, focusedElement, isRoot)
                viewSelfLoopingField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxHeadInDegreeFields(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxTailOutDegreeFields(this, revDispatchModel, focusedElement, isRoot)
            }

        }

    }

}


private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "abstractness",
    "Abstract?",
    edgeType.abstractness,
    EAbstractness::valueOf,
    listOf(
        RadioConfig(isRoot, EAbstractness.ABSTRACT, "Abstract"),
        RadioConfig(isRoot, EAbstractness.CONCRETE, "Concrete")
    )
) { abstractness ->
    revDispatchModel(AbstractEdgeTypeActions.changeAbstractness(edgeType, abstractness))
}


private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    vertexType: VertexType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "abstractness",
    "Abstract?",
    vertexType.abstractness,
    EAbstractness::valueOf,
    listOf(
        RadioConfig(isRoot, EAbstractness.ABSTRACT, "Abstract"),
        RadioConfig(isRoot, EAbstractness.CONCRETE, "Concrete")
    )
) { abstractness ->
    revDispatchModel(VertexTypeActions.changeAbstractness(vertexType, abstractness))
}


private fun viewCyclicityField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "cyclicity",
    "Cycles?",
    edgeType.cyclicity,
    ECyclicity::valueOf,
    listOf(
        RadioConfig(isRoot, ECyclicity.ACYCLIC, "Allowed"),
        RadioConfig(isRoot, ECyclicity.POTENTIALLY_CYCLIC, "Not Allowed"),
        RadioConfig(isRoot || edgeType.abstractness.isConcrete(), ECyclicity.UNCONSTRAINED, "Unconstrained")
    )
) { cyclicity ->
    revDispatchModel(AbstractEdgeTypeActions.changeCyclicity(edgeType, cyclicity))
}


private fun viewForwardReverseNameFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextGroup(
    builder,
    "directed-names",
    "Directed Names (Forward, Reverse):",
    listOf(
        TextInputConfig(isRoot, edgeType.forwardName, "forward-name", "forward") { forwardName ->
            revDispatchModel(DirectedEdgeTypeActions.changeForwardName(edgeType, forwardName))
        },
        TextInputConfig(isRoot, edgeType.reverseName, "reverse-name", "reverse") { reverseName ->
            revDispatchModel(DirectedEdgeTypeActions.changeReverseName(edgeType, reverseName))
        }
    )
)


private fun viewMinMaxDegreeFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: UndirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "degree",
    "Degree (Minimum, Maximum):",
    IntegerInputConfig(isRoot, edgeType.minDegree, "min-degree", "minimum") { minDegree ->
        revDispatchModel(UndirectedEdgeTypeActions.changeMinDegree(edgeType, minDegree))
    },
    IntegerInputConfig(isRoot, edgeType.maxDegree, "max-degree", "maximum") { maxDegree ->
        revDispatchModel(UndirectedEdgeTypeActions.changeMaxDegree(edgeType, maxDegree))
    }
)


private fun viewMinMaxHeadInDegreeFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "head-in-degree",
    "Head In-Degree (Minimum, Maximum):",
    IntegerInputConfig(isRoot, edgeType.minHeadInDegree, "min-head-in-degree", "minimum") { minHeadInDegree ->
        revDispatchModel(DirectedEdgeTypeActions.changeMinHeadInDegree(edgeType, minHeadInDegree))
    },
    IntegerInputConfig(isRoot, edgeType.maxHeadInDegree, "max-head-in-degree", "maximum") { maxHeadInDegree ->
        revDispatchModel(DirectedEdgeTypeActions.changeMaxHeadInDegree(edgeType, maxHeadInDegree))
    }
)


private fun viewMinMaxTailOutDegreeFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "tail-out-degree",
    "Tail Out-Degree (Minimum, Maximum):",
    IntegerInputConfig(isRoot, edgeType.minTailOutDegree, "min-tail-out-degree", "minimum") { minTailOutDegree ->
        revDispatchModel(DirectedEdgeTypeActions.changeMinTailOutDegree(edgeType, minTailOutDegree))
    },
    IntegerInputConfig(isRoot, edgeType.maxTailOutDegree, "max-tail-out-degree", "maximum") { maxTailOutDegree ->
        revDispatchModel(DirectedEdgeTypeActions.changeMaxTailOutDegree(edgeType, maxTailOutDegree))
    }
)

private fun viewMultiEdgednessField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "multiedgedness",
    "Multi-Edges?",
    edgeType.multiEdgedness,
    EMultiEdgedness::valueOf,
    listOf(
        RadioConfig(isRoot, EMultiEdgedness.MULTI_EDGES_ALLOWED, "Allowed"),
        RadioConfig(isRoot, EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED, "Not Allowed"),
        RadioConfig(isRoot || edgeType.abstractness.isConcrete(), EMultiEdgedness.UNCONSTRAINED, "Unconstrained")
    )
) { multiEdgedness ->
    revDispatchModel(AbstractEdgeTypeActions.changeMultiEdgedness(edgeType, multiEdgedness))
}


private fun viewNameField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    namedElement: AbstractNamedElement,
    isRoot: Boolean
) = viewInputTextField(
    builder,
    "name",
    namedElement.id.toString(),
    "Name:",
    namedElement.name,
    "lowercase name",
    isRoot
) { newName ->
    revDispatchModel(NamedElementActions.rename(namedElement, newName))
}


private fun viewRoleNameFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextGroup(
    builder,
    "role-names",
    "Role Names (Head, Tail):",
    listOf(
        TextInputConfig(isRoot, edgeType.headRoleName, "head-role-name", "head") { headRoleName ->
            revDispatchModel(DirectedEdgeTypeActions.changeHeadRoleName(edgeType, headRoleName))
        },
        TextInputConfig(isRoot, edgeType.tailRoleName, "tail-role-name", "tail") { tailRoleName ->
            revDispatchModel(DirectedEdgeTypeActions.changeTailRoleName(edgeType, tailRoleName))
        }
    )
)


private fun viewSelfLoopingField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "selflooping",
    "Self Looping?",
    edgeType.selfLooping,
    ESelfLooping::valueOf,
    listOf(
        RadioConfig(isRoot, ESelfLooping.SELF_LOOPS_ALLOWED, "Allowed"),
        RadioConfig(isRoot, ESelfLooping.SELF_LOOPS_NOT_ALLOWED, "Not Allowed"),
        RadioConfig(isRoot || edgeType.abstractness.isConcrete(), ESelfLooping.UNCONSTRAINED, "Unconstrained")
    )
) { selfLooping ->
    revDispatchModel(AbstractEdgeTypeActions.changeSelfLooping(edgeType, selfLooping))
}

