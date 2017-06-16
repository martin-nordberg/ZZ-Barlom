//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.forms

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for a label element.
 */
internal class KatyDomLabel : KatyDomHtmlElement{

  constructor(
    flowContent: KatyDomFlowContentBuilder,
    selector: String?,
    key: String?,
    accesskey: String?,
    contenteditable: Boolean?,
    dir: EDirection?,
    `for`: String?,
    hidden: Boolean?,
    lang: String?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    title: String?,
    translate: Boolean?,
    defineContent: KatyDomPhrasingContentBuilder.() -> Unit
  ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        flowContent.contentRestrictions.confirmInteractiveContentAllowed()
        flowContent.contentRestrictions.confirmLabelAllowed()

        setAttribute("for", `for`)

        flowContent.phrasingContent(this).withLabelNotAllowed(this).defineContent()
        this.freeze()
    }

  constructor(
      phrasingContent: KatyDomPhrasingContentBuilder,
      selector: String?,
      key: String?,
      accesskey: String?,
      contenteditable: Boolean?,
      dir: EDirection?,
      `for`: String?,
      hidden: Boolean?,
      lang: String?,
      spellcheck: Boolean?,
      style: String?,
      tabindex: Int?,
      title: String?,
      translate: Boolean?,
      defineContent: KatyDomPhrasingContentBuilder.() -> Unit
  ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()
        phrasingContent.contentRestrictions.confirmLabelAllowed()

        setAttribute("for", `for`)

        phrasingContent.withLabelNotAllowed(this).defineContent()
        this.freeze()
    }

    override val nodeName = "LABEL"

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

