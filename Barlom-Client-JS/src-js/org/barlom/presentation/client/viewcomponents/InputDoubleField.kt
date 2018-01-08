//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the view for a single number text input.
 */
fun viewInputNumberField(
    builder: KatyDomFlowContentBuilder,
    name: String,
    id: String,
    label: String,
    currentValue: Double?,
    placeholder: String,
    disabled: Boolean,
    changeValue: (Double?) -> Unit
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

                if (newValue != currentValue) {
                    changeValue(newValue)
                }

            }

        }

    }

}

