//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder

data class TextInputConfig<Message>(
    val disabled: Boolean,
    val value: String?,
    val id: String,
    val placeholder: String,
    val changeValue: (String) -> Iterable<Message>
)

/**
 * Builds the view for a grouped block of text inputs.
 */
fun <Message> viewInputTextGroup(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    legend: String,
    textInputs: List<TextInputConfig<Message>>
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

                            textInput.changeValue(newValue)

                        }

                    }

                }

            }

        }

    }

}

