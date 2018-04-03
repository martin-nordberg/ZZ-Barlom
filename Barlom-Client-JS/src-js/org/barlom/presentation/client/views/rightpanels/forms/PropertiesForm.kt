//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.forms

import org.barlom.domain.metamodel.api.actions.*
import org.barlom.domain.metamodel.api.types.*
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.messages.Message
import org.barlom.presentation.client.messages.ModelActionMessage
import org.barlom.presentation.client.viewcomponents.*
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder


fun viewPropertiesForm(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: AbstractNamedElement
) = katyDomComponent(builder) {

    val isRoot = focusedElement is Package && focusedElement.isRoot ||
        focusedElement is VertexType && focusedElement.isRoot ||
        focusedElement is AbstractEdgeType && focusedElement.isRoot

    form("#properties-form-${focusedElement.id}.form--properties", action = "javascript:void(0);") {

        when (focusedElement) {

            is ConstrainedBoolean   -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewDefaultValueField(this, focusedElement)
            }

        // TODO: ConstrainedDateTime

            is ConstrainedFloat64   -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewMinMaxValueFields(this, focusedElement)
                viewDefaultValueField(this, focusedElement)
            }

            is ConstrainedInteger32 -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewMinMaxValueFields(this, focusedElement)
                viewDefaultValueField(this, focusedElement)
            }

            is ConstrainedString    -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewMinMaxLengthFields(this, focusedElement)
                viewDefaultValueField(this, focusedElement)
                viewPatternField(this, focusedElement)
            }

            is ConstrainedUuid      -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
            }

            is DirectedEdgeType     -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewSuperTypeField(this, focusedElement, isRoot)
                viewConnectedHeadVertexTypeField(this, focusedElement, isRoot)
                viewConnectedTailVertexTypeField(this, focusedElement, isRoot)
                viewForwardReverseNameFields(this, focusedElement, isRoot)
                viewRoleNameFields(this, focusedElement, isRoot)
                viewAbstractnessField(this, focusedElement, isRoot)
                viewCyclicityField(this, focusedElement, isRoot)
                viewMultiEdgednessField(this, focusedElement, isRoot)
                viewSelfLoopingField(this, focusedElement, isRoot)
                viewMinMaxHeadInDegreeFields(this, focusedElement, isRoot)
                viewMinMaxTailOutDegreeFields(this, focusedElement, isRoot)
            }

            is EdgeAttributeType    -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewDataTypeField(this, focusedElement)
                viewOptionalityField(this, focusedElement)
            }

            is Package              -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
            }

            is UndirectedEdgeType   -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewSuperTypeField(this, focusedElement, isRoot)
                viewConnectedVertexTypeField(this, focusedElement, isRoot)
                viewAbstractnessField(this, focusedElement, isRoot)
                viewCyclicityField(this, focusedElement, isRoot)
                viewMultiEdgednessField(this, focusedElement, isRoot)
                viewSelfLoopingField(this, focusedElement, isRoot)
                viewMinMaxDegreeFields(this, focusedElement, isRoot)
            }

            is VertexAttributeType  -> {
                viewNameField(this, focusedElement, false)
                viewDescriptionField(this, focusedElement, false)
                viewDataTypeField(this, focusedElement)
                viewOptionalityField(this, focusedElement)
                viewLabelDefaultingField(this, focusedElement)
            }

            is VertexType           -> {
                viewNameField(this, focusedElement, isRoot)
                viewDescriptionField(this, focusedElement, isRoot)
                viewSuperTypeField(this, focusedElement, isRoot)
                viewAbstractnessField(this, focusedElement, isRoot)
            }

        }

    }

}


private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder<Message>,
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
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeAbstractness(edgeType, abstractness)))
}


private fun viewAbstractnessField(
    builder: KatyDomFlowContentBuilder<Message>,
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
    listOf<Message>(ModelActionMessage(VertexTypeActions.changeAbstractness(vertexType, abstractness)))
}


private fun viewConnectedHeadVertexTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextFieldWithDataList(
    builder,
    "connected-head-vertex-type",
    edgeType.id.toString(),
    "Connected Head Vertex Type:",
    edgeType.connectedHeadVertexTypes.elementAtOrNull(0)?.path ?: "",
    "connected head vertex type",
    isRoot,
    edgeType.findPotentialConnectedHeadVertexTypes().map { vt -> DataListOptionConfig(vt.id.toString(), vt.path) }
) { newDefaultValue ->
    listOf<Message>(
        ModelActionMessage(DirectedEdgeTypeActions.changeConnectedHeadVertexType(edgeType, newDefaultValue)))
}


