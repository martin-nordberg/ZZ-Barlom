//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the view for a single double text input.
 */
fun <Message> viewInputDoubleField(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    id: String,
    label: String,
    currentValue: Double?,
    placeholder: String,
    disabled: Boolean,
    changeValue: (Double?) -> Iterable<Message>
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

