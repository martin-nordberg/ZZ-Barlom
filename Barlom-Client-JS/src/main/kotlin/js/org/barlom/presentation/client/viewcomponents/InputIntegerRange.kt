//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import kotlin.math.roundToInt

data class IntegerInputConfig<Message>(
    val disabled: Boolean,
    val value: Int?,
    val id: String,
    val placeholder: String,
    val changeValue: (Int?) -> Iterable<Message>
)

/**
 * Builds the view for a grouped block of integer text inputs.
 */
fun <Message> viewInputIntegerRange(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    legend: String,
    minNumberInput: IntegerInputConfig<Message>,
    maxNumberInput: IntegerInputConfig<Message>
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
                    min = 0,
                    max = maxNumberInput.value,
                    placeholder = minNumberInput.placeholder,
                    value = minNumberInput.value
                ) {

                    onblur { event ->

                        val newValue: Int? = (event.target.asDynamic().value as String).toDoubleOrNull()?.roundToInt()
                        event.target.asDynamic().value = newValue

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

                        val newValue: Int? = (event.target.asDynamic().value as String).toDoubleOrNull()?.roundToInt()
                        event.target.asDynamic().value = newValue

                        maxNumberInput.changeValue(newValue)

                    }

                }

            }

        }

    }

}

