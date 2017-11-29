//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

data class TextInputConfig(
    val disabled: Boolean,
    val value: String?,
    val id: String,
    val placeholder: String,
    val changeValue: (String) -> Unit
)

/**
 * Builds the view for a grouped block of text inputs.
 */
fun viewInputTextGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    textInputs: List<TextInputConfig>
) = katyDomComponent(builder) {

    fieldset("#$name-field.o-fieldset") {

        legend {
            text(legend)
        }

        div(
            "#$name-block.c-input-group",
            style = "width:50em;"
        ) {

            for (textInput in textInputs) {

                div(
                    "#${textInput.id}-field.o-field"
                ) {

                    inputText(
                        ".c-field",
                        key = name + "-" + textInput.id,
                        disabled = textInput.disabled,
                        placeholder = textInput.placeholder,
                        value = textInput.value
                    ) {

                        onblur { event ->

                            val newValue: String = event.target.asDynamic().value as String

                            if (newValue != textInput.value) {
                                textInput.changeValue(newValue)
                            }

                        }

                    }

                }

            }

        }

    }

}

