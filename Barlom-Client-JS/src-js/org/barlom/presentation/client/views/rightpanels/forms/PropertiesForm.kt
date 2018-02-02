//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.forms

import org.barlom.domain.metamodel.api.actions.*
import org.barlom.domain.metamodel.api.types.*
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.viewcomponents.*
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder


fun viewPropertiesForm(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractNamedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    val isRoot = focusedElement is Package && focusedElement.isRoot ||
        focusedElement is VertexType && focusedElement.isRoot ||
        focusedElement is AbstractEdgeType && focusedElement.isRoot

    form("#properties-form.form--properties", action = "javascript:void(0);") {

        when (focusedElement) {

            is ConstrainedBoolean   -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewDefaultValueField(this, revDispatchModel, focusedElement)
            }

        // TODO: ConstrainedDateTime

            is ConstrainedFloat64   -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxValueFields(this, revDispatchModel, focusedElement)
                viewDefaultValueField(this, revDispatchModel, focusedElement)
            }

            is ConstrainedInteger32 -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxValueFields(this, revDispatchModel, focusedElement)
                viewDefaultValueField(this, revDispatchModel, focusedElement)
            }

            is ConstrainedString    -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxLengthFields(this, revDispatchModel, focusedElement)
                viewDefaultValueField(this, revDispatchModel, focusedElement)
                viewPatternField(this, revDispatchModel, focusedElement)
            }

            is ConstrainedUuid      -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
            }

            is DirectedEdgeType     -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewSuperTypeField( this, revDispatchModel, focusedElement, isRoot)
                viewForwardReverseNameFields(this, revDispatchModel, focusedElement, isRoot)
                viewRoleNameFields(this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
                viewCyclicityField(this, revDispatchModel, focusedElement, isRoot)
                viewMultiEdgednessField(this, revDispatchModel, focusedElement, isRoot)
                viewSelfLoopingField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxHeadInDegreeFields(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxTailOutDegreeFields(this, revDispatchModel, focusedElement, isRoot)
            }

            is EdgeAttributeType    -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewDataTypeField(this, revDispatchModel, focusedElement)
                viewOptionalityField(this, revDispatchModel, focusedElement)
            }

            is Package              -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
            }

            is UndirectedEdgeType   -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewSuperTypeField( this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
                viewCyclicityField(this, revDispatchModel, focusedElement, isRoot)
                viewMultiEdgednessField(this, revDispatchModel, focusedElement, isRoot)
                viewSelfLoopingField(this, revDispatchModel, focusedElement, isRoot)
                viewMinMaxDegreeFields(this, revDispatchModel, focusedElement, isRoot)
            }

            is VertexAttributeType  -> {
                viewNameField(this, revDispatchModel, focusedElement, false)
                viewDescriptionField(this, revDispatchModel, focusedElement, false)
                viewDataTypeField(this, revDispatchModel, focusedElement)
                viewOptionalityField(this, revDispatchModel, focusedElement)
                viewLabelDefaultingField(this, revDispatchModel, focusedElement)
            }

            is VertexType           -> {
                viewNameField(this, revDispatchModel, focusedElement, isRoot)
                viewDescriptionField(this, revDispatchModel, focusedElement, isRoot)
                viewSuperTypeField( this, revDispatchModel, focusedElement, isRoot)
                viewAbstractnessField(this, revDispatchModel, focusedElement, isRoot)
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


private fun viewDataTypeField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    edgeAttributeType: EdgeAttributeType
) = viewInputTextFieldWithDataList(
    builder,
    "data-type",
    edgeAttributeType.id.toString(),
    "Data Type:",
    edgeAttributeType.dataTypes.elementAtOrNull(0)?.path ?: "",
    "data type",
    false,
    edgeAttributeType.findPotentialDataTypes().map{ dt -> DataListOptionConfig( dt.id.toString(), dt.path ) }
) { newDefaultValue ->
    revDispatchModel(EdgeAttributeTypeActions.changeDataType(edgeAttributeType, newDefaultValue))
}


