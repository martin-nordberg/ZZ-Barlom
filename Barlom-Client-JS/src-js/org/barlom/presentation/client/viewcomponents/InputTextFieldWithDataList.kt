//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder


data class DataListOptionConfig(
    val key: String,
    val value: String
)

/**
 * Builds the view for a single string text input. Adds a list of potential data items to choose from.
 */
fun viewInputTextFieldWithDataList(
    builder: KatyDomFlowContentBuilder,
    name: String,
    id: String,
    label: String,
    currentValue: String,
    placeholder: String,
    disabled: Boolean,
    options: List<DataListOptionConfig>,
    changeValue: (String) -> Unit
) = katyDomComponent(builder) {

    label("#$name-field.c-label.o-form-element") {

        val datalistId = "$name-$id-data-list"

        text(label)

        inputText(
            ".c-field.c-field--label",
            key = "$name-$id-input-field",
            disabled = disabled,
            list = datalistId,
            placeholder = placeholder,
            style = "width:50em",
            value = currentValue
        ) {

            onblur { event ->

                val newValue: String = event.target.asDynamic().value as String

                if (newValue != currentValue) {
                    changeValue(newValue)
                }

            }

        }

        datalist(
            "#$datalistId"
        ) {

            for ( optionConfig in options ) {
                option( "#${optionConfig.key}" ) { text( optionConfig.value ) }
            }

        }

    }

}

