//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.application.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.eventhandling.onblur

//---------------------------------------------------------------------------------------------------------------------

/**
 * Configuration for half of a floating point input range, either the minimum field or the maximum field.
 */
data class DoubleInputConfig<out Msg>(

    /** The value to set in the field. */
    val value: Double?,

    /** A unique ID that contributes to the virtual DOM key of the field (not an HTML id attribute). */
    val id: String,

    /** Placeholder text to use when the field is empty. */
    val placeholder: String? = null,

    /** Whether the field is disabled (grayed out). */
    val disabled: Boolean = false,

    /** A callback triggered when a "blur" event occurs for the field. */
    val changeValue: (Double?) -> Iterable<Msg>

)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for an adjacent pair of text input containing floating point (Double) values.
 * @param builder the virtual DOM builder creating the new field.
 * @param name the name of the input field.
 * @param legend the user-visible label for the two fields.
 * @param minNumberInput the configuration for the first field (the minimum of the range).
 * @param maxNumberInput the configuration for the second field (the maximum of the range).
 */
fun <Msg> viewInputDoubleRange(
    builder: KatyDomFlowContentBuilder<Msg>,
    name: String,
    legend: String,
    minNumberInput: DoubleInputConfig<Msg>,
    maxNumberInput: DoubleInputConfig<Msg>
) = katyDomComponent(builder) {

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
                    min = 0.0,
                    max = maxNumberInput.value,
                    placeholder = minNumberInput.placeholder,
                    value = minNumberInput.value
                ) {

                    onblur { event ->

                        val newValue: Double? = (event.target.asDynamic().value as String).toDoubleOrNull()

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
                    min = minNumberInput.value ?: 0.0,
                    placeholder = maxNumberInput.placeholder,
                    value = maxNumberInput.value
                ) {

                    onblur { event ->

                        val newValue: Double? = (event.target.asDynamic().value as String).toDoubleOrNull()

                        maxNumberInput.changeValue(newValue)

                    }

                }

            }

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

