//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.types.EDirection
import org.katydom.types.EFormEncodingType
import org.katydom.types.EFormSubmissionMethod

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a form element.
 */
internal class KatyDomForm<Message>(
    flowContent: KatyDomFlowContentBuilder<Message>,
    selector: String?,
    key: Any?,
    acceptCharset: String?,
    accesskey: String?,
    action: String?,
    autocomplete: String?,
    contenteditable: Boolean?,
    dir: EDirection?,
    enctype: EFormEncodingType?,
    hidden: Boolean?,
    lang: String?,
    method: EFormSubmissionMethod?,
    name: String?,
    novalidate: Boolean?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    target: String?,
    title: String?,
    translate: Boolean?,
    defineContent: KatyDomFlowContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key, accesskey, contenteditable, dir, hidden, lang,
                                spellcheck, style, tabindex, title, translate) {

    override val nodeName = "FORM"

    init {
        flowContent.contentRestrictions.confirmFormAllowed()

        setAttribute("accept-charset", acceptCharset)
        setAttribute("action", action)
        setAttribute("autocomplete", autocomplete)
        setAttribute("enctype", enctype?.toHtmlString())
        setAttribute("method", method?.toHtmlString())
        setAttribute("name", name)
        setBooleanAttribute("novalidate", novalidate)
        setAttribute("target", target)

        flowContent.withFormNotAllowed(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

