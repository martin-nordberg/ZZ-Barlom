//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.katydid.events.eventhandling.onblur
import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import kotlin.math.min

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a single string text input.
 */
fun <Msg> viewTextAreaField(
    builder: KatydidFlowContentBuilder<Msg>,
    name: String,
    id: String,
    label: String,
    currentValue: String,
    placeholder: String,
    disabled: Boolean,
    changeValue: (String) -> Iterable<Msg>
) = katydidComponent(builder) {

    val height = min(8.5, currentValue.filter { it == '\n' }.length * 1.7 + 3.5)

    label("#$name-field.c-label.o-form-element") {

        text(label)

        textarea(
            ".c-field.c-field--label",
            key = "$name-$id-input-field",
            disabled = disabled,
            placeholder = placeholder,
            style = "width:50em;height:${height}em"
        ) {

            onblur { event ->

                val newValue: String = event.target.asDynamic().value as String

                changeValue(newValue)

            }

            // TODO: onkeypress, if enter then update the value for new height

            text(currentValue)

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

