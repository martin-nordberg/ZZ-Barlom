//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.rightpanels.forms

import js.org.barlom.presentation.client.messages.Message
import js.org.barlom.presentation.client.messages.ModelActionMessage
import js.org.barlom.presentation.client.viewcomponents.*
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.org.barlom.domain.metamodel.api.actions.*
import o.org.barlom.domain.metamodel.api.types.*
import o.org.barlom.domain.metamodel.api.vertices.*


fun viewPropertiesForm(
    builder: KatydidFlowContentBuilder<Message>,
    focusedElement: AbstractNamedElement
) = katydidComponent(builder) {

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
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "abstractness",
    "Abstract?",
    edgeType.abstractness,
    EAbstractness::valueOf,
    listOf(
        RadioConfig(EAbstractness.ABSTRACT, "Abstract", isRoot),
        RadioConfig(EAbstractness.CONCRETE, "Concrete", isRoot)
    )
) { abstractness ->
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeAbstractness(edgeType, abstractness)))
}


private fun viewAbstractnessField(
    builder: KatydidFlowContentBuilder<Message>,
    vertexType: VertexType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "abstractness",
    "Abstract?",
    vertexType.abstractness,
    EAbstractness::valueOf,
    listOf(
        RadioConfig(EAbstractness.ABSTRACT, "Abstract", isRoot),
        RadioConfig(EAbstractness.CONCRETE, "Concrete", isRoot)
    )
) { abstractness ->
    listOf<Message>(ModelActionMessage(VertexTypeActions.changeAbstractness(vertexType, abstractness)))
}


private fun viewConnectedHeadVertexTypeField(
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "cyclicity",
    "Cycles?",
    edgeType.cyclicity,
    ECyclicity::valueOf,
    listOf(
        RadioConfig(ECyclicity.POTENTIALLY_CYCLIC, "Allowed", isRoot),
        RadioConfig(ECyclicity.ACYCLIC, "Not Allowed", isRoot),
        RadioConfig(ECyclicity.UNCONSTRAINED, "Unconstrained", isRoot || edgeType.abstractness.isConcrete())
    )
) { cyclicity ->
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeCyclicity(edgeType, cyclicity)))
}


private fun viewDataTypeField(
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextGroup(
    builder,
    "directed-names",
    "Directed Names (Forward, Reverse):",
    listOf(
        TextInputConfig(edgeType.forwardName, "forward-name", "forward", isRoot) { forwardName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeForwardName(edgeType, forwardName)))
        },
        TextInputConfig(edgeType.reverseName, "reverse-name", "reverse", isRoot) { reverseName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeReverseName(edgeType, reverseName)))
        }
    )
)


private fun viewLabelDefaultingField(
    builder: KatydidFlowContentBuilder<Message>,
    vertexAttributeType: VertexAttributeType
) = viewRadioGroup(
    builder,
    "labelDefaulting",
    "Default Label for Vertex?",
    vertexAttributeType.labelDefaulting,
    ELabelDefaulting::valueOf,
    listOf(
        RadioConfig(ELabelDefaulting.DEFAULT_LABEL, "Yes"),
        RadioConfig(ELabelDefaulting.NOT_DEFAULT_LABEL, "No")
    )
) { labelDefaulting ->
    listOf<Message>(
        ModelActionMessage(VertexAttributeTypeActions.changeLabelDefaulting(vertexAttributeType, labelDefaulting)))
}


private fun viewMinMaxDegreeFields(
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: UndirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "degree",
    "Degree (Minimum, Maximum):",
    IntegerInputConfig(edgeType.minDegree, "min-degree", "minimum", isRoot) { minDegree ->
        listOf<Message>(ModelActionMessage(UndirectedEdgeTypeActions.changeMinDegree(edgeType, minDegree)))
    },
    IntegerInputConfig(edgeType.maxDegree, "max-degree", "maximum", isRoot) { maxDegree ->
        listOf<Message>(ModelActionMessage(UndirectedEdgeTypeActions.changeMaxDegree(edgeType, maxDegree)))
    }
)


private fun viewMinMaxHeadInDegreeFields(
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "head-in-degree",
    "Head In-Degree (Minimum, Maximum):",
    IntegerInputConfig(edgeType.minHeadInDegree, "min-head-in-degree", "minimum", isRoot) { minHeadInDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMinHeadInDegree(edgeType, minHeadInDegree)))
    },
    IntegerInputConfig(edgeType.maxHeadInDegree, "max-head-in-degree", "maximum", isRoot) { maxHeadInDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMaxHeadInDegree(edgeType, maxHeadInDegree)))
    }
)


private fun viewMinMaxLengthFields(
    builder: KatydidFlowContentBuilder<Message>,
    constrainedString: ConstrainedString
) = viewInputIntegerRange(
    builder,
    "length",
    "Length (Minimum, Maximum):",
    IntegerInputConfig(constrainedString.minLength, "min-length", "minimum") { minLength ->
        listOf<Message>(ModelActionMessage(ConstrainedStringActions.changeMinLength(constrainedString, minLength)))
    },
    IntegerInputConfig(constrainedString.maxLength, "max-length", "maximum") { maxLength ->
        listOf<Message>(ModelActionMessage(ConstrainedStringActions.changeMaxLength(constrainedString, maxLength)))
    }
)


