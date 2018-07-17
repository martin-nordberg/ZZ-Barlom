//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.eventhandling.onblur

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a text input containing a floating point (Double) value.
 * @param builder the virtual DOM builder creating the new field.
 * @param name the name of the input field.
 * @param id a unique ID that becomes part of the key for the input field (but not an HTML id attribute).
 * @param label the user-visible label for the field.
 * @param currentValue the value of the variable that will set what is in the field.
 * @param placeholder text to show in the field when it is empty.
 * @param disabled if true the input field is disabled (grayed out)
 * @param changeValue a handler to call whenever a "blur" event occurs for the field.
 */
fun <Msg> viewInputDoubleField(
    builder: KatyDomFlowContentBuilder<Msg>,
    name: String,
    id: String,
    label: String,
    currentValue: Double?,
    placeholder: String? = null,
    disabled: Boolean = false,
    changeValue: (Double?) -> Iterable<Msg>
) = katyDomComponent(builder) {

    label("#$name-field.c-label.o-form-element") {

        text(label)

        inputNumber(
            ".c-field.c-field--label",
            key = "$name-$id-input-field",
            disabled = disabled,
            placeholder = placeholder,
            style = "width:50em",
            value = currentValue
        ) {

            onblur { event ->

                val newValue: Double? = (event.target.asDynamic().value as String).toDoubleOrNull()

                changeValue(newValue)

            }

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------
