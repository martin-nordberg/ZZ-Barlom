//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.viewcomponents

import org.barlom.domain.metamodel.api.actions.ConstrainedBooleanActions
import org.barlom.domain.metamodel.api.types.EBoolean
import org.barlom.domain.metamodel.api.types.EBooleanOrNull
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

data class RadioConfig<out T>(
    val disabled: Boolean,
    val value: T,
    val label: String
)

/**
 * Builds the view for a set of radio buttons for an optional Boolean value.
 */
fun viewBooleanRadioGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    currentValue: Boolean,
    trueLabel: String = "Yes",
    falseLabel: String = "No",
    changeValue: (Boolean) -> Unit
) = viewRadioGroup(
    builder,
    name,
    legend,
    EBoolean.fromBoolean(currentValue),
    EBoolean::valueOf,
    listOf(
        RadioConfig(false, EBoolean.TRUE, trueLabel),
        RadioConfig(false, EBoolean.FALSE, falseLabel)
    )
) { newDefaultValue -> changeValue(newDefaultValue.toBoolean())
}


/**
 * Builds the view for a set of radio buttons for an optional Boolean value.
 */
fun viewBooleanOrNullRadioGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    currentValue: Boolean?,
    trueLabel: String = "Yes",
    falseLabel: String = "No",
    nullLabel: String = "N/A",
    changeValue: (Boolean?) -> Unit
) = viewRadioGroup(
    builder,
    name,
    legend,
    EBooleanOrNull.fromBoolean(currentValue),
    EBooleanOrNull::valueOf,
    listOf(
        RadioConfig(false, EBooleanOrNull.TRUE, trueLabel),
        RadioConfig(false, EBooleanOrNull.FALSE, falseLabel),
        RadioConfig(false, EBooleanOrNull.NULL, nullLabel)
    )
) { newDefaultValue -> changeValue(newDefaultValue.toBoolean())
}


/**
 * Builds the view for a generic set of radio buttons.
 */
fun <T> viewRadioGroup(
    builder: KatyDomFlowContentBuilder,
    name: String,
    legend: String,
    currentValue: T,
    toT: (String) -> T,
    radios: List<RadioConfig<T>>,
    changeValue: (T) -> Unit
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

                        if (newValue != currentValue) {
                            changeValue(newValue)
                        }

                    }

                }

                text(radio.label)

            }

        }

    }

}