private fun viewConnectedTailVertexTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextFieldWithDataList(
    builder,
    "connected-tail-vertex-type",
    edgeType.id.toString(),
    "Connected Tail Vertex Type:",
    edgeType.connectedTailVertexTypes.elementAtOrNull(0)?.path ?: "",
    "connected tail vertex type",
    isRoot,
    edgeType.findPotentialConnectedTailVertexTypes().map { vt -> DataListOptionConfig(vt.id.toString(), vt.path) }
) { newDefaultValue ->
    listOf<Message>(
        ModelActionMessage(DirectedEdgeTypeActions.changeConnectedTailVertexType(edgeType, newDefaultValue)))
}


private fun viewConnectedVertexTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: UndirectedEdgeType,
    isRoot: Boolean
) = viewInputTextFieldWithDataList(
    builder,
    "connected-vertex-type",
    edgeType.id.toString(),
    "Connected Vertex Type:",
    edgeType.connectedVertexTypes.elementAtOrNull(0)?.path ?: "",
    "connected vertex type",
    isRoot,
    edgeType.findPotentialConnectedVertexTypes().map { vt -> DataListOptionConfig(vt.id.toString(), vt.path) }
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(UndirectedEdgeTypeActions.changeConnectedVertexType(edgeType, newDefaultValue)))
}


private fun viewCyclicityField(
    builder: KatyDomFlowContentBuilder<Message>,
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
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeCyclicity(edgeType, cyclicity)))
}


private fun viewDataTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeAttributeType: EdgeAttributeType
) = viewInputTextFieldWithDataList(
    builder,
    "data-type",
    edgeAttributeType.id.toString(),
    "Data Type:",
    edgeAttributeType.dataTypes.elementAtOrNull(0)?.path ?: "",
    "data type",
    false,
    edgeAttributeType.findPotentialDataTypes().map { dt -> DataListOptionConfig(dt.id.toString(), dt.path) }
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(EdgeAttributeTypeActions.changeDataType(edgeAttributeType, newDefaultValue)))
}


private fun viewDataTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    vertexAttributeType: VertexAttributeType
) = viewInputTextFieldWithDataList(
    builder,
    "data-type",
    vertexAttributeType.id.toString(),
    "Data Type:",
    vertexAttributeType.dataTypes.elementAtOrNull(0)?.path ?: "",
    "data type",
    false,
    vertexAttributeType.findPotentialDataTypes().map { dt -> DataListOptionConfig(dt.id.toString(), dt.path) }
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(VertexAttributeTypeActions.changeDataType(vertexAttributeType, newDefaultValue)))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedBoolean: ConstrainedBoolean
) = viewBooleanOrNullRadioGroup(
    builder,
    "default-value",
    "Default Value",
    constrainedBoolean.defaultValue,
    "True",
    "False",
    "No Default"
) { newDefaultValue ->
    listOf<Message>(
        ModelActionMessage(ConstrainedBooleanActions.changeDefaultValue(constrainedBoolean, newDefaultValue)))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedFloat64: ConstrainedFloat64
) = viewInputDoubleField(
    builder,
    "default-value",
    constrainedFloat64.id.toString(),
    "Default Value:",
    constrainedFloat64.defaultValue,
    "default value",
    false
) { newDefaultValue ->
    listOf<Message>(
        ModelActionMessage(ConstrainedFloat64Actions.changeDefaultValue(constrainedFloat64, newDefaultValue)))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedFloat64: ConstrainedInteger32
) = viewInputIntegerField(
    builder,
    "default-value",
    constrainedFloat64.id.toString(),
    "Default Value:",
    constrainedFloat64.defaultValue,
    "default value",
    false
) { newDefaultValue ->
    listOf<Message>(
        ModelActionMessage(ConstrainedInteger32Actions.changeDefaultValue(constrainedFloat64, newDefaultValue)))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedString: ConstrainedString
) = viewInputTextField(
    builder,
    "default-value",
    constrainedString.id.toString(),
    "Default Value:",
    constrainedString.defaultValue ?: "",
    "default value",
    false
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(ConstrainedStringActions.changeDefaultValue(constrainedString, newDefaultValue)))
}


private fun viewDescriptionField(
    builder: KatyDomFlowContentBuilder<Message>,
    element: AbstractDocumentedElement,
    isRoot: Boolean
) = viewTextAreaField(
    builder,
    "description",
    element.id.toString(),
    "Description:",
    element.description,
    "short summary of the element ...",
    isRoot
) { newDescription ->
    listOf<Message>(ModelActionMessage(DocumentedElementActions.describe(element, newDescription)))
}


