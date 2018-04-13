//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder

data class RadioConfig<out T>(
    val disabled: Boolean,
    val value: T,
    val label: String
)

/**
 * Builds the view for a generic set of radio buttons.
 */
fun <Enum,Message> viewRadioGroup(
    builder: KatyDomFlowContentBuilder<Message>,
    name: String,
    legend: String,
    currentValue: Enum,
    toT: (String) -> Enum,
    radios: List<RadioConfig<Enum>>,
    changeValue: (Enum) -> Iterable<Message>
) = katyDomComponent(builder) {

    fieldset("#$name-fieldset.o-fieldset.c-list.c-list--inline.c-list--unstyled") {

        legend("#$name-legend.o-fieldset__legend") {
            text(legend)
        }

        for (radio in radios) {

            val value = radio.value.toString()
            val key = value.toLowerCase()

            label("#$key-label.c-field.c-field--choice.c-list__item") {

                inputRadioButton(
                    key = key + "-" + name,
                    checked = radio.value == currentValue,
                    disabled = radio.disabled,
                    name = name,
                    value = value
                ) {

                    onchange { event ->

                        val newValue = toT(event.target.asDynamic().value as String)

                        changeValue(newValue)

                    }

                }

                text(radio.label)

            }

        }

    }

}
