//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.grouping

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a <fieldset> element.
 */
internal class KatyDomFieldSet<Message>(
    flowContent: KatyDomFlowContentBuilder<Message>,
    selector: String? = null,
    key: Any? = null,
    accesskey: String? = null,
    contenteditable: Boolean? = null,
    dir: EDirection? = null,
    disabled: Boolean? = null,
    form: String? = null,
    hidden: Boolean? = null,
    lang: String? = null,
    name: String? = null,
    spellcheck: Boolean? = null,
    style: String? = null,
    tabindex: Int? = null,
    title: String? = null,
    translate: Boolean? = null,
    defineContent: KatyDomFlowContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "FIELDSET"

    init {
        setBooleanAttribute("disabled", disabled)
        setAttribute("form", form)
        setAttribute("name", name)

        flowContent.withLegendAllowed(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