private fun viewForwardReverseNameFields(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextGroup(
    builder,
    "directed-names",
    "Directed Names (Forward, Reverse):",
    listOf(
        TextInputConfig(isRoot, edgeType.forwardName, "forward-name", "forward") { forwardName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeForwardName(edgeType, forwardName)))
        },
        TextInputConfig(isRoot, edgeType.reverseName, "reverse-name", "reverse") { reverseName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeReverseName(edgeType, reverseName)))
        }
    )
)


private fun viewLabelDefaultingField(
    builder: KatyDomFlowContentBuilder<Message>,
    vertexAttributeType: VertexAttributeType
) = viewRadioGroup(
    builder,
    "labelDefaulting",
    "Default Label for Vertex?",
    vertexAttributeType.labelDefaulting,
    ELabelDefaulting::valueOf,
    listOf(
        RadioConfig(false, ELabelDefaulting.DEFAULT_LABEL, "Yes"),
        RadioConfig(false, ELabelDefaulting.NOT_DEFAULT_LABEL, "No")
    )
) { labelDefaulting ->
    listOf<Message>(
        ModelActionMessage(VertexAttributeTypeActions.changeLabelDefaulting(vertexAttributeType, labelDefaulting)))
}


private fun viewMinMaxDegreeFields(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: UndirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "degree",
    "Degree (Minimum, Maximum):",
    IntegerInputConfig(isRoot, edgeType.minDegree, "min-degree", "minimum") { minDegree ->
        listOf<Message>(ModelActionMessage(UndirectedEdgeTypeActions.changeMinDegree(edgeType, minDegree)))
    },
    IntegerInputConfig(isRoot, edgeType.maxDegree, "max-degree", "maximum") { maxDegree ->
        listOf<Message>(ModelActionMessage(UndirectedEdgeTypeActions.changeMaxDegree(edgeType, maxDegree)))
    }
)


private fun viewMinMaxHeadInDegreeFields(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "head-in-degree",
    "Head In-Degree (Minimum, Maximum):",
    IntegerInputConfig(isRoot, edgeType.minHeadInDegree, "min-head-in-degree", "minimum") { minHeadInDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMinHeadInDegree(edgeType, minHeadInDegree)))
    },
    IntegerInputConfig(isRoot, edgeType.maxHeadInDegree, "max-head-in-degree", "maximum") { maxHeadInDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMaxHeadInDegree(edgeType, maxHeadInDegree)))
    }
)


private fun viewMinMaxLengthFields(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedString: ConstrainedString
) = viewInputIntegerRange(
    builder,
    "length",
    "Length (Minimum, Maximum):",
    IntegerInputConfig(false, constrainedString.minLength, "min-length", "minimum") { minLength ->
        listOf<Message>(ModelActionMessage(ConstrainedStringActions.changeMinLength(constrainedString, minLength)))
    },
    IntegerInputConfig(false, constrainedString.maxLength, "max-length", "maximum") { maxLength ->
        listOf<Message>(ModelActionMessage(ConstrainedStringActions.changeMaxLength(constrainedString, maxLength)))
    }
)


private fun viewMinMaxTailOutDegreeFields(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "tail-out-degree",
    "Tail Out-Degree (Minimum, Maximum):",
    IntegerInputConfig(isRoot, edgeType.minTailOutDegree, "min-tail-out-degree", "minimum") { minTailOutDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMinTailOutDegree(edgeType, minTailOutDegree)))
    },
    IntegerInputConfig(isRoot, edgeType.maxTailOutDegree, "max-tail-out-degree", "maximum") { maxTailOutDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMaxTailOutDegree(edgeType, maxTailOutDegree)))
    }
)


private fun viewMinMaxValueFields(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedFloat64: ConstrainedFloat64
) = viewInputDoubleRange(
    builder,
    "value-limits",
    "Value Limits (Minimum, Maximum):",
    DoubleInputConfig(false, constrainedFloat64.minValue, "min-value", "minimum") { minValue ->
        listOf<Message>(ModelActionMessage(ConstrainedFloat64Actions.changeMinValue(constrainedFloat64, minValue)))
    },
    DoubleInputConfig(false, constrainedFloat64.maxValue, "max-value", "maximum") { maxValue ->
        listOf<Message>(ModelActionMessage(ConstrainedFloat64Actions.changeMaxValue(constrainedFloat64, maxValue)))
    }
)


private fun viewMinMaxValueFields(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedInteger32: ConstrainedInteger32
) = viewInputIntegerRange(
    builder,
    "value-limits",
    "Value Limits (Minimum, Maximum):",
    IntegerInputConfig(false, constrainedInteger32.minValue, "min-value", "minimum") { minValue ->
        listOf<Message>(ModelActionMessage(ConstrainedInteger32Actions.changeMinValue(constrainedInteger32, minValue)))
    },
    IntegerInputConfig(false, constrainedInteger32.maxValue, "max-value", "maximum") { maxValue ->
        listOf<Message>(ModelActionMessage(ConstrainedInteger32Actions.changeMaxValue(constrainedInteger32, maxValue)))
    }
)


