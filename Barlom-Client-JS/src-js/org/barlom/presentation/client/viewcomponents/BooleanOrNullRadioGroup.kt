//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.barlom.domain.metamodel.api.types.EBooleanOrNull
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the view for a set of radio buttons for an optional Boolean value.
 */
fun viewBooleanOrNullRadioGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    currentValue: Boolean?,
    trueLabel: String = "Yes",
    falseLabel: String = "No",
    nullLabel: String = "N/A",
    changeValue: (Boolean?) -> Unit
) = viewRadioGroup(
    builder,
    name,
    legend,
    EBooleanOrNull.fromBoolean(currentValue),
    EBooleanOrNull::valueOf,
    listOf(
        RadioConfig(false, EBooleanOrNull.TRUE, trueLabel),
        RadioConfig(false, EBooleanOrNull.FALSE, falseLabel),
        RadioConfig(false, EBooleanOrNull.NULL, nullLabel)
    )
) { newDefaultValue ->
    changeValue(newDefaultValue.toBoolean())
}