private fun viewDataTypeField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    vertexAttributeType: VertexAttributeType
) = viewInputTextFieldWithDataList(
    builder,
    "data-type",
    vertexAttributeType.id.toString(),
    "Data Type:",
    vertexAttributeType.dataTypes.elementAtOrNull(0)?.path ?: "",
    "data type",
    false,
    vertexAttributeType.findPotentialDataTypes().map{ dt -> DataListOptionConfig( dt.id.toString(), dt.path ) }
) { newDefaultValue ->
    revDispatchModel(VertexAttributeTypeActions.changeDataType(vertexAttributeType, newDefaultValue))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(ConstrainedBooleanActions.changeDefaultValue(constrainedBoolean, newDefaultValue))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(ConstrainedFloat64Actions.changeDefaultValue(constrainedFloat64, newDefaultValue))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(ConstrainedInteger32Actions.changeDefaultValue(constrainedFloat64, newDefaultValue))
}


private fun viewDefaultValueField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(ConstrainedStringActions.changeDefaultValue(constrainedString, newDefaultValue))
}


private fun viewDescriptionField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(DocumentedElementActions.describe(element, newDescription))
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


private fun viewLabelDefaultingField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(VertexAttributeTypeActions.changeLabelDefaulting(vertexAttributeType, labelDefaulting))
}


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


private fun viewMinMaxLengthFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    constrainedString: ConstrainedString
) = viewInputIntegerRange(
    builder,
    "length",
    "Length (Minimum, Maximum):",
    IntegerInputConfig(false, constrainedString.minLength, "min-length", "minimum") { minLength ->
        revDispatchModel(ConstrainedStringActions.changeMinLength(constrainedString, minLength))
    },
    IntegerInputConfig(false, constrainedString.maxLength, "max-length", "maximum") { maxLength ->
        revDispatchModel(ConstrainedStringActions.changeMaxLength(constrainedString, maxLength))
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


private fun viewMinMaxValueFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    constrainedFloat64: ConstrainedFloat64
) = viewInputDoubleRange(
    builder,
    "value-limits",
    "Value Limits (Minimum, Maximum):",
    DoubleInputConfig(false, constrainedFloat64.minValue, "min-value", "minimum") { minValue ->
        revDispatchModel(ConstrainedFloat64Actions.changeMinValue(constrainedFloat64, minValue))
    },
    DoubleInputConfig(false, constrainedFloat64.maxValue, "max-value", "maximum") { maxValue ->
        revDispatchModel(ConstrainedFloat64Actions.changeMaxValue(constrainedFloat64, maxValue))
    }
)


private fun viewMinMaxValueFields(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    constrainedInteger32: ConstrainedInteger32
) = viewInputIntegerRange(
    builder,
    "value-limits",
    "Value Limits (Minimum, Maximum):",
    IntegerInputConfig(false, constrainedInteger32.minValue, "min-value", "minimum") { minValue ->
        revDispatchModel(ConstrainedInteger32Actions.changeMinValue(constrainedInteger32, minValue))
    },
    IntegerInputConfig(false, constrainedInteger32.maxValue, "max-value", "maximum") { maxValue ->
        revDispatchModel(ConstrainedInteger32Actions.changeMaxValue(constrainedInteger32, maxValue))
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


private fun viewOptionalityField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(EdgeAttributeTypeActions.changeOptionality(edgeAttributeType, optionality))
}


private fun viewOptionalityField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(VertexAttributeTypeActions.changeOptionality(vertexAttributeType, optionality))
}


private fun viewPatternField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    revDispatchModel(ConstrainedStringActions.changeRegexPattern(constrainedString, newPattern))
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


private fun viewSuperTypeField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    edgeType.findPotentialSuperTypes().map{ et -> DataListOptionConfig( et.id.toString(), et.path ) }
) { newDefaultValue ->
    revDispatchModel(DirectedEdgeTypeActions.changeSuperType(edgeType, newDefaultValue))
}


private fun viewSuperTypeField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    edgeType.findPotentialSuperTypes().map{ et -> DataListOptionConfig( et.id.toString(), et.path ) }
) { newDefaultValue ->
    revDispatchModel(UndirectedEdgeTypeActions.changeSuperType(edgeType, newDefaultValue))
}


private fun viewSuperTypeField(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
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
    vertexType.findPotentialSuperTypes().map{ vt -> DataListOptionConfig( vt.id.toString(), vt.path ) }
) { newDefaultValue ->
    revDispatchModel(VertexTypeActions.changeSuperType(vertexType, newDefaultValue))
}


