//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.viewcomponents

import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.barlom.domain.metamodel.api.types.EBooleanOrNull

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a set of three radio buttons for an optional Boolean value.
 * Typically they are "Yes", "No", and "N/A".
 * @param builder the virtual DOM builder creating the new radio group.
 * @param name the name of the field for the radio button group.
 * @param legend the label for the group as a whole.
 * @param currentValue the value of the variable that will set which radio button is checked.
 * @param trueLabel the label to use for the yes/true radio button.
 * @param falseLabel the label to use for the no/false radio button.
 * @param nullLabel the label to use for the null radio button.
 * @param disabled if true the buttons are all disabled (grayed out)
 * @param changeValue a handler to call whenever a "change" event occurs for the radio button group.
 */
fun <Msg> viewBooleanOrNullRadioGroup(
    builder: KatydidFlowContentBuilder<Msg>,
    name: String,
    legend: String,
    currentValue: Boolean?,
    trueLabel: String = "Yes",
    falseLabel: String = "No",
    nullLabel: String = "N/A",
    disabled: Boolean = false,
    changeValue: (Boolean?) -> Iterable<Msg>
) = viewRadioGroup(
    builder,
    name,
    legend,
    EBooleanOrNull.fromBoolean(currentValue),
    EBooleanOrNull::valueOf,
    listOf(
        RadioConfig(EBooleanOrNull.TRUE, trueLabel, disabled),
        RadioConfig(EBooleanOrNull.FALSE, falseLabel, disabled),
        RadioConfig(EBooleanOrNull.NULL, nullLabel, disabled)
    )
) { newDefaultValue ->
    changeValue(newDefaultValue.toBoolean())
}

//---------------------------------------------------------------------------------------------------------------------

