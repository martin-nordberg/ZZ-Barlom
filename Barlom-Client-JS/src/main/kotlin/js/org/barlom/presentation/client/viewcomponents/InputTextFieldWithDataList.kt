//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.viewcomponents

import o.katydid.vdom.application.katydidComponent
import o.katydid.vdom.builders.KatydidFlowContentBuilder
import o.katydid.vdom.eventhandling.onblur


//---------------------------------------------------------------------------------------------------------------------

/**
 * Configuration for a data list option.
 */
data class DataListOptionConfig(

    /** The virtual DOM key for this option. */
    val key: String,

    /** The value visible to the end user for this option. */
    val value: String

)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Builds the view for a text input field that has an accompanying data list.
 * @param builder the virtual DOM builder creating the new field.
 * @param name the name of the input field.
 * @param id a unique ID that becomes part of the key for the input field (but not an HTML id attribute).
 * @param label the user-visible label for the field.
 * @param currentValue the value of the variable that will set what is in the field.
 * @param placeholder text to show in the field when it is empty.
 * @param disabled if true the input field is disabled (grayed out)
 * @param options the list of options to put in the `<datalist>` element.
 * @param changeValue a handler to call whenever a "blur" event occurs for the field.
 */
fun <Msg> viewInputTextFieldWithDataList(
    builder: KatydidFlowContentBuilder<Msg>,
    name: String,
    id: String,
    label: String,
    currentValue: String,
    placeholder: String? = null,
    disabled: Boolean = false,
    options: List<DataListOptionConfig>,
    changeValue: (String) -> Iterable<Msg>
) = katydidComponent(builder) {

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

                changeValue(newValue)

            }

        }

        datalist(
            "#$datalistId"
        ) {

            for (optionConfig in options) {
                option("#${optionConfig.key}") { text(optionConfig.value) }
            }

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

