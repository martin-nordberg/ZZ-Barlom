//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder

//---------------------------------------------------------------------------------------------------------------------

/** Configuration of one radio button in a group. */
data class RadioConfig<out Enum>(

    /** The value of the field (group) when the radio button is checked. */
    val value: Enum,

    /** The user-visible label for this radio button. */
    val label: String,

    /** Whether the radio button is disabled. */
    val disabled: Boolean = false

)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a generic set of radio buttons.
 * @param builder the virtual DOM builder creating the new radio group.
 * @param name the name of the field for the radio button group.
 * @param legend the label for the group as a whole.
 * @param currentValue the value of the variable that will set which radio button is checked.
 * @param toEnum a function that converts a string value to the corresponding Enum instance.
 * @param radios the configuration of each radio button in the group.
 * @param changeValue a handler to call whenever a "change" event occurs for the radio button group.
 */
fun <Enum, Msg> viewRadioGroup(
    builder: KatyDomFlowContentBuilder<Msg>,
    name: String,
    legend: String,
    currentValue: Enum,
    toEnum: (String) -> Enum,
    radios: List<RadioConfig<Enum>>,
    changeValue: (Enum) -> Iterable<Msg>
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

                        val newValue = toEnum(event.target.asDynamic().value as String)

                        changeValue(newValue)

                    }

                }

                text(radio.label)

            }

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

