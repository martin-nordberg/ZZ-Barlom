//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.viewcomponents

import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.barlom.domain.metamodel.api.types.EBoolean

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a set of two radio buttons for an optional Boolean value.
 * Typically they are "Yes" and "No".
 * @param builder the virtual DOM builder creating the new radio group.
 * @param name the name of the field for the radio button group.
 * @param legend the label for the group as a whole.
 * @param currentValue the value of the variable that will set which radio button is checked.
 * @param trueLabel the label to use for the yes/true radio button.
 * @param falseLabel the label to use for the no/false radio button.
 * @param disabled if true the buttons are all disabled (grayed out)
 * @param changeValue a handler to call whenever a "change" event occurs for the radio button group.
 */
fun <Msg> viewBooleanRadioGroup(
    builder: KatydidFlowContentBuilder<Msg>,
    name: String,
    legend: String,
    currentValue: Boolean,
    trueLabel: String = "Yes",
    falseLabel: String = "No",
    disabled: Boolean = false,
    changeValue: (Boolean) -> Iterable<Msg>
) = viewRadioGroup(
    builder,
    name,
    legend,
    EBoolean.fromBoolean(currentValue),
    EBoolean::valueOf,
    listOf(
        RadioConfig(EBoolean.TRUE, trueLabel, disabled),
        RadioConfig(EBoolean.FALSE, falseLabel, disabled)
    )
) { newDefaultValue ->
    changeValue(newDefaultValue.toBoolean())
}

//---------------------------------------------------------------------------------------------------------------------

