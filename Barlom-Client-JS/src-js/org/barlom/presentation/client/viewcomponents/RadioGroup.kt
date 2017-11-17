//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.w3c.dom.events.Event

data class RadioConfig<T>(val disabled: Boolean, val value: T, val label: String)

fun <T> viewRadioGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    currentValue: T,
    radios: List<RadioConfig<T>>,
    handleChange: (Event) -> Unit
) = katyDomComponent(builder) {

    fieldset("#" + name + "-fieldset.o-fieldset.c-list.c-list--inline.c-list--unstyled") {

        legend("#" + name + "-legend.o-fieldset__legend") {
            text(legend)
        }

        for (radio in radios) {

            val key = radio.value.toString().toLowerCase()
            val value = radio.value.toString()

            label("#" + key + "-label.c-field.c-field--choice.c-list__item") {

                inputRadioButton(
                    key = key + "-" + name,
                    checked = radio.value == currentValue,
                    disabled = radio.disabled,
                    name = name,
                    value = value
                ) {
                    onchange(handleChange)
                }

                text(radio.label)

            }

        }

    }

}