private fun viewMultiEdgednessField(
    builder: KatyDomFlowContentBuilder<Message>,
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
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeMultiEdgedness(edgeType, multiEdgedness)))
}


private fun viewNameField(
    builder: KatyDomFlowContentBuilder<Message>,
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
    listOf<Message>(ModelActionMessage(NamedElementActions.rename(namedElement, newName)))
}


private fun viewOptionalityField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeAttributeType: EdgeAttributeType
) = viewRadioGroup(
    builder,
    "optionality",
    "Required?",
    edgeAttributeType.optionality,
    EAttributeOptionality::valueOf,
    listOf(
        RadioConfig(false, EAttributeOptionality.REQUIRED, "Required"),
        RadioConfig(false, EAttributeOptionality.OPTIONAL, "Optional")
    )
) { optionality ->
    listOf<Message>(ModelActionMessage(EdgeAttributeTypeActions.changeOptionality(edgeAttributeType, optionality)))
}


private fun viewOptionalityField(
    builder: KatyDomFlowContentBuilder<Message>,
    vertexAttributeType: VertexAttributeType
) = viewRadioGroup(
    builder,
    "optionality",
    "Required?",
    vertexAttributeType.optionality,
    EAttributeOptionality::valueOf,
    listOf(
        RadioConfig(false, EAttributeOptionality.REQUIRED, "Required"),
        RadioConfig(false, EAttributeOptionality.OPTIONAL, "Optional")
    )
) { optionality ->
    listOf<Message>(ModelActionMessage(VertexAttributeTypeActions.changeOptionality(vertexAttributeType, optionality)))
}


private fun viewPatternField(
    builder: KatyDomFlowContentBuilder<Message>,
    constrainedString: ConstrainedString
) = viewInputTextField(
    builder,
    "pattern",
    constrainedString.id.toString(),
    "Pattern (Regular Expression):",
    constrainedString.regexPattern?.toString() ?: "",
    "regular expression",
    false
) { newPattern ->
    listOf<Message>(ModelActionMessage(ConstrainedStringActions.changeRegexPattern(constrainedString, newPattern)))
}


private fun viewRoleNameFields(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextGroup(
    builder,
    "role-names",
    "Role Names (Head, Tail):",
    listOf(
        TextInputConfig(isRoot, edgeType.headRoleName, "head-role-name", "head") { headRoleName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeHeadRoleName(edgeType, headRoleName)))
        },
        TextInputConfig(isRoot, edgeType.tailRoleName, "tail-role-name", "tail") { tailRoleName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeTailRoleName(edgeType, tailRoleName)))
        }
    )
)


private fun viewSelfLoopingField(
    builder: KatyDomFlowContentBuilder<Message>,
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
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeSelfLooping(edgeType, selfLooping)))
}


private fun viewSuperTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextFieldWithDataList(
    builder,
    "super-type",
    edgeType.id.toString(),
    "Super Type:",
    edgeType.superTypes.elementAtOrNull(0)?.path ?: "",
    "super type",
    isRoot,
    edgeType.findPotentialSuperTypes().map { et -> DataListOptionConfig(et.id.toString(), et.path) }
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeSuperType(edgeType, newDefaultValue)))
}


private fun viewSuperTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    edgeType: UndirectedEdgeType,
    isRoot: Boolean
) = viewInputTextFieldWithDataList(
    builder,
    "super-type",
    edgeType.id.toString(),
    "Super Type:",
    edgeType.superTypes.elementAtOrNull(0)?.path ?: "",
    "super type",
    isRoot,
    edgeType.findPotentialSuperTypes().map { et -> DataListOptionConfig(et.id.toString(), et.path) }
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(UndirectedEdgeTypeActions.changeSuperType(edgeType, newDefaultValue)))
}


private fun viewSuperTypeField(
    builder: KatyDomFlowContentBuilder<Message>,
    vertexType: VertexType,
    isRoot: Boolean
) = viewInputTextFieldWithDataList(
    builder,
    "super-type",
    vertexType.id.toString(),
    "Super Type:",
    vertexType.superTypes.elementAtOrNull(0)?.path ?: "",
    "super type",
    isRoot,
    vertexType.findPotentialSuperTypes().map { vt -> DataListOptionConfig(vt.id.toString(), vt.path) }
) { newDefaultValue ->
    listOf<Message>(ModelActionMessage(VertexTypeActions.changeSuperType(vertexType, newDefaultValue)))
}


