//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.barlom.presentation.client.viewcomponents

import o.katydid.events.eventhandling.onblur
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import kotlin.math.roundToInt

//---------------------------------------------------------------------------------------------------------------------

/**
 * Configuration for half of an integer input range, either the minimum field or the maximum field.
 */
data class IntegerInputConfig<out Msg>(

    /** The value to set in the field. */
    val value: Int?,

    /** A unique ID that contributes to the virtual DOM key of the field (not an HTML id attribute). */
    val id: String,

    /** Placeholder text to use when the field is empty. */
    val placeholder: String? = null,

    /** Whether the field is disabled (grayed out). */
    val disabled: Boolean = false,

    /** A callback triggered when a "blur" event occurs for the field. */
    val changeValue: (Int?) -> Iterable<Msg>

)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a grouped block of integer text inputs.
 */
fun <Msg> viewInputIntegerRange(
    builder: KatydidFlowContentBuilder<Msg>,
    name: String,
    legend: String,
    minNumberInput: IntegerInputConfig<Msg>,
    maxNumberInput: IntegerInputConfig<Msg>
) = katydidComponent(builder) {

    fieldset("#$name-field.o-fieldset") {

        legend {
            text(legend)
        }

        div(
            "#$name-block.c-input-group",
            style = "width:20em;"
        ) {

            div(
                "#${minNumberInput.id}-field.o-field"
            ) {

                inputNumber(
                    ".c-field",
                    key = name + "-" + minNumberInput.id,
                    disabled = minNumberInput.disabled,
                    min = 0,
                    max = maxNumberInput.value,
                    placeholder = minNumberInput.placeholder,
                    value = minNumberInput.value
                ) {

                    onblur { event ->

                        val newValue: Int? = event.getTargetAttribute<String>("value").toDoubleOrNull()?.roundToInt()
                        event.setTargetAttribute("value", newValue)

                        minNumberInput.changeValue(newValue)

                    }

                }

            }

            div(
                "#${maxNumberInput.id}-field.o-field"
            ) {

                inputNumber(
                    ".c-field",
                    key = name + "-" + maxNumberInput.id,
                    disabled = maxNumberInput.disabled,
                    min = minNumberInput.value ?: 0,
                    placeholder = maxNumberInput.placeholder,
                    value = maxNumberInput.value
                ) {

                    onblur { event ->

                        val newValue: Int? = event.getTargetAttribute<String>("value").toDoubleOrNull()?.roundToInt()
                        event.setTargetAttribute("value", newValue)

                        maxNumberInput.changeValue(newValue)

                    }

                }

            }

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

