//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.katydid.events.eventhandling.onblur
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder


//---------------------------------------------------------------------------------------------------------------------

/** Configuration for one in a group of text inputs. */
data class TextInputConfig<out Msg>(

    /** The value for the input field. */
    val value: String?,

    /** A unique ID that contributes to the virtual DOM key of the field (not its HTML id attribute). */
    val id: String,

    /** Placeholder text to appear in the field when it is blank. */
    val placeholder: String? = null,

    /** Whether the field is disabled (grayed out). */
    val disabled: Boolean = false,

    /** Callback triggered by a "change" event for the field. */
    val changeValue: (String) -> Iterable<Msg>

)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a grouped block of text inputs.
 * @param builder the virtual DOM builder creating the new fields.
 * @param name the name of the input field group.
 * @param legend the user-visible label for the field group.
 * @param textInputs configuration of the individual fields.
 */
fun <Msg> viewInputTextGroup(
    builder: KatydidFlowContentBuilder<Msg>,
    name: String,
    legend: String,
    textInputs: List<TextInputConfig<Msg>>
) = katydidComponent(builder) {

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

//---------------------------------------------------------------------------------------------------------------------

