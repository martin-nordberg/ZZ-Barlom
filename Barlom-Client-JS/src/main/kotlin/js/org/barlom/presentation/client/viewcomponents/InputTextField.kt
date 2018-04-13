//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the view for a single string text input.
 */
fun <Message> viewInputTextField(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    id: String,
    label: String,
    currentValue: String,
    placeholder: String,
    disabled: Boolean,
    changeValue: (String) -> Iterable<Message>
) = katyDomComponent(builder) {

    label("#$name-field.c-label.o-form-element") {

        text(label)

        inputText(
            ".c-field.c-field--label",
            key = "$name-$id-input-field",
            disabled = disabled,
            placeholder = placeholder,
            style = "width:50em",
            value = currentValue
        ) {

            onblur { event ->

                val newValue: String = event.target.asDynamic().value as String

                changeValue(newValue)

            }

        }

    }

}

