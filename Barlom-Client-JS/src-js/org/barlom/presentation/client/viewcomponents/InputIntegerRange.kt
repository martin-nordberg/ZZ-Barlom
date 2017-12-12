//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

data class IntegerInputConfig(
    val disabled: Boolean,
    val value: Int?,
    val id: String,
    val placeholder: String,
    val changeValue: (Int) -> Unit
)

/**
 * Builds the view for a grouped block of text inputs.
 */
fun viewInputIntegerRange(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    minNumberInput: IntegerInputConfig,
    maxNumberInput: IntegerInputConfig
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

                        val newValue: Int = (event.target.asDynamic().value as String).toInt()

                        if (newValue != minNumberInput.value) {
                            minNumberInput.changeValue(newValue)
                        }

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

                        val newValue: Int = (event.target.asDynamic().value as String).toInt()

                        if (newValue != maxNumberInput.value) {
                            maxNumberInput.changeValue(newValue)
                        }

                    }

                }

            }

        }

    }

}

