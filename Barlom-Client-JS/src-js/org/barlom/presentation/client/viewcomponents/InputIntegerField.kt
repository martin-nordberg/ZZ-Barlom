//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import kotlin.math.roundToInt

/**
 * Builds the view for a single integer text input.
 */
fun <Message> viewInputIntegerField(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    id: String,
    label: String,
    currentValue: Int?,
    placeholder: String,
    disabled: Boolean,
    changeValue: (Int?) -> Iterable<Message>
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

                val newValue: Int? = (event.target.asDynamic().value as String).toDoubleOrNull()?.roundToInt()
                event.target.asDynamic().value = newValue

                changeValue(newValue)

            }

        }

    }

}

