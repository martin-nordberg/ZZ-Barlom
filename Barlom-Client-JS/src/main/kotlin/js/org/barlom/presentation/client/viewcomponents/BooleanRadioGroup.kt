//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.barlom.domain.metamodel.api.types.EBoolean
import o.org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the view for a set of radio buttons for an optional Boolean value.
 */
fun <Message> viewBooleanRadioGroup(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    legend: String,
    currentValue: Boolean,
    trueLabel: String = "Yes",
    falseLabel: String = "No",
    changeValue: (Boolean) -> Iterable<Message>
) = viewRadioGroup(
    builder,
    name,
    legend,
    EBoolean.fromBoolean(currentValue),
    EBoolean::valueOf,
    listOf(
        RadioConfig(false, EBoolean.TRUE, trueLabel),
        RadioConfig(false, EBoolean.FALSE, falseLabel)
    )
) { newDefaultValue ->
    changeValue(newDefaultValue.toBoolean())
}