private fun viewMinMaxTailOutDegreeFields(
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputIntegerRange(
    builder,
    "tail-out-degree",
    "Tail Out-Degree (Minimum, Maximum):",
    IntegerInputConfig(edgeType.minTailOutDegree, "min-tail-out-degree", "minimum", isRoot) { minTailOutDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMinTailOutDegree(edgeType, minTailOutDegree)))
    },
    IntegerInputConfig(edgeType.maxTailOutDegree, "max-tail-out-degree", "maximum", isRoot) { maxTailOutDegree ->
        listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeMaxTailOutDegree(edgeType, maxTailOutDegree)))
    }
)


private fun viewMinMaxValueFields(
    builder: KatydidFlowContentBuilder<Message>,
    constrainedFloat64: ConstrainedFloat64
) = viewInputDoubleRange(
    builder,
    "value-limits",
    "Value Limits (Minimum, Maximum):",
    DoubleInputConfig(constrainedFloat64.minValue, "min-value", "minimum") { minValue ->
        listOf<Message>(ModelActionMessage(ConstrainedFloat64Actions.changeMinValue(constrainedFloat64, minValue)))
    },
    DoubleInputConfig(constrainedFloat64.maxValue, "max-value", "maximum") { maxValue ->
        listOf<Message>(ModelActionMessage(ConstrainedFloat64Actions.changeMaxValue(constrainedFloat64, maxValue)))
    }
)


private fun viewMinMaxValueFields(
    builder: KatydidFlowContentBuilder<Message>,
    constrainedInteger32: ConstrainedInteger32
) = viewInputIntegerRange(
    builder,
    "value-limits",
    "Value Limits (Minimum, Maximum):",
    IntegerInputConfig(constrainedInteger32.minValue, "min-value", "minimum") { minValue ->
        listOf<Message>(ModelActionMessage(ConstrainedInteger32Actions.changeMinValue(constrainedInteger32, minValue)))
    },
    IntegerInputConfig(constrainedInteger32.maxValue, "max-value", "maximum") { maxValue ->
        listOf<Message>(ModelActionMessage(ConstrainedInteger32Actions.changeMaxValue(constrainedInteger32, maxValue)))
    }
)


private fun viewMultiEdgednessField(
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "multiedgedness",
    "Multi-Edges?",
    edgeType.multiEdgedness,
    EMultiEdgedness::valueOf,
    listOf(
        RadioConfig(EMultiEdgedness.MULTI_EDGES_ALLOWED, "Allowed", isRoot),
        RadioConfig(EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED, "Not Allowed", isRoot),
        RadioConfig(EMultiEdgedness.UNCONSTRAINED, "Unconstrained", isRoot || edgeType.abstractness.isConcrete())
    )
) { multiEdgedness ->
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeMultiEdgedness(edgeType, multiEdgedness)))
}


private fun viewNameField(
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
    edgeAttributeType: EdgeAttributeType
) = viewRadioGroup(
    builder,
    "optionality",
    "Required?",
    edgeAttributeType.optionality,
    EAttributeOptionality::valueOf,
    listOf(
        RadioConfig(EAttributeOptionality.REQUIRED, "Required"),
        RadioConfig(EAttributeOptionality.OPTIONAL, "Optional")
    )
) { optionality ->
    listOf<Message>(ModelActionMessage(EdgeAttributeTypeActions.changeOptionality(edgeAttributeType, optionality)))
}


private fun viewOptionalityField(
    builder: KatydidFlowContentBuilder<Message>,
    vertexAttributeType: VertexAttributeType
) = viewRadioGroup(
    builder,
    "optionality",
    "Required?",
    vertexAttributeType.optionality,
    EAttributeOptionality::valueOf,
    listOf(
        RadioConfig(EAttributeOptionality.REQUIRED, "Required"),
        RadioConfig(EAttributeOptionality.OPTIONAL, "Optional")
    )
) { optionality ->
    listOf<Message>(ModelActionMessage(VertexAttributeTypeActions.changeOptionality(vertexAttributeType, optionality)))
}


private fun viewPatternField(
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: DirectedEdgeType,
    isRoot: Boolean
) = viewInputTextGroup(
    builder,
    "role-names",
    "Role Names (Head, Tail):",
    listOf(
        TextInputConfig(edgeType.headRoleName, "head-role-name", "head", isRoot) { headRoleName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeHeadRoleName(edgeType, headRoleName)))
        },
        TextInputConfig(edgeType.tailRoleName, "tail-role-name", "tail", isRoot) { tailRoleName ->
            listOf<Message>(ModelActionMessage(DirectedEdgeTypeActions.changeTailRoleName(edgeType, tailRoleName)))
        }
    )
)


private fun viewSelfLoopingField(
    builder: KatydidFlowContentBuilder<Message>,
    edgeType: AbstractEdgeType,
    isRoot: Boolean
) = viewRadioGroup(
    builder,
    "selflooping",
    "Self Looping?",
    edgeType.selfLooping,
    ESelfLooping::valueOf,
    listOf(
        RadioConfig(ESelfLooping.SELF_LOOPS_ALLOWED, "Allowed", isRoot),
        RadioConfig(ESelfLooping.SELF_LOOPS_NOT_ALLOWED, "Not Allowed", isRoot),
        RadioConfig(ESelfLooping.UNCONSTRAINED, "Unconstrained", isRoot || edgeType.abstractness.isConcrete())
    )
) { selfLooping ->
    listOf<Message>(ModelActionMessage(AbstractEdgeTypeActions.changeSelfLooping(edgeType, selfLooping)))
}


private fun viewSuperTypeField(
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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
    builder: KatydidFlowContentBuilder<Message>,
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


